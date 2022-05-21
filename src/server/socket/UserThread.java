package server.socket;

import constant.MyConstant;
import server.database.data.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @className: UserThread
 * @description: TODO 类描述
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
                String msgType = br.readLine(),msgLine= br.readLine();
                Message msg=new Message(msgLine);
                switch (msgType){
                    case MSGTYPE_USER:
                        UserThread ut=cs.userThreadMap.get(msg.getDstName());
                        ut.sendMessage(msgLine);

                        //将聊天记录存入数据库

                        break;
                    case MSGTYPE_GROUP:
                        break;
                }
            }
        }
        catch (Exception exception){}
    }
}
