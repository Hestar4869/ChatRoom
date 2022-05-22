/*
 * Created by JFormDesigner on Sun May 22 20:19:28 CST 2022
 */

package test;

import org.jdesktop.swingx.*;

import javax.swing.*;

/**
 * @author MuQuanyu
 */
public class MsgCellRender extends JPanel {
    public MsgCellRender() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        userLabel = new JLabel();
        label1 = new JLabel();
        panel2 = new JPanel();
        userLabel2 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        setLayout(new VerticalLayout());

        //======== panel1 ========
        {
            panel1.setAlignmentX(0.0F);
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

            //---- userLabel ----
            userLabel.setText("user say:");
            panel1.add(userLabel);

            //---- label1 ----
            label1.setText("        \u4f60\u597d\u53d1\u9001\u5361\u53d1\u6492\u91d1\u5361");
            panel1.add(label1);
        }
        add(panel1);

        //======== panel2 ========
        {
            panel2.setAlignmentX(0.0F);
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

            //---- userLabel2 ----
            userLabel2.setText("user say:");
            userLabel2.setAlignmentX(1.0F);
            panel2.add(userLabel2);

            //---- label2 ----
            label2.setText("\u4f60\u597d\u53d1\u9001\u5361\u53d1\u6492\u91d1\u5361        ");
            label2.setAlignmentX(1.0F);
            panel2.add(label2);
        }
        add(panel2);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel userLabel;
    private JLabel label1;
    private JPanel panel2;
    private JLabel userLabel2;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args)
    {

    }
}
