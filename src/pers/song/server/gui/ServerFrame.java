/*
 * Created by JFormDesigner on Mon Dec 14 22:56:48 CST 2020
 */

package pers.song.server.gui;

import pers.song.common.gui.ExceptionFrame;
import pers.song.server.dao.DBController;
import pers.song.server.net.ServerNetController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;
import java.util.Enumeration;
import java.util.Map;

/**
 * This is the main window of the server and it will show some states of the server
 * @author Nathan
 * @version 2020-12-25
 */

public class ServerFrame extends JFrame
{
    private DBController DBC;
    private ServerNetController SNC;
    private DefaultTableModel DTM;
    private boolean serverState;

    public ServerFrame (DBController DBC)
    {
        this.DBC = DBC;
        DBC.SetOwner(this);
        initComponents();
        SetFrame();
        SetButton();
        SetTable();
        SNC = new ServerNetController(this, DBC);
        serverState = false;
    }

    private void startButtonActionPerformed (ActionEvent e)
    {
        stateLabel.setText("服务器已启动！");
        portLabel.setText("端口号3060等待连接");
        startButton.setVisible(false);
        closeButton.setVisible(true);
        SNC.StartServer();
        ipLabel.setText("本机IP为： " + GetIP());
        serverState = true;
    }

    private void closeButtonActionPerformed (ActionEvent e)
    {
        stateLabel.setText("服务器等待启动！");
        portLabel.setText("端口号3060已关闭");
        startButton.setVisible(true);
        closeButton.setVisible(false);
        ipLabel.setText("服务器未启动");
        SNC.CloseServer();
        SNC.CLearUserList();
        SNC.ClearThreadList();
        DTM.setRowCount(0);
        serverState = false;
    }

    private void clearButtonActionPerformed (ActionEvent e)
    {
        messageContent.setText("");
    }

    private void thisWindowClosing (WindowEvent e)
    {
        if (serverState == true)
        {
            SNC.CloseServer();
            DBC.DisconnectDB();
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        connectLabel = new JLabel();
        connectNumberLabel = new JLabel();
        messagePanel = new JScrollPane();
        messageContent = new JTextArea();
        userPanel = new JScrollPane();
        currentUser = new JLabel();
        messageLabel = new JLabel();
        portLabel = new JLabel();
        startButton = new JButton();
        stateLabel = new JLabel();
        closeButton = new JButton();
        clearButton = new JButton();
        ipLabel = new JLabel();
        userTable = new JTable()
        {
            public boolean isCellEditable (int row, int column)
            {
                return false;
            }
        };

        //======== this ========
        setMinimumSize(new Dimension(850, 740));
        setTitle("\u5fae\u85aa\u670d\u52a1\u5668");
        setIconImage(new ImageIcon(getClass().getResource("/pers/song/resource/WeChat.png")).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- connectLabel ----
        connectLabel.setText("\u5f53\u524d\u8fde\u63a5\u5ba2\u6237\u7aef\u6570\u91cf\uff1a");
        connectLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(connectLabel);
        connectLabel.setBounds(30, 270, 205, 45);

        //---- connectNumberLabel ----
        connectNumberLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        connectNumberLabel.setText("0");
        contentPane.add(connectNumberLabel);
        connectNumberLabel.setBounds(230, 280, 105, connectNumberLabel.getPreferredSize().height);

        //======== messagePanel ========
        {
            messagePanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            messagePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            //---- messageContent ----
            messageContent.setEditable(false);
            messageContent.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            messagePanel.setViewportView(messageContent);
        }
        contentPane.add(messagePanel);
        messagePanel.setBounds(0, 375, 845, 330);

        //======== userPanel ========
        {
            userPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            userPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            //---- userTable ----
            userTable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            userTable.setRowHeight(30);
            userPanel.setViewportView(userTable);
        }
        contentPane.add(userPanel);
        userPanel.setBounds(365, 40, 475, 330);

        //---- currentUser ----
        currentUser.setText("\u5f53\u524d\u8fde\u63a5\u7528\u6237");
        currentUser.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(currentUser);
        currentUser.setBounds(new Rectangle(new Point(535, 5), currentUser.getPreferredSize()));

        //---- messageLabel ----
        messageLabel.setText("\u65e5\u5fd7\u8bb0\u5f55");
        messageLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(messageLabel);
        messageLabel.setBounds(new Rectangle(new Point(35, 340), messageLabel.getPreferredSize()));

        //---- portLabel ----
        portLabel.setText("\u7aef\u53e3\u53f73060\u5df2\u5173\u95ed");
        portLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(portLabel);
        portLabel.setBounds(30, 195, 260, portLabel.getPreferredSize().height);

        //---- startButton ----
        startButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        startButton.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/Start_Button.png")));
        startButton.addActionListener(e -> startButtonActionPerformed(e));
        contentPane.add(startButton);
        startButton.setBounds(30, 15, 128, 128);

        //---- stateLabel ----
        stateLabel.setText("\u670d\u52a1\u5668\u7b49\u5f85\u542f\u52a8");
        stateLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(stateLabel);
        stateLabel.setBounds(30, 155, 230, stateLabel.getPreferredSize().height);

        //---- closeButton ----
        closeButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        closeButton.setIcon(new ImageIcon(getClass().getResource("/pers/song/resource/Stop_Button.png")));
        closeButton.addActionListener(e -> closeButtonActionPerformed(e));
        contentPane.add(closeButton);
        closeButton.setBounds(200, 15, 128, 128);

        //---- clearButton ----
        clearButton.setText("\u6e05\u7a7a\u5185\u5bb9");
        clearButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        contentPane.add(clearButton);
        clearButton.setBounds(150, 335, 175, 35);

        //---- ipLabel ----
        ipLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        ipLabel.setText("\u670d\u52a1\u5668\u672a\u542f\u52a8");
        contentPane.add(ipLabel);
        ipLabel.setBounds(30, 235, 315, 35);

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

    private void SetButton () // Customize button
    {
        closeButton.setVisible(false);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
    }

    private void SetTable () // Customize table
    {
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer CR = new DefaultTableCellRenderer();
        CR.setHorizontalAlignment(JLabel.CENTER);
        userTable.setDefaultRenderer(Object.class, CR);
        DTM = (DefaultTableModel)userTable.getModel();
        DTM.addColumn("用户");
        DTM.addColumn("IP");
        userTable.getTableHeader().setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    }

    public void UpdateConnectNumber (String connectNumber) // Update the connect number
    {
        connectNumberLabel.setText(connectNumber);
    }

    public void ResetTable (Map<String, Socket> userList) // If there are users login or logout, update the user list
    {
        DTM.setRowCount(0);
        for (String userName : userList.keySet())
        {
            Socket socket = userList.get(userName);
            DTM.addRow(new Object[]{userName, socket.getInetAddress().toString()});
        }
    }

    public void AppendMessageContent (String message) // Add new message to the log panel
    {
        messageContent.append(message);
        messageContent.setCaretPosition(messageContent.getText().length());
    }

    public void UpdateUserList (String userName, String IP)
    {
        Object [] newUser = {userName, IP};
        DTM.addRow(newUser);
    }

    private String GetIP () // Get server's IP
    {
        String IP = null;
        try
        {
            Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            for(;allNetworkInterfaces.hasMoreElements();)
            {
                NetworkInterface networkInterface=allNetworkInterfaces.nextElement();
                if(networkInterface.isLoopback()||networkInterface.isVirtual()||!networkInterface.isUp()||networkInterface.getDisplayName().contains("VM"))
                {
                    continue;
                }
                for(Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses(); inetAddressEnumeration.hasMoreElements();)
                {
                    InetAddress inetAddress=inetAddressEnumeration.nextElement();
                    if(inetAddress!=null)
                    {
                        if (inetAddress instanceof Inet4Address)
                        {
                            IP = inetAddress.getHostAddress();
                            return IP;
                        }
                    }
                }
            }
        }
        catch(SocketException exception)
        {
            ShowExceptionAll(exception);
            return null;
        }
        return null;
    }

    private void ShowExceptionAll (Exception e)
    {
        ExceptionFrame EF  = new ExceptionFrame(this, e);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel connectLabel;
    private JLabel connectNumberLabel;
    private JScrollPane messagePanel;
    private JTextArea messageContent;
    private JScrollPane userPanel;
    private JTable userTable;
    private JLabel currentUser;
    private JLabel messageLabel;
    private JLabel portLabel;
    private JButton startButton;
    private JLabel stateLabel;
    private JButton closeButton;
    private JButton clearButton;
    private JLabel ipLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
