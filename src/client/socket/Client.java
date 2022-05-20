package client.socket;

import java.io.*;
import java.net.Socket;

/**
 * @className: Client
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 18:39
 */
public class Client implements Runnable
{
    //单例模式
    private static Client client=new Client();
    private static Socket socket;
    //Socket的输入输出流
    private static PrintStream ps;
    private static BufferedReader br;
    //判断当前客户端是否运行以及是否有新消息
    public boolean isRun=true;
    public boolean isRead=false;

    private Client(){}

    public static Client getInstance(){
        return client;
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

    public static void main(String[] args)
    {

    }

    @Override
    public void run()
    {
        try
        {
            while(isRun){
                String flag= br.readLine();
                if(flag.equals("LOGOUT")){
                    isRun=false;
                }
                else if(flag.equals("MESSAGE")){
                    isRead=true;
                }
            }
        }
        catch (Exception exception){}

    }
}
