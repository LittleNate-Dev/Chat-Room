/*
 * Created by JFormDesigner on Sun Dec 06 18:21:25 CST 2020
 */

package pers.song.client.gui;

import pers.song.client.net.ClientNetController;
import pers.song.common.gui.WarningFrame;
import pers.song.common.utlis.border.RoundBorder;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class will create a login window
 * This LoginFrame belongs to client
 * @author Nathan Song
 * @version 2020-12-24
 */

public class LoginFrame extends JFrame
{
    private String password;
    private String IP;

    private ClientNetController CNC;

    public LoginFrame ()
    {
        CNC = new ClientNetController(this);
        initComponents();
        SetFrame();
        SetTextField();
        SetButton();
        SetLabel();
    }

    private void loginButtonActionPerformed (ActionEvent e)
    {
        password = new String(passwordBox.getPassword());
        if (userNameBox.getText().equals(""))
        {
            WarningFrame WF = new WarningFrame(this, "提示", "请输入用户名！");
        }
        else if (password.equals(""))
        {
            WarningFrame WF = new WarningFrame(this, "提示", "请输入密码！");
        }
        else if (ipBox_1.getText().equals("") || ipBox_2.getText().equals("") || ipBox_3.getText().equals("") || ipBox_4.getText().equals(""))
        {
            WarningFrame WF = new WarningFrame(this, "提示", "请输入完整的服务器IP地址！");
        }
        else
        {
            IP = ipBox_1.getText()+"."+ipBox_2.getText()+"."+ipBox_3.getText()+"."+ipBox_4.getText();
            CNC.SetUser(userNameBox.getText(), password);
            CNC.SetLoginFrame(this);
            loginButton.setVisible(false);
            connectingLabel.setVisible(true);
            CNC.ConnectServer(IP);
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        mainPanel = new JPanel();
        titleLabel = new JLabel();
        userNameLabel = new JLabel();
        passwordLabel = new JLabel();
        ipLabel = new JLabel();
        copyrightLabel = new JLabel();
        userNameBox = new JTextField();
        passwordBox = new JPasswordField();
        loginButton = new JButton();
        IconLabel = new JLabel();
        ipBox_1 = new JTextField();
        ipBox_2 = new JTextField();
        ipBox_3 = new JTextField();
        ipBox_4 = new JTextField();
        dotLabel_1 = new JLabel();
        dotLabel_2 = new JLabel();
        dotLabel_3 = new JLabel();
        connectingLabel = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(390, 440));
        setTitle("\u5fae\u85aa");
        setBackground(new Color(204, 204, 204));
        setIconImage(new ImageIcon(getClass().getResource("/pers/song/resource/WeChat.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== mainPanel ========
        {
            mainPanel.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < mainPanel.getComponentCount(); i++) {
                    Rectangle bounds = mainPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = mainPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                mainPanel.setMinimumSize(preferredSize);
                mainPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(mainPanel);
        mainPanel.setBounds(new Rectangle(new Point(145, 160), mainPanel.getPreferredSize()));

        //---- titleLabel ----
        titleLabel.setText("\u5fae\u85aa");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 48));
        contentPane.add(titleLabel);
        titleLabel.setBounds(125, 25, 215, 50);

        //---- userNameLabel ----
        userNameLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/UserIcon.png")));
        contentPane.add(userNameLabel);
        userNameLabel.setBounds(30, 105, 32, 32);

        //---- passwordLabel ----
        passwordLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/LockIcon.png")));
        contentPane.add(passwordLabel);
        passwordLabel.setBounds(30, 175, 32, 32);

        //---- ipLabel ----
        ipLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/IP.png")));
        contentPane.add(ipLabel);
        ipLabel.setBounds(30, 245, 32, 32);

        //---- copyrightLabel ----
        copyrightLabel.setText("Copyright \u00a9 2020-2023 Nathan Song");
        copyrightLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        contentPane.add(copyrightLabel);
        copyrightLabel.setBounds(225, 390, 155, copyrightLabel.getPreferredSize().height);

        //---- userNameBox ----
        userNameBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(userNameBox);
        userNameBox.setBounds(80, 105, 290, 40);

        //---- passwordBox ----
        passwordBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(passwordBox);
        passwordBox.setBounds(80, 175, 290, 40);

        //---- loginButton ----
        loginButton.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 26));
        loginButton.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/Button_Default.png")));
        loginButton.setSelectedIcon(null);
        loginButton.addActionListener(e -> loginButtonActionPerformed(e));
        contentPane.add(loginButton);
        loginButton.setBounds(135, 310, 115, 50);

        //---- IconLabel ----
        IconLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/WeChat_Min.png")));
        contentPane.add(IconLabel);
        IconLabel.setBounds(90, 15, 70, 70);

        //---- ipBox_1 ----
        ipBox_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(ipBox_1);
        ipBox_1.setBounds(80, 240, 65, 40);

        //---- ipBox_2 ----
        ipBox_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(ipBox_2);
        ipBox_2.setBounds(155, 240, 65, 40);

        //---- ipBox_3 ----
        ipBox_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(ipBox_3);
        ipBox_3.setBounds(230, 240, 65, 40);

        //---- ipBox_4 ----
        ipBox_4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(ipBox_4);
        ipBox_4.setBounds(305, 240, 65, 40);

        //---- dotLabel_1 ----
        dotLabel_1.setText(".");
        dotLabel_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 48));
        contentPane.add(dotLabel_1);
        dotLabel_1.setBounds(new Rectangle(new Point(145, 225), dotLabel_1.getPreferredSize()));

        //---- dotLabel_2 ----
        dotLabel_2.setText(".");
        dotLabel_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 48));
        contentPane.add(dotLabel_2);
        dotLabel_2.setBounds(new Rectangle(new Point(220, 225), dotLabel_2.getPreferredSize()));

        //---- dotLabel_3 ----
        dotLabel_3.setText(".");
        dotLabel_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 48));
        contentPane.add(dotLabel_3);
        dotLabel_3.setBounds(new Rectangle(new Point(295, 225), dotLabel_3.getPreferredSize()));

        //---- connectingLabel ----
        connectingLabel.setText("\u6b63\u5728\u8fde\u63a5...");
        connectingLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        connectingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(connectingLabel);
        connectingLabel.setBounds(100, 305, 205, 65);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void SetFrame () // Customize frame
    {
        setLocationRelativeTo(null); // Set the location of the frame to the center of the screen
        setResizable(false); // Lock the size of the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void SetLabel () // customize label
    {
        connectingLabel.setVisible(false);
    }

    private void SetTextField () // Customize text field
    {
        userNameBox.setBorder(new RoundBorder(10,20,20));
        userNameBox.setBackground(new Color(238,238,238));
        passwordBox.setBorder(new RoundBorder(10,20,20));
        passwordBox.setBackground(new Color(238,238,238));
        ipBox_1.setBorder(new RoundBorder(5,20,20));
        ipBox_1.setBackground(new Color(238,238,238));
        ipBox_2.setBorder(new RoundBorder(5,20,20));
        ipBox_2.setBackground(new Color(238,238,238));
        ipBox_3.setBorder(new RoundBorder(5,20,20));
        ipBox_3.setBackground(new Color(238,238,238));
        ipBox_4.setBorder(new RoundBorder(5,20,20));
        ipBox_4.setBackground(new Color(238,238,238));
        //region Limit the length of the ip
        ipBox_1.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if(getLength() + str.length() <= 3)
                    super.insertString(offs, str, a);
            }
        });
        ipBox_2.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if(getLength() + str.length() <= 3)
                    super.insertString(offs, str, a);
            }
        });
        ipBox_3.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if(getLength() + str.length() <= 3)
                    super.insertString(offs, str, a);
            }
        });
        ipBox_4.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if(getLength() + str.length() <= 3)
                    super.insertString(offs, str, a);
            }
        });
        //endregion
    }

    private void SetButton () // Customize button
    {
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
    }

    public void SetChatFrame () // Set the chat frame of the CNC
    {
        ChatFrame CF = new ChatFrame(CNC, this);
        setVisible(false);
    }

    public void ConnectFalse () // When false to connect to the server, use this method to set the visibility of the login button
    {
        loginButton.setVisible(true);
        connectingLabel.setVisible(false);
    }

    public void ConnectTrue ()
    {
        loginButton.setVisible(true);
        connectingLabel.setVisible(false);
    }

    public void ReturnToLogin ()
    {
        setVisible(true);
        CNC.SetOwner(this);
        loginButton.setVisible(true);
        connectingLabel.setVisible(false);
        WarningFrame WF = new WarningFrame(this, "提示", "服务器已关闭！");
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel ipLabel;
    private JLabel copyrightLabel;
    private JTextField userNameBox;
    private JPasswordField passwordBox;
    private JButton loginButton;
    private JLabel IconLabel;
    private JTextField ipBox_1;
    private JTextField ipBox_2;
    private JTextField ipBox_3;
    private JTextField ipBox_4;
    private JLabel dotLabel_1;
    private JLabel dotLabel_2;
    private JLabel dotLabel_3;
    private JLabel connectingLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
