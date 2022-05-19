package server.socket;

import java.net.Socket;

/**
 * @className: UserThread
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 18:51
 */
public class UserThread extends Thread
{
    private String username;
    private Socket socket;


    public UserThread(String username, Socket socket)
    {
        this.username = username;
        this.socket = socket;
    }
}
