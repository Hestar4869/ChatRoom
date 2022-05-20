package client.view;

import client.socket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @className: LoginFrame
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 16:13
 */
public class LoginFrame extends JFrame implements ActionListener
{
    private String username;
    final int WIDTH = 300;
    final int HEIGHT = 229;
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 20, 20);

    //登录界面的元素
    private JLabel userLabel = new JLabel("输入账号");
    private JTextField userText = new JTextField(20);
    private JLabel passwdLabel = new JLabel("输入密码");
    private JPasswordField passwdText = new JPasswordField(20);

    private JButton registerButton=new JButton("注册");
    private JButton loginButton = new JButton("登录");
    private JButton exitButton = new JButton("取消");

    private JPanel loginPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JPanel passwdPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    //构造登录页面
    public LoginFrame() throws HeadlessException
    {
        //给三个按钮添加事件
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        registerButton.addActionListener(this);

        //初始化登录面板
        textPanel.setLayout(flowLayout);
        textPanel.add(userLabel);
        textPanel.add(userText);

        passwdPanel.setLayout(flowLayout);
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwdText);

        buttonPanel.setLayout(flowLayout);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(registerButton);

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        //填充透明盒子
        loginPanel.add(Box.createVerticalGlue());
        loginPanel.add(textPanel);
        loginPanel.add(passwdPanel);
        loginPanel.add(buttonPanel);
        loginPanel.add(Box.createVerticalGlue());


        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - WIDTH) / 2, (int) (width.getHeight() - HEIGHT) / 2);

        //设置当前显示的是登录面板
//        loginPanel.setSize(WIDTH,HEIGHT);
        this.add(loginPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("登录界面");
        System.out.println(this.getWidth()+" "+this.getHeight());
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if (e.getSource() == loginButton)
            {
                //获取登录账号
                String username = userText.getText(), passwd = String.valueOf(passwdText.getPassword());

                //todo 用SocketClient.login()函数尝试登录
                if (Client.loginRequest(username,passwd)) {
                    JOptionPane.showMessageDialog(this, "登录成功");
                    new ChatFrame(username);

                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "登录失败，账号或密码错误");
                }
            }
            else if (e.getSource() == exitButton){
                //退出
                System.exit(0);
            }

            else if(e.getSource()==registerButton){
                //获取登录账号
                String username = userText.getText(), passwd = String.valueOf(passwdText.getPassword());
                Client.registerRequest(username,passwd);
                JOptionPane.showMessageDialog(this,"注册成功");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        LoginFrame loginFrame = new LoginFrame();
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                new LoginFrame();
//            }
//        }).start();
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                new LoginFrame();
//            }
//        }).start();
    }
}
