package server.socket;

import server.database.data.User;
import server.socket.runnable.LoginRunnable;
import server.socket.runnable.RegisterRunnable;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.zip.Deflater;

/**
 * @className: ChatServer
 * @description: 处理用户请求的聊天服务器
 * @author: HMX
 * @date: 2022-05-19 16:52
 */
public class ChatServer
{
    private static ChatServer chatServer=new ChatServer();
    //保存目前在线的用户
    public List<String> currentUsers =new LinkedList<>();

    public Map<String,UserThread> userThreadMap=new HashMap<>();

    private ChatServer() {
        System.out.println("实例化完毕");
    }
    public void start(){
        try
        {
            new Thread(new LoginRunnable()).start();
            new Thread(new RegisterRunnable()).start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static ChatServer getInstance(){
        if (chatServer==null){
            System.out.println("当前chatServer为null");
        }
        return chatServer;
    }


}
