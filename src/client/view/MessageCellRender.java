package client.view;

import org.jdesktop.swingx.VerticalLayout;
import server.database.data.Message;

import javax.swing.*;
import java.awt.*;

import static java.awt.Label.RIGHT;

/**
 * @className: MessageCellRender
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-21 11:19
 */
public class MessageCellRender extends JPanel implements ListCellRenderer
{
    private String username;
    public MessageCellRender(String username)
    {
        this.username=username;
        this.setLayout(new VerticalLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        this.removeAll();
        //将添加的元素转换为Message
        Message msg=(Message)value;
        JLabel userLabel=new JLabel(msg.getSrcName()+"说：");
        JLabel msgLabel=new JLabel(msg.getContent()+"    ");
        JLabel blankLabel=new JLabel(" ");

        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        //添加UI元素
        panel.add(userLabel);
        panel.add(msgLabel);
        panel.add(blankLabel);

        if(msg.getSrcName().equals(username)){
            //发消息的是自己，靠右
            userLabel.setAlignmentX(1);
            msgLabel.setAlignmentX(1);
            blankLabel.setAlignmentX(1);
        }
        else{
            //发消息的不是自己，靠左
            userLabel.setAlignmentX(0);
            msgLabel.setAlignmentX(0);
            blankLabel.setAlignmentX(0);
            msgLabel.setText("    "+msg.getContent());
        }

        this.add(panel);
        return this;
    }
}
