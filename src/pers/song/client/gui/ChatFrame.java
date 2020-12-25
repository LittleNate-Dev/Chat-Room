/*
 * Created by JFormDesigner on Tue Dec 15 14:28:01 CST 2020
 */

package pers.song.client.gui;

import pers.song.client.net.ClientNetController;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.gui.WarningFrame;
import pers.song.common.utlis.pack.MessagePack;
import pers.song.common.utlis.time.CurrentTime;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

/**
 * This is the chat window of the client
 * @author Nathan
 * @version 2020-12-25
 */

public class ChatFrame extends JFrame
{
    private ClientNetController CNC;
    private String sendMessage;
    private DefaultTableModel DTM;
    private LoginFrame LF;

    public ChatFrame (ClientNetController CNC, LoginFrame LF)
    {
        sendMessage = "";
        this.CNC = CNC;
        this.LF = LF;
        CNC.SetOwner(this);
        CNC.SetChatFrame(this);
        initComponents();
        SetFrame();
        SetText();
        SetTable();
    }

    private void sendButtonActionPerformed (ActionEvent e)
    {
        if(userListTable.getSelectedRow() == -1)
        {
            WarningFrame WF = new WarningFrame(this, "提示", "请选择发送对象！");
        }
        else if (sendMessage.length() >= 300)
        {
            WarningFrame WF = new WarningFrame(this, "提示", "超过字数限制！");
        }
        else
        {
            CNC.SendMessage(sendArea.getText(), userListTable.getValueAt(userListTable.getSelectedRow(), 0).toString());
            InsertNewMessage(CNC.GetUserName() + " " + CurrentTime.GetTime() + sendArea.getText() + "（我的消息）" + "\n\n", Color.BLACK);
            sendArea.setText("");
            tipLabel.setText("0/300");
        }
    }

    private void thisWindowClosing (WindowEvent e) // When the chat frame is closing, it will disconnect to the server
    {
        CNC.DisconnectServer();
    }

    private void clearButtonActionPerformed (ActionEvent e)
    {
        messageArea.setText("");
    }

    private void sendAreaKeyReleased (KeyEvent e)
    {
        if (sendArea.getText().equals("\n"))
        {
            sendArea.setText("");
            tipLabel.setText("0/300");
        }
        sendMessage = sendArea.getText();
        tipLabel.setText(Integer.toString(sendMessage.length()) + "/300");
    }

    private void enterOptionStateChanged (ChangeEvent e)
    {
        if (enterOption.isSelected())
        {
            enterLabel.setVisible(true);
        }
        else
        {
            enterLabel.setVisible(false);
        }
    }

    private void sendAreaFocusLost (FocusEvent e)
    {
        sendPanel.requestFocus();
        sendArea.requestFocus();
        sendArea.setCaretPosition(sendArea.getText().length());
    }

    private void sendAreaKeyTyped (KeyEvent e)
    {
        if (enterOption.isSelected())
        {
            if (e.isControlDown() && e.getKeyChar() == KeyEvent.VK_ENTER)
            {
                sendArea.append("\n");
            }
            else if (e.getKeyChar() == KeyEvent.VK_ENTER)
            {
                if(userListTable.getSelectedRow() == -1)
                {
                    WarningFrame WF = new WarningFrame(this, "提示", "请选择发送对象！");
                }
                else if (sendMessage.length() >= 300)
                {
                    WarningFrame WF = new WarningFrame(this, "提示", "超过字数限制！");
                }
                else
                {
                    if (!sendArea.getText().equals("\n"))
                    {
                        CNC.SendMessage(sendArea.getText().trim(), userListTable.getValueAt(userListTable.getSelectedRow(), 0).toString());
                        InsertNewMessage(CNC.GetUserName() + " " + CurrentTime.GetTime() + sendArea.getText().trim() + "（我的消息）" + "\n\n", Color.BLACK);
                        sendArea.setText("");
                        tipLabel.setText("0/300");
                    }
                    else
                    {
                        sendArea.setText("");
                        tipLabel.setText("0/300");
                    }
                }
            }
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        mesagePanel = new JScrollPane();
        messageArea = new JTextPane();
        userPanel = new JScrollPane();
        userListTable = new JTable();
        userLabel = new JLabel();
        sendPanel = new JScrollPane();
        sendArea = new JTextArea();
        sendButton = new JButton();
        tipLabel = new JLabel();
        enterOption = new JCheckBox();
        clearButton = new JButton();
        enterLabel = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/pers/song/resource/WeChat.png")).getImage());
        setTitle("\u5fae\u85aa");
        setMinimumSize(new Dimension(985, 755));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== mesagePanel ========
        {

            //---- messageArea ----
            messageArea.setEditable(false);
            mesagePanel.setViewportView(messageArea);
        }
        contentPane.add(mesagePanel);
        mesagePanel.setBounds(15, 10, 765, 430);

        //======== userPanel ========
        {
            userPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            userPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            //---- userListTable ----
            userListTable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            userListTable.setRowHeight(30);
            userPanel.setViewportView(userListTable);
        }
        contentPane.add(userPanel);
        userPanel.setBounds(785, 50, 170, 390);

        //---- userLabel ----
        userLabel.setText("\u6210\u5458\u5217\u8868");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
        contentPane.add(userLabel);
        userLabel.setBounds(785, 5, 170, 35);

        //======== sendPanel ========
        {

            //---- sendArea ----
            sendArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            sendArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    sendAreaKeyReleased(e);
                }
                @Override
                public void keyTyped(KeyEvent e) {
                    sendAreaKeyTyped(e);
                }
            });
            sendArea.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    sendAreaFocusLost(e);
                }
            });
            sendPanel.setViewportView(sendArea);
        }
        contentPane.add(sendPanel);
        sendPanel.setBounds(15, 445, 940, 210);

        //---- sendButton ----
        sendButton.setText("\u53d1\u9001");
        sendButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        sendButton.addActionListener(e -> sendButtonActionPerformed(e));
        contentPane.add(sendButton);
        sendButton.setBounds(860, 660, 95, 50);

        //---- tipLabel ----
        tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tipLabel.setText("/300");
        tipLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(tipLabel);
        tipLabel.setBounds(735, 665, 110, 40);

        //---- enterOption ----
        enterOption.setText("\u56de\u8f66\u53d1\u9001");
        enterOption.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        enterOption.addChangeListener(e -> enterOptionStateChanged(e));
        contentPane.add(enterOption);
        enterOption.setBounds(15, 660, 115, 45);

        //---- clearButton ----
        clearButton.setText("\u6e05\u7a7a\u6d88\u606f");
        clearButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        contentPane.add(clearButton);
        clearButton.setBounds(420, 660, 125, 50);

        //---- enterLabel ----
        enterLabel.setText("\u6309Ctrl+Enter\u8f93\u5165\u56de\u8f66");
        enterLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        contentPane.add(enterLabel);
        enterLabel.setBounds(new Rectangle(new Point(135, 670), enterLabel.getPreferredSize()));

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

    private void SetText () // Customize text
    {
        tipLabel.setText("0/300");
        enterLabel.setVisible(false);
    }

    private void SetTable () // Customize table
    {
        userListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer CR = new DefaultTableCellRenderer();
        CR.setHorizontalAlignment(JLabel.CENTER);
        userListTable.setDefaultRenderer(Object.class, CR);
        DTM = (DefaultTableModel)userListTable.getModel();
        DTM.addColumn("用户");
        userListTable.getTableHeader().setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        DTM.addRow(new Object[]{"全体成员"});
        CNC.GetExistedUser();
    }

    public void UpdateTable (Map<String, String> userList) // When the user login or login, use this method to update the user list shown on the chat frame
    {
        DTM.setRowCount(0);
        for (String userName : userList.keySet())
        {
            DTM.addRow(new Object[]{userName});
        }
    }

    public void UpdateMessage (MessagePack MP) // When the client receive the new message, use this method to add the new message to the message panel
    {
        if (MP.GetReceiver().equals(CNC.GetUserName()))
        {
            InsertNewMessage(MP.GetFullMessageAt(), new Color(255,64,64));
        }
        else
        {
            InsertNewMessage(MP.GetFullMessage(), new Color(15,25,181));
        }
        requestFocus();
    }

    private void InsertNewMessage (String message, Color color) // Add the new message to the message panel with different color
    {
        messageArea.setCaretPosition(0);
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attrSet, color);
        StyleConstants.setFontSize(attrSet, 20);
        Document docs = messageArea.getDocument();
        try
        {
            docs.insertString(docs.getLength(), message, attrSet);
            Document newDocs = messageArea.getDocument();
            messageArea.setCaretPosition(newDocs.getLength());
        }
        catch (BadLocationException e)
        {
            ShowExceptionAll(e);
        }
    }

    public void ReturnToLogin () // When the server is closed, use this method to dispose the chat frame and return to the login frame
    {
        this.dispose();
        LF.ReturnToLogin();
    }

    private void ShowExceptionAll (Exception e) // Create a Exception frame to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(this, "程序异常", "程序发生异常！异常信息如下：", e);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane mesagePanel;
    private JTextPane messageArea;
    private JScrollPane userPanel;
    private JTable userListTable;
    private JLabel userLabel;
    private JScrollPane sendPanel;
    private JTextArea sendArea;
    private JButton sendButton;
    private JLabel tipLabel;
    private JCheckBox enterOption;
    private JButton clearButton;
    private JLabel enterLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
