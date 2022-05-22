package server.socket.runnable;

import server.database.dao.UserDAO;
import server.database.daoimpl.UserDAOImpl;
import server.database.data.User;
import server.socket.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: RegisterRunnable
 * @description: 显示 注册功能 的线程
 * @author: HMX
 * @date: 2022-05-19 18:42
 */
public class RegisterRunnable implements Runnable
{
    ServerSocket ss;
    ChatServer cs=ChatServer.getInstance();
    public RegisterRunnable() throws IOException
    {
        this.ss =new ServerSocket(17779);
    }

    @Override
    public void run()
    {
        while(true){
            try
            {
                Socket socket=ss.accept();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                //获取从客户端传送的账号密码
                String username=br.readLine(),passwd= br.readLine();

                UserDAO userDAO=new UserDAOImpl();
                userDAO.insert(new User(username,passwd));
                ps.println("succeed");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
