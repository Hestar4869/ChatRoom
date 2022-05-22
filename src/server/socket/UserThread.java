package server.socket;

import constant.MyConstant;
import server.database.dao.MsgDAO;
import server.database.daoimpl.MsgDAOImpl;
import server.database.data.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @className: UserThread
 * @description: 单独与用户保持socket连接的线程
 * @author: HMX
 * @date: 2022-05-19 18:51
 */
public class UserThread extends Thread implements MyConstant
{
    ChatServer cs=ChatServer.getInstance();

    private String username;
    private Socket socket;

    private BufferedReader br=null;
    private PrintStream ps=null;

    private boolean isRun=true;
    public UserThread(String username, Socket socket) throws IOException
    {
        this.username = username;
        this.socket = socket;
        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps=new PrintStream(socket.getOutputStream());
        this.start();
    }

    public void logout(){
        ps.println(TYPE_LOGOUT);
        isRun=false;
    }
    public void sendMessage(String msgLine){

        ps.println(TYPR_MESSAGE);
        //send Message object
        ps.println(MSGTYPE_USER);
        ps.println(msgLine);
        System.out.println("服务器将消息"+msgLine+"转发给"+username);
    }
    @Override
    public void run()
    {
        try {
            super.run();
            while (isRun)
            {
                String type=br.readLine();
                switch (type){
                    //收到来自客户端的登出请求
                    case TYPE_LOGOUT:
                        isRun=false;
                        //去除当前在线用户
                        cs.currentUsers.remove(username);
                        //去除 用户:线程 映射
                        cs.userThreadMap.remove(username);
                        break;

                    //收到来自客户端发送给另一用户的消息 的请求
                    case TYPR_MESSAGE:
                        String msgType = br.readLine(),msgLine= br.readLine();
                        Message msg=new Message(msgLine);
                        switch (msgType)
                        {
                            case MSGTYPE_USER:
                                UserThread ut = cs.userThreadMap.get(msg.getDstName());
                                ut.sendMessage(msgLine);

                                //将聊天记录存入数据库
                                MsgDAO msgDAO=new MsgDAOImpl();
                                msgDAO.insert(new Message(msgLine));

                                break;
                            case MSGTYPE_GROUP:
                                break;

                        }
                    break;
                }
            }
        }
        catch (Exception exception){

        }
    }
}
