/*
 * Created by JFormDesigner on Mon Dec 07 12:04:54 CST 2020
 */

package pers.song.common.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Use this class to create a exception window and show some exception message
 * @author Nathan
 * @version 2020-12-21
 */

public class ExceptionFrame extends JDialog
{
    //region Parameters of the frame
    private String title;
    private String warningMessage;
    private Exception e;
    private String exceptionMessageAll;
    private Writer writer;
    private PrintWriter PW;
    public static boolean EFExisting = false; // Detect whether the ExceptionFrame is already existed
    //endregion

    public ExceptionFrame (Window owner, String title, String warningMessage, Exception e)
    {
        super(owner);
        if (!ExceptionFrame.EFExisting)
        {
            this.title = title;
            this.warningMessage = warningMessage;
            this.e = e;
            initComponents();
            SetFrame();
            SetText();
            ExceptionFrame.EFExisting = true;
        }
    }

    public ExceptionFrame (Window owner, Exception e)
    {
        super(owner);
        if (!ExceptionFrame.EFExisting)
        {
            this.title = "程序异常";
            this.warningMessage = "程序发生异常！异常信息如下：";
            this.e = e;
            initComponents();
            SetFrame();
            SetText();
            ExceptionFrame.EFExisting = true;
        }
    }

    private void thisWindowClosed(WindowEvent e) {
        ExceptionFrame.EFExisting = false;
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        warningMessageLabel = new JLabel();
        exceptionPanel = new JScrollPane();
        exceptionInfoBox = new JTextArea();

        //======== this ========
        setMinimumSize(new Dimension(540, 500));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- warningMessageLabel ----
        warningMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningMessageLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 22));
        contentPane.add(warningMessageLabel, BorderLayout.NORTH);

        //======== exceptionPanel ========
        {

            //---- exceptionInfoBox ----
            exceptionInfoBox.setEditable(false);
            exceptionInfoBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            exceptionPanel.setViewportView(exceptionInfoBox);
        }
        contentPane.add(exceptionPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void SetFrame () // Customize frame
    {
        setLocationRelativeTo(null); // Set the location of the frame to the center of the screen
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(title);
        setVisible(true);
    }

    private void SetText () // Customize text
    {
        writer = new StringWriter();
        PW = new PrintWriter(writer);
        e.fillInStackTrace().printStackTrace(PW);
        exceptionMessageAll = writer.toString();
        warningMessageLabel.setText(warningMessage);
        exceptionInfoBox.setText(exceptionMessageAll);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel warningMessageLabel;
    private JScrollPane exceptionPanel;
    private JTextArea exceptionInfoBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
