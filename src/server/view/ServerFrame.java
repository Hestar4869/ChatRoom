package server.view;

import server.socket.ChatServer;
import server.socket.UserThread;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
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
public class ServerFrame extends JFrame implements ActionListener, ListDataListener
{
    ChatServer cs=ChatServer.getInstance();
    //界面宽高
    private final int WIDTH =400;
    private final int HEIGHT=400;
    //界面UI元素
    JPanel jPanel=new JPanel();
    public static DefaultListModel<String> lst = new DefaultListModel<>();
    JList<String> jList=new JList(lst);


    JPanel btnPanel=new JPanel();
    JButton logoutBtn=new JButton("Logout");
    JButton msgBtn=new JButton("send Message");
    //更新轮询器
    Timer timer=new Timer(1000,this);

    public ServerFrame() throws HeadlessException
    {

        cs.start();
        //jList.setListData(cs.currentUsers.toArray());
        //按钮绑定事件
        logoutBtn.addActionListener(this);
        msgBtn.addActionListener(this);
        //add a Panel with many Buttoms which is BoxLayout in Y
        btnPanel.setLayout(new BoxLayout(btnPanel,BoxLayout.Y_AXIS));
        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(logoutBtn);
        btnPanel.add(new JLabel(" "));
        btnPanel.add(msgBtn);
        btnPanel.add(Box.createVerticalGlue());


        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - WIDTH) / 2, (int) (width.getHeight() - HEIGHT) / 2);

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(jList),BorderLayout.WEST);
        this.add(btnPanel,BorderLayout.EAST);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);

        timer.start();

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==timer) {
//            jList.setListData((Vector<? extends String>) cs.currentUsers);
        }
        else if(e.getSource()==logoutBtn){
            java.util.List<String> selectedUser= jList.getSelectedValuesList();
            System.out.println("logoutBtn被点击");

            //todo send logout messsge to those users who are selected in the JList
            for (String username : selectedUser)
            {
                System.out.println("被选中的用户有"+username);
                UserThread ut = cs.userThreadMap.get(username);
                lst.removeElement(username);
                ut.logout();
            }
        }
        else if(e.getSource()==msgBtn){
            //todo send System message to those users who are selected in the JList
        }
    }
    public static void main(String[] args)
    {
        new ServerFrame();
    }

    @Override
    public void intervalAdded(ListDataEvent e)
    {

    }

    @Override
    public void intervalRemoved(ListDataEvent e)
    {

    }

    @Override
    public void contentsChanged(ListDataEvent e)
    {

    }
}
