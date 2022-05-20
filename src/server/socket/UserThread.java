package server.socket;

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
public class UserThread extends Thread
{
    private String username;
    private Socket socket;

    private BufferedReader br=null;
    private PrintStream ps=null;

    public UserThread(String username, Socket socket) throws IOException
    {
        this.username = username;
        this.socket = socket;
        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps=new PrintStream(socket.getOutputStream());
    }

    public void logout(){
        ps.println("LOGOUT");
    }
    public void sendMessage(String msg){
        ps.println("MESSAGE");
        //send Message object

    }

}
