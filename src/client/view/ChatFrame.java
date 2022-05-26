/*
 * Created by JFormDesigner on Sat May 21 10:32:39 CST 2022
 */

package client.view;

import java.awt.event.*;
import javax.swing.event.*;
import client.socket.Client;
import constant.MyConstant;
import server.database.data.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class ChatFrame extends JFrame implements ActionListener, MyConstant
{
    //轮询器
    Timer timer = new Timer(100, this);
    Client client = Client.getInstance();
    //当前登录的用户
    private String username;

    //消息List列表框的相关UI
    public MessageCellRender msgCellRender;
    public DefaultListModel<Message> msgListModel;
    public Map<String,DefaultListModel<Message>> msgListModelMap=new HashMap<>();

    //用户列表框相关UI
    public DefaultListModel<String> userListModel;

    public ChatFrame(String username) throws Exception
    {
        this.username = username;
        //开启轮询器
        timer.start();
        //初始化组件
        initComponents();
        deliverBtn.addActionListener(this::actionPerformed);
        deleverText.addActionListener(this::actionPerformed);

        //初始化消息框UI
        msgCellRender = new MessageCellRender(username);
        msgListModel = new DefaultListModel<>();
        msgList.setCellRenderer(msgCellRender);
        msgList.setModel(msgListModel);

        //初始化用户列表框UI
        userListModel=new DefaultListModel<>();
        userList.setModel(userListModel);
        userListModel.addElement("系统消息");
        msgListModelMap.put("系统消息",new DefaultListModel<>());
        //初始化历史记录
//        initHistory();

        label1.setText(label1.getText() + username);
    }

    public void initHistory() throws Exception
    {
        //获取当前在线用户并初始化历史聊天记录
        Client.initRequest(username);

        //如果不为空，则加入到用户列表
        if (!Client.currentUsers.isEmpty())
        {

        }
    }

    private void deliverBtn(ActionEvent e)
    {
        // TODO add your code here
    }

    //userList列表框绑定事件
    private void userListValueChanged(ListSelectionEvent e) {
        //当鼠标按下时
        if(e.getValueIsAdjusting()){
            java.util.List<String> users=userList.getSelectedValuesList();
            //数量大于1,直接跳过
            if (users.size()>1)
                return;

            //切换ListModel
            String user=users.get(0);
            System.out.println("被点击了,开始切换");
            msgList.setModel(msgListModelMap.get(user));
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        // 窗口关闭
        System.out.println("窗口关闭了");
        //向远端服务器传送用户下线的消息
        try
        {
            Client.logoutRequest(username);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private void initComponents()
    {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        westPanel = new JTabbedPane();
        userTab = new JScrollPane();
        userList = new JList();
        groupTab = new JScrollPane();
        groupList = new JList();
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- label1 ----
        label1.setFont(new Font("JetBrains Mono", Font.BOLD|Font.ITALIC, label1.getFont().getSize() + 12));
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

                //---- userList ----
                userList.addListSelectionListener(e -> userListValueChanged(e));
                userTab.setViewportView(userList);
            }
            westPanel.addTab("\u7528\u6237", userTab);

            //======== groupTab ========
            {
                groupTab.setViewportView(groupList);
            }
            westPanel.addTab("\u7fa4\u7ec4", groupTab);

            westPanel.setSelectedIndex(0);
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
    private JList groupList;
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
        else if (e.getSource() == deliverBtn || e.getSource()==deleverText)
        {
            java.util.List<String> selectedUser = userList.getSelectedValuesList();
            for (String user : selectedUser)
            {
                Message msg=new Message(username, user, deleverText.getText());
                Client.sendMessage(msg, MyConstant.MSGTYPE_USER);
                DefaultListModel<Message> lm=msgListModelMap.get(user);
                lm.addElement(msg);
            }
            deleverText.setText("");
            System.out.println("已发送消息");
        }
    }

}
