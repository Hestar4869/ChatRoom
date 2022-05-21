package client.view;

import client.socket.Client;
import constant.MyConstant;
import server.database.data.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @className: ChatFrame
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 21:17
 */
public class ChatFrame extends JFrame implements ActionListener,MyConstant
{
    private JButton testBtn=new JButton();
    private final int WIDTH=500;
    private final int HEIGHT=300;
    Client client=Client.getInstance();
    private String username;

    JPanel jPanel=new JPanel();

    Timer timer=new Timer(100, this);

    public ChatFrame(String username) throws HeadlessException
    {
        testBtn.addActionListener(this);
        this.add(testBtn);
        this.username=username;
        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - WIDTH) / 2, (int) (width.getHeight() - HEIGHT) / 2);


        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==timer){
            //每隔0.1s轮询，检查新消息和登出
            //先检查消息
            if(client.isRead){
                //有新消息需要读取
            }

            //后检查是否关闭
            if(!client.isRun){
                //客户端已关闭
                System.exit(0);
            }
        }
        else if(e.getSource()==testBtn){
            Client.sendMessage(new Message("a","b","hello b. This is a."),MyConstant.MSGTYPE_USER);
            System.out.println("已发送消息");
        }

    }

}
