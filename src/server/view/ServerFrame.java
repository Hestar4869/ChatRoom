/*
 * Created by JFormDesigner on Wed May 25 20:25:52 CST 2022
 */

package server.view;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.jgoodies.forms.factories.*;
import constant.MyConstant;
import server.database.data.Message;
import server.socket.ChatServer;
import server.socket.UserThread;

import javax.swing.border.*;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author MuQuanyu
 */
public class ServerFrame extends JFrame implements ActionListener
{
    ChatServer cs = ChatServer.getInstance();
    public static DefaultListModel<String> lst = new DefaultListModel<>();

    public ServerFrame() {
        //初始化组件
        initComponents();

        //事件和数据绑定
        jList.setModel(lst);
        msgBtn.addActionListener(this::actionPerformed);
        msgText.addActionListener(this::actionPerformed);
        logoutBtn.addActionListener(this::actionPerformed);

        //服务器启动
        cs.start();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        jList = new JList();
        buttonPanel = new JPanel();
        vSpacer2 = new JPanel(null);
        logoutBtn = new JButton();
        label1 = new JLabel();
        msgBtn = new JButton();
        vSpacer6 = new JPanel(null);
        panel1 = new JPanel();
        msgText = new JTextField();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 400));
        setVisible(true);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(jList);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);

        //======== buttonPanel ========
        {
            buttonPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.add(vSpacer2);

            //---- logoutBtn ----
            logoutBtn.setText("logout");
            buttonPanel.add(logoutBtn);

            //---- label1 ----
            label1.setText(" ");
            buttonPanel.add(label1);

            //---- msgBtn ----
            msgBtn.setText("send Message");
            msgBtn.setAlignmentX(0.15F);
            buttonPanel.add(msgBtn);
            buttonPanel.add(vSpacer6);
        }
        contentPane.add(buttonPanel, BorderLayout.EAST);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- msgText ----
            msgText.setPreferredSize(new Dimension(200, 30));
            panel1.add(msgText);
        }
        contentPane.add(panel1, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JList jList;
    private JPanel buttonPanel;
    private JPanel vSpacer2;
    private JButton logoutBtn;
    private JLabel label1;
    private JButton msgBtn;
    private JPanel vSpacer6;
    private JPanel panel1;
    private JTextField msgText;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == logoutBtn)
        {
            java.util.List<String> selectedUser = jList.getSelectedValuesList();
            System.out.println("logoutBtn被点击");

            //todo send logout messsge to those users who are selected in the JList
            for (String username : selectedUser)
            {
                System.out.println("被选中的用户有" + username);
                UserThread ut = cs.userThreadMap.get(username);
                lst.removeElement(username);
                cs.currentUsers.remove(username);
                cs.userThreadMap.remove(username);
                ut.logout();
            }
        }
        else if (e.getSource() == msgBtn || e.getSource() == msgText)
        {
            //todo send System message to those users who are selected in the JList
            java.util.List<String> selectedUser = jList.getSelectedValuesList();

            for (String username : selectedUser)
            {
                Message msg = new Message("系统消息", username, msgText.getText());
                UserThread ut = cs.userThreadMap.get(username);
                ut.sendMessage(msg.toString(), MyConstant.MSGTYPE_USER);
            }
            msgText.setText("");
        }
    }
    public static void main(String[] args)
    {
        //Flat Darcula
        try
        {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }
        catch (Exception ex)
        {
            System.err.println("Failed to initialize LaF");
        }
        new ServerFrame();
    }
}
