package client.socket;

import client.view.ChatFrame;
import constant.MyConstant;
import server.database.data.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @className: Client
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 18:39
 */
public class Client implements Runnable, MyConstant
{
    public static ChatFrame chatFrame;
    //单例模式
    private static Client client=new Client();
    private static Socket socket;
    //Socket的输入输出流
    private static PrintStream ps;
    private static BufferedReader br;
    //判断当前客户端是否运行以及是否有新消息
    public boolean isRun=true;
    public boolean isRead=false;

    //当前在线用户
    public static List<String> currentUsers=new Vector<>();
    //所属群组
    public static List<String> groups=new Vector<>();
    //所有聊天记录
    public static Map<String,List<Message>> map;
    private Client(){
        new Thread(this).start();
    }
    public static Client getInstance(){
        return client;
    }

    //将传入的消息类，发送给服务器，通过服务器发送给其他用户
    //type:"user" 或 "group"
    public static boolean sendMessage(Message msg,String msgType){
        chatFrame.msgListModel.addElement(msg);
        ps.println(TYPR_MESSAGE);
        ps.println(msgType);
        ps.println(msg.toString());
        System.out.println(msg.toString());
        return true;
    }
    //根据传入的账号密码，进行账号注册
    public static boolean registerRequest(String username,String passwd) throws Exception {
        Socket registerSocket = new Socket("127.0.0.1", 17779);
        //输入流
        InputStream is = registerSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = registerSocket.getOutputStream();
        PrintStream ps = new PrintStream(os);

        //传送账号密码
        ps.println(username);
        ps.println(passwd);

        //读取信息
        String info = br.readLine();
        if (info.equals("succeed"))
        {
            System.out.println("注册成功");
        }
        return true;
    }
    //根据传入的账号密码，对远端服务器进行请求验证
    public static boolean loginRequest(String username, String passwd) throws Exception {
        Socket loginSocket = new Socket("127.0.0.1", 17775);
        //输入流
        InputStream is = loginSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = loginSocket.getOutputStream();
        PrintStream ps = new PrintStream(os);

        //传送账号密码
        ps.println(username);
        ps.println(passwd);

        //读取信息
        String info = br.readLine();
        if (info.equals("succeed"))
        {
            System.out.println("登录成功");
            Client.socket=loginSocket;
            Client.ps=ps;
            Client.br=br;
            new Thread(getInstance()).start();
            return true;
        }
        else
        {
            System.out.println("账号或密码错误");
            return false;
        }
    }
    //根据传入的用户信息，向远端服务器请求所有初始化所需信息
    public static boolean initRequest(String username) throws Exception{
        Socket s=new Socket("127.0.0.1",17776);
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps=new PrintStream(s.getOutputStream());
        ObjectInputStream oos=new ObjectInputStream(s.getInputStream());
        ps.println(username);
        int count= Integer.valueOf(br.readLine());
        for(int i=1;i<=count;i++){
            String user=br.readLine();
            if(user.equals(username))
                continue;
            currentUsers.add(user);
        }
//        while (true)
//        {
//            Thread.sleep(1000);
//            try
//            {
//                s.sendUrgentData(0xFF);
//            }
//            catch (Exception exception)
//            {
//                //先读入当前在线的用户
//                currentUsers = (Vector<String>) oos.readObject();
//                break;
//                //再读入所属群组
////        groups=(List<String>)oos.readObject();
//                //再读入用户聊天记录和群组聊天记录
////        map= (Map<String, List<Message>>) oos.readObject();
//            }
//
//        }
        for (String user : currentUsers)
        {
            System.out.println("当前在线的用户有："+user);
        }
        return true;
    }

    //开启线程，等待登出信息或者其他用户的聊天消息
    @Override
    public void run()
    {
        try
        {
            while(isRun){
                String flag= br.readLine();
                System.out.println("收到flag "+flag);

                if(flag.equals(TYPE_LOGOUT)){
                    isRun=false;
                }
                else if(flag.equals(TYPR_MESSAGE)){
                    isRead=true;
                    String msgType= br.readLine();
                    switch (msgType){
                        case MSGTYPE_GROUP:
                            //添加到群聊消息

                            break;
                        case MSGTYPE_USER:
                            //添加到用户消息
                            String msgLine= br.readLine();
                            Message msg=new Message(msgLine);
                            chatFrame.msgListModel.addElement(msg);
                            break;
                    }
                }
            }
        }
        catch (Exception exception){}

    }
}
