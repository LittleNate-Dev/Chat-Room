/*
 * Created by JFormDesigner on Sun Dec 06 18:21:25 CST 2020
 */

package pers.song.server.gui;

import pers.song.common.gui.WarningFrame;
import pers.song.common.utlis.border.RoundBorder;
import pers.song.server.dao.DBController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class will create a login window
 * This LoginFrame belongs to server
 * @author Nathan Song
 * @version 2020-12-15
 */

public class LoginFrame extends JFrame
{
    private String password;
    private DBController DBC;

    public LoginFrame()
    {
        initComponents();
        SetFrame();
        SetTextField();
        SetButton();
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
        else
        {
            DBC = new DBController(userNameBox.getText(), password);
            DBC.SetOwner(this);
            if (DBC.ConnectDB())
            {
                setVisible(false);
                ServerFrame SF = new ServerFrame(DBC);
            }
            else if (DBC.GetException().equals("Access denied for user '"+userNameBox.getText()+"'@'localhost' (using password: YES)"))
            {
                WarningFrame WF = new WarningFrame(this, "提示", "用户名或密码错误！");
            }
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        mainPanel = new JPanel();
        titleLabel = new JLabel();
        userNameLabel = new JLabel();
        passwordLabel = new JLabel();
        copyrightLabel = new JLabel();
        userNameBox = new JTextField();
        passwordBox = new JPasswordField();
        loginButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(390, 375));
        setTitle("\u5fae\u85aa\u670d\u52a1\u5668");
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
        titleLabel.setText("\u5fae\u85aa\u670d\u52a1\u5668");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 48));
        contentPane.add(titleLabel);
        titleLabel.setBounds(55, 25, 275, 50);

        //---- userNameLabel ----
        userNameLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/UserIcon.png")));
        contentPane.add(userNameLabel);
        userNameLabel.setBounds(30, 105, 32, 32);

        //---- passwordLabel ----
        passwordLabel.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/LockIcon.png")));
        contentPane.add(passwordLabel);
        passwordLabel.setBounds(30, 175, 32, 32);

        //---- copyrightLabel ----
        copyrightLabel.setText("Copyright \u00a9 2020-2023 Nathan Song");
        copyrightLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        contentPane.add(copyrightLabel);
        copyrightLabel.setBounds(225, 325, 155, copyrightLabel.getPreferredSize().height);

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
        loginButton.setBounds(135, 245, 115, 50);

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

    private void SetTextField () // Customize text field
    {
        userNameBox.setBorder(new RoundBorder(10,20,20));
        userNameBox.setBackground(new Color(238,238,238));
        passwordBox.setBorder(new RoundBorder(10,20,20));
        passwordBox.setBackground(new Color(238,238,238));
    }

    private void SetButton () // Customize button
    {
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel copyrightLabel;
    private JTextField userNameBox;
    private JPasswordField passwordBox;
    private JButton loginButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
