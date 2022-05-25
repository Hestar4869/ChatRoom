package server.socket;

import constant.MyConstant;
import server.database.dao.MsgDAO;
import server.database.daoimpl.MsgDAOImpl;
import server.database.data.Message;
import server.view.ServerFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

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
        //告诉本线程对应的客户端下线
        ps.println(TYPE_LOGOUT);
        isRun=false;

        //通知其他线程，有新用户下线
        for (String user: cs.currentUsers){
            UserThread ut=cs.userThreadMap.get(user);
            ut.sendNewUserLogout(username);
        }
    }
    public void sendMessage(String msgLine){

        ps.println(TYPR_MESSAGE);
        //send Message object
        ps.println(MSGTYPE_USER);
        ps.println(msgLine);
        System.out.println("服务器将消息"+msgLine+"转发给"+username);
    }

    public void sendNewUserLogin(String newUser) throws Exception
    {
        ps.println(TYPE_USERLOGIN);
        ps.println(newUser);
        //传送新用户的聊天记录
        MsgDAO msgDAO=new MsgDAOImpl();
        //传送两人之间的聊天记录
        List<Message> msgs=msgDAO.findByTwoUsername(username,newUser);
        //传送聊天记录的条数
        ps.println(msgs.size());
        //传送聊天记录具体内容
        for (Message msg:msgs)
            ps.println(msg.toString());
    }
    public void sendNewUserLogout(String newUser){
        ps.println(TYPE_USERLOGOUT);
        ps.println(newUser);
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
                    case TYPE_USERLOGOUT:
                        isRun=false;
                        //去除当前在线用户
                        String logoutUser=br.readLine();
                        cs.currentUsers.remove(username);
                        ServerFrame.lst.removeElement(username);
                        //去除 用户:线程 映射
                        cs.userThreadMap.remove(username);
                        //向其他客户端发送该用户登出的信息
                        for (String user:cs.currentUsers){
                            UserThread ut=cs.userThreadMap.get(user);
                            ut.sendNewUserLogout(logoutUser);
                        }
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
