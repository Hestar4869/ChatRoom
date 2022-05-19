package server.view;

import server.socket.ChatServer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * @className: ServerFrame
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 19:09
 */
public class ServerFrame extends JFrame implements ActionListener
{
    ChatServer cs=ChatServer.getInstance();

    //界面UI元素
    JPanel jPanel=new JPanel();
    JList jList=new JList();

    //更新轮询器
    Timer timer=new Timer(1000,this);

    public ServerFrame() throws HeadlessException
    {
        cs.start();
        //jList.setListData(cs.currentUsers.toArray());


        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - WIDTH) / 2, (int) (width.getHeight() - HEIGHT) / 2);

        this.add(new JScrollPane(jList));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200,160);

        timer.start();

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==timer) {
            jList.setListData(cs.currentUsers.toArray());
        }
    }
    public static void main(String[] args)
    {
        new ServerFrame();
    }
}
