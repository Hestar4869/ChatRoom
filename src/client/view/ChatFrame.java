/*
 * Created by JFormDesigner on Sat May 21 10:32:39 CST 2022
 */

package client.view;

import client.socket.Client;
import constant.MyConstant;
import server.database.data.Message;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    public Map<String, DefaultListModel<Message>> msgListModelMap = new HashMap<>();

    //用户列表框相关UI
    public DefaultListModel<String> userListModel;
    public DefaultListModel<String> groupListModel;

    public ChatFrame(String username) throws Exception
    {
        this.username = username;
        //开启轮询器
        timer.start();
        //初始化组件
        initComponents();
        deliverBtn.addActionListener(this::actionPerformed);
        deleverText.addActionListener(this::actionPerformed);
        createGroupBtn.addActionListener(this::actionPerformed);

        //初始化消息框UI
        msgCellRender = new MessageCellRender(username);
        msgListModel = new DefaultListModel<>();
        msgList.setCellRenderer(msgCellRender);
        msgList.setModel(msgListModel);

        //初始化用户列表框UI
        userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userListModel.addElement("系统消息");
        msgListModelMap.put("系统消息", new DefaultListModel<>());

        //初始化群组列表框UI
        groupListModel = new DefaultListModel<>();
        groupList.setModel(groupListModel);

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
    private void ListValueChanged(ListSelectionEvent e)
    {
        //当鼠标按下时
        if (e.getSource() == userList && e.getValueIsAdjusting())
        {
            java.util.List<String> users = userList.getSelectedValuesList();
            //数量大于1,直接跳过
            if (users.size() > 1)
            {
                return;
            }

            //切换ListModel
            String user = users.get(0);
            System.out.println("用户被点击了,开始切换");
            msgList.setModel(msgListModelMap.get(user));
        }
        else if (e.getSource() == groupList && e.getValueIsAdjusting()){
            java.util.List<String> groups=groupList.getSelectedValuesList();
            //数量大于1,直接跳过
            if (groups.size() > 1)
                return;

            //切换ListModel
            String group = groups.get(0);
            System.out.println("群组被点击了,开始切换");
            msgList.setModel(msgListModelMap.get(group));
        }
    }

    private void thisWindowClosing(WindowEvent e)
    {
        // 窗口关闭
        System.out.println("窗口关闭了");
        //向远端服务器传送用户下线的消息
        try
        {
            Client.logoutRequest(username);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public String getMessageType()
    {
        if (westPanel.getTabCount() == 0)
        {
            return MSGTYPE_USER;
        }
        else
        {
            return MSGTYPE_GROUP;
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
        vSpacer1 = new JPanel(null);
        createGroupBtn = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        vSpacer2 = new JPanel(null);
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
                userList.addListSelectionListener(e -> ListValueChanged(e));
                userTab.setViewportView(userList);
            }
            westPanel.addTab("\u7528\u6237", userTab);

            //======== groupTab ========
            {

                //---- groupList ----
                groupList.addListSelectionListener(e -> ListValueChanged(e));
                groupTab.setViewportView(groupList);
            }
            westPanel.addTab("\u7fa4\u7ec4", groupTab);
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
            eastPanel.add(vSpacer1);

            //---- createGroupBtn ----
            createGroupBtn.setText("\u521b\u5efa\u7fa4\u804a");
            eastPanel.add(createGroupBtn);

            //---- button2 ----
            button2.setText("text");
            eastPanel.add(button2);

            //---- button3 ----
            button3.setText("text");
            eastPanel.add(button3);
            eastPanel.add(vSpacer2);
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
    private JPanel vSpacer1;
    private JButton createGroupBtn;
    private JButton button2;
    private JButton button3;
    private JPanel vSpacer2;
    private JPanel panel1;
    private JTextField deleverText;
    private JButton deliverBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == timer)
        {
            //每隔0.1s轮询，检查登出

            //后检查是否关闭
            if (!client.isRun)
            {
                //客户端已关闭
                System.exit(0);
            }
        }
        else if (e.getSource() == deliverBtn || e.getSource() == deleverText)
        {
            String msgType=getMessageType();
            //该处的user可能是用户也可能是群组
            java.util.List<String> selectedUser;
            if(msgType.equals(MSGTYPE_USER))
                selectedUser=userList.getSelectedValuesList();
            else
                selectedUser=groupList.getSelectedValuesList();

            for (String user : selectedUser)
            {
                Message msg = new Message(username, user, deleverText.getText());
                Client.sendMessage(msg, getMessageType());

                DefaultListModel<Message> lm=msgListModelMap.get(user);
                lm.addElement(msg);
            }
            deleverText.setText("");
            System.out.println("已发送消息");
        }
        else if (e.getSource() == createGroupBtn)
        {
            java.util.List<String> selectedUser = userList.getSelectedValuesList();
            if (selectedUser.size() < 2)
            {
                JOptionPane.showMessageDialog(this, "至少选择两个用户");
                return;
            }
            String groupName = JOptionPane.showInputDialog(this, "请输入新建的群名");
            selectedUser.add(username);
            Client.createGroupRequest(selectedUser, groupName);
        }
    }

}
