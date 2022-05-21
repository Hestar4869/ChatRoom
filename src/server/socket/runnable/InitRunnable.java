package server.socket.runnable;

import server.socket.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: InitRunnable
 * @description: 对各个用户的初始化请求进行响应
 * @author: HMX
 * @date: 2022-05-21 15:56
 */
public class InitRunnable implements Runnable
{
    private ServerSocket initServer;
    private boolean isRun=true;
    private ChatServer cs=ChatServer.getInstance();

    public InitRunnable() throws IOException
    {
        initServer=new ServerSocket(17776);
    }

    @Override
    public void run()
    {
        try
        {
            while(isRun){
                Socket socket=initServer.accept();

                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);
                ObjectOutputStream oos=new ObjectOutputStream(os);

                //传送在线用户
                ps.println(cs.currentUsers.size());
                for (String user : cs.currentUsers)
                {
                    ps.println(user);
                }
                System.out.println("收到来自"+br.readLine()+"的请求");
                socket.shutdownInput();
                socket.shutdownOutput();
            }
        }catch (Exception exception){}

    }
}
