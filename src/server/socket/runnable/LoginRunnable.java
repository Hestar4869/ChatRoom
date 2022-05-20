package server.socket.runnable;

import server.database.dao.UserDAO;
import server.database.daoimpl.UserDAOImpl;
import server.database.data.User;
import server.socket.ChatServer;
import server.socket.UserThread;
import server.view.ServerFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: LoginRunnable
 * @description: 处理客户端登录请求，监听17775端口
 * @author: HMX
 * @date: 2022-05-19 17:01
 */
public class LoginRunnable implements Runnable
{
    private ServerSocket loginServer;
    private boolean isRun=true;
    private ChatServer cs=ChatServer.getInstance();
    public LoginRunnable() throws IOException
    {
        loginServer=new ServerSocket(17775);

    }
    @Override
    public void run()
    {
        while(isRun){
            try
            {
                System.out.println("LoginRunnable开始运行");
                Socket socket=loginServer.accept();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                //获取从客户端传送的账号密码
                String username=br.readLine(),passwd= br.readLine();

                UserDAO userDAO=new UserDAOImpl();
                if(userDAO.findPasswordByUsername(username).equals(passwd))
                {
                    ps.println("succeed");
                    //将该用户添加进入在线用户
                    cs.currentUsers.add(username);
                    cs.userThreadMap.put(username,new UserThread(username,socket));
                    ServerFrame.lst.addElement(username);
                }
                else
                    ps.println("failed");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void stop(){ isRun=false;}

    public static void main(String[] args) throws IOException
    {
        new Thread(new LoginRunnable()).start();
        new Thread(new RegisterRunnable()).start();
    }
}
