package server.socket;

import constant.MyConstant;
import server.database.dao.GroupDAO;
import server.database.dao.MsgDAO;
import server.database.daoimpl.GroupDAOImpl;
import server.database.daoimpl.MsgDAOImpl;
import server.database.data.Message;
import server.view.ServerFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * @className: UserThread
 * @description: 单独与用户保持socket连接的线程
 * @author: HMX
 * @date: 2022-05-19 18:51
 */
public class UserThread extends Thread implements MyConstant
{
    ChatServer cs = ChatServer.getInstance();

    private String username;
    private Socket socket;

    private BufferedReader br = null;
    private PrintStream ps = null;

    private boolean isRun = true;

    public UserThread(String username, Socket socket) throws IOException
    {
        this.username = username;
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps = new PrintStream(socket.getOutputStream());
        this.start();
    }

    public void logout()
    {
        //告诉本线程对应的客户端下线
        ps.println(TYPE_LOGOUT);
        isRun = false;

        //通知其他线程，有新用户下线
        for (String user : cs.currentUsers)
        {
            UserThread ut = cs.userThreadMap.get(user);
            ut.sendNewUserLogout(username);
        }
    }

    public void sendMessage(String msgLine,String msgType)
    {
        ps.println(TYPR_MESSAGE);
        //send Message object
        ps.println(msgType);
        ps.println(msgLine);
        System.out.println("服务器将消息" + msgLine + "转发给" + username);
    }

    public void sendNewUserLogin(String newUser) throws Exception
    {
        ps.println(TYPE_USER_LOGIN);
        ps.println(newUser);
        //传送新用户的聊天记录
        MsgDAO msgDAO = new MsgDAOImpl();
        //传送两人之间的聊天记录
        List<Message> msgs = msgDAO.findByTwoUsername(username, newUser);
        //传送聊天记录的条数
        ps.println(msgs.size());
        //传送聊天记录具体内容
        for (Message msg : msgs)
            ps.println(msg.toString());
    }

    public void sendNewUserLogout(String newUser)
    {
        ps.println(TYPE_USER_LOGOUT);
        ps.println(newUser);
    }

    public void sendNewGroupCreate(String groupName)
    {
        ps.println(TYPE_GROUP_CREATE);
        ps.println(groupName);
    }

    @Override
    public void run()
    {
        try
        {
            super.run();
            while (isRun)
            {
                String type = br.readLine();
                switch (type)
                {
                    //收到来自客户端的登出请求
                    case TYPE_USER_LOGOUT:
                        logoutResponse();
                        break;

                    //收到来自客户端发送给另一用户的消息 的请求
                    case TYPR_MESSAGE:
                        messageResponse();
                        break;
                    case TYPE_GROUP_CREATE:
                        createGroupResponse();
                        break;
                }
            }
        }
        catch (Exception exception)
        {

        }
    }

    private void logoutResponse() throws IOException
    {
        isRun = false;
        //去除当前在线用户
        String logoutUser = br.readLine();
        cs.currentUsers.remove(username);
        ServerFrame.lst.removeElement(username);
        //去除 用户:线程 映射
        cs.userThreadMap.remove(username);
        //向其他客户端发送该用户登出的信息
        for (String user : cs.currentUsers)
        {
            UserThread ut = cs.userThreadMap.get(user);
            ut.sendNewUserLogout(logoutUser);
        }
    }

    private void messageResponse() throws Exception
    {
        UserThread ut;
        String msgType = br.readLine(), msgLine = br.readLine();
        Message msg = new Message(msgLine);
        System.out.println(msgLine);
        //将聊天记录存入数据库
        MsgDAO msgDAO = new MsgDAOImpl();
        msgDAO.insert(new Message(msgLine));
        switch (msgType)
        {
            case MSGTYPE_USER:
                //将消息转发给另一个用户
                ut = cs.userThreadMap.get(msg.getDstName());
                ut.sendMessage(msgLine,msgType);

                break;
            case MSGTYPE_GROUP:
                //获取群组名称
                String groupName=msg.getDstName();
                GroupDAO groupDAO=new GroupDAOImpl();
                List<String> users=groupDAO.findUsersByGroup(groupName);

                //将消息传送给该群组中的其他用户
                for (String user : users)
                {
                    System.out.println("在群聊"+groupName+"中的用户有"+user);
                    if(!cs.currentUsers.contains(user) || user.equals(username))
                        continue;
                    ut=cs.userThreadMap.get(user);
                    ut.sendMessage(msgLine,msgType);
                }

                break;


        }
    }

    private void createGroupResponse() throws Exception
    {
        System.out.println("收到新群聊建立的请求");
        List<String> users = new Vector<>();
        int userCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < userCount; i++)
        {
            String user = br.readLine();
            System.out.println(user);
            users.add(user);
        }
        String groupName = br.readLine();
        //将新建群组插入到数据库中
        GroupDAO groupDAO = new GroupDAOImpl();
        groupDAO.insertGroup(users, groupName);

        //告诉目前在线的用户，已被拉入一个新群聊
        for (String user : cs.currentUsers)
        {
            if (!users.contains(user))
            {
                continue;
            }
            UserThread ut = cs.userThreadMap.get(user);
            ut.sendNewGroupCreate(groupName);
        }

    }


}
