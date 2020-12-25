/*
 * Created by JFormDesigner on Mon Dec 07 11:11:05 CST 2020
 */

package pers.song.common.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Use this class to create a warning window and show some warning message
 * @author Nathan
 * @version 2020-12-14
 */

public class WarningFrame extends JDialog
{
    //region Parameters of the frame
    private String title;
    private String warningMessage;
    public static boolean WFExisting = false; // Detect whether the WarningFrame is already existed
    //endregion

    public WarningFrame (Window owner, String title, String warningMessage)
    {
        super(owner);
        if(!WarningFrame.WFExisting)
        {
            this.title = title;
            this.warningMessage = warningMessage;
            initComponents();
            SetFrame();
            SetLabel();
            WarningFrame.WFExisting = true;
        }
    }

    public WarningFrame (Window owner, String title, String warningMessage, int width, int height) // You can change the size of the window with using this constructor
    {
        super(owner);
        this.title = title;
        this.warningMessage = warningMessage;
        initComponents();
        SetFrame();
        SetLabel();
        setBounds(this.getX(),this.getY(),width,height);
        WarningFrame.WFExisting = true;
    }

    private void thisWindowClosed (WindowEvent e)
    {
        WarningFrame.WFExisting = false;
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        warningMessageLabel = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(330, 180));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //---- warningMessageLabel ----
        warningMessageLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 22));
        warningMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(warningMessageLabel);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void SetFrame () // Customize frame
    {
        setLocationRelativeTo(null); // Set the location of the frame to the center of the screen
        setResizable(false); // Lock the size of the frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle(title);
    }

    private void SetLabel () // Customize label
    {
        warningMessageLabel.setText(warningMessage);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel warningMessageLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
