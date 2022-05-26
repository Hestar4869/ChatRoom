package server.socket.runnable;

import server.database.dao.GroupDAO;
import server.database.dao.MsgDAO;
import server.database.daoimpl.GroupDAOImpl;
import server.database.daoimpl.MsgDAOImpl;
import server.database.data.Message;
import server.socket.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
                MsgDAO msgDAO=new MsgDAOImpl();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);
                ObjectOutputStream oos=new ObjectOutputStream(os);

                //获取远端客户端的用户名
                String clientUser=br.readLine();

                //传送在线用户
                ps.println(cs.currentUsers.size());
                for (String user : cs.currentUsers)
                {
                    ps.println(user);

                    //如何在线用户为客户端用户，不传送消息
                    if (user.equals(clientUser))
                        continue;
                    //传送两人之间的聊天记录
                    List<Message> msgs=msgDAO.findByTwoUsername(clientUser,user);
                    //传送聊天记录的条数
                    ps.println(msgs.size());
                    //传送聊天记录具体内容
                    for (Message msg:msgs)
                        ps.println(msg.toString());

                }
                //传送该用户所属群组
                GroupDAO groupDAO=new GroupDAOImpl();
                List<String> groups=groupDAO.findGroupsbyUser(clientUser);
                //先传送群组数
                ps.println(groups.size());
                for (String group : groups)
                    ps.println(group);


                //传送历史聊天记录

                socket.shutdownInput();
                socket.shutdownOutput();
            }
        }catch (Exception exception){}

    }
}
