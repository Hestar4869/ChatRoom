/*
 * Created by JFormDesigner on Sat May 21 10:32:39 CST 2022
 */

package client.view;

import client.socket.Client;
import constant.MyConstant;
import server.database.data.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author MuQuanyu
 */
public class ChatFrame extends JFrame implements ActionListener, MyConstant
{
    //轮询器
    Timer timer = new Timer(100, this);
    Client client = Client.getInstance();
    //当前登录的用户
    private String username;

    //消息List的相关UI
    public MessageCellRender msgCellRender;
    public DefaultListModel<Message> msgListModel;

    public ChatFrame(String username) throws Exception
    {
        this.username = username;
        //开启轮询器
        timer.start();
        //初始化组件
        initComponents();
        deliverBtn.addActionListener(this::actionPerformed);

        //初始化消息框UI
        msgCellRender = new MessageCellRender(username);
        msgListModel = new DefaultListModel<>();
        msgList.setCellRenderer(msgCellRender);
        msgList.setModel(msgListModel);
        //初始化历史记录
        initHistory();
        //
        label1.setText(label1.getText() + username);
    }

    private void initHistory() throws Exception
    {
        //获取当前在线用户并初始化历史聊天记录
        Client.initRequest(username);

        //如果不为空，则加入到用户列表
        if (!Client.currentUsers.isEmpty())
        {
            //初始化用户列表
            userList.setModel(new AbstractListModel<String>()
            {
                @Override
                public int getSize()
                {
                    return Client.currentUsers.size();
                }

                @Override
                public String getElementAt(int index)
                {
                    return Client.currentUsers.get(index);
                }
            });


        }
    }

    private void deliverBtn(ActionEvent e)
    {
        // TODO add your code here
    }


    private void initComponents()
    {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        westPanel = new JTabbedPane();
        userTab = new JScrollPane();
        userList = new JList();
        groupTab = new JScrollPane();
        groupList = new JList<>();
        centerPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        msgList = new JList();
        eastPanel = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        panel1 = new JPanel();
        deleverText = new JTextField();
        deliverBtn = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(585, 450));
        setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- label1 ----
        label1.setFont(new Font("JetBrains Mono", Font.BOLD | Font.ITALIC, label1.getFont().getSize() + 12));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBackground(UIManager.getColor("Button.background"));
        label1.setText("\u6b22\u8fce\u6765\u5230\u804a\u5929\u754c\u9762");
        label1.setForeground(UIManager.getColor("Button.background"));
        contentPane.add(label1, BorderLayout.NORTH);

        //======== westPanel ========
        {

            //======== userTab ========
            {
                userTab.setPreferredSize(new Dimension(100, 164));
                userTab.setViewportView(userList);
            }
            westPanel.addTab("\u7528\u6237", userTab);

            //======== groupTab ========
            {

                //---- groupList ----
                groupList.setModel(new AbstractListModel<String>()
                {
                    String[] values = {
                            "fsdk", "fsafsfsf", "fsgsdd"
                    };

                    @Override
                    public int getSize()
                    {
                        return values.length;
                    }

                    @Override
                    public String getElementAt(int i)
                    {
                        return values[i];
                    }
                });
                groupTab.setViewportView(groupList);
            }
            westPanel.addTab("\u7fa4\u7ec4", groupTab);

            westPanel.setSelectedIndex(1);
        }
        contentPane.add(westPanel, BorderLayout.WEST);

        //======== centerPanel ========
        {
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

            //======== scrollPane1 ========
            {

                //---- msgList ----
                msgList.setForeground(UIManager.getColor("Button.background"));
                scrollPane1.setViewportView(msgList);
            }
            centerPanel.add(scrollPane1);
        }
        contentPane.add(centerPanel, BorderLayout.CENTER);

        //======== eastPanel ========
        {
            eastPanel.setAlignmentX(0.0F);
            eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

            //---- button1 ----
            button1.setText("text");
            eastPanel.add(button1);

            //---- button2 ----
            button2.setText("text");
            eastPanel.add(button2);

            //---- button3 ----
            button3.setText("text");
            eastPanel.add(button3);
        }
        contentPane.add(eastPanel, BorderLayout.EAST);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- deleverText ----
            deleverText.setMinimumSize(new Dimension(80, 30));
            deleverText.setPreferredSize(new Dimension(250, 30));
            panel1.add(deleverText);

            //---- deliverBtn ----
            deliverBtn.setText("\u53d1\u9001");
            panel1.add(deliverBtn);
        }
        contentPane.add(panel1, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public static void main(String[] args) throws Exception
    {
        new ChatFrame("a");
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTabbedPane westPanel;
    private JScrollPane userTab;
    private JList userList;
    private JScrollPane groupTab;
    private JList<String> groupList;
    private JPanel centerPanel;
    private JScrollPane scrollPane1;
    private JList msgList;
    private JPanel eastPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel panel1;
    private JTextField deleverText;
    private JButton deliverBtn;

    // JFormDesigner - End of variables declaration  //GEN-END:variables
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == timer)
        {
            //每隔0.1s轮询，检查新消息和登出
            //先检查消息
            if (client.isRead)
            {
                //有新消息需要读取
            }

            //后检查是否关闭
            if (!client.isRun)
            {
                //客户端已关闭
                System.exit(0);
            }
        }
        else if (e.getSource() == deliverBtn)
        {
            java.util.List<String> selectedUser = userList.getSelectedValuesList();
            for (String user : selectedUser)
            {
                Client.sendMessage(new Message(username, user, deleverText.getText()), MyConstant.MSGTYPE_USER);
                deleverText.setText("");
            }

            System.out.println("已发送消息");
        }
    }

    /**
     * @description:
     * @param:
     * @return: boolean
     * @author: HMX
     * @date: 2022-5-21 15:20
     */
    private boolean checkSelectedItem(String type)
    {
        switch (type)
        {
            case MSGTYPE_USER:

                break;
            case MSGTYPE_GROUP:

        }
        return true;
    }
}
