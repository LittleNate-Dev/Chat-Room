package pers.song.common.utlis.border;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Use this class to set the Border of components to RoundBorder
 * @author Nathan Song
 * @version 2020-12-6
 */

public class RoundBorder implements Border
{
    private Color color;

    //region Parameters of the RoundBorder
    private int x;
    private int y;
    private int width;
    private int height;
    private int arcWidth;
    private int arcHeight;
    private int leftInsets;
    //endregion

    public RoundBorder() // Constructor without paras
    {
        color = Color.BLACK;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        arcWidth = 0;
        arcHeight = 0;
        leftInsets = 0;
    }

    public RoundBorder(Color color) // Constructor with paras
    {
        this.color = color;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        arcWidth = 0;
        arcHeight = 0;
        leftInsets = 0;
    }

    public RoundBorder(int leftInsets)
    {
        this.leftInsets = leftInsets;
        this.color = color;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        arcWidth = 0;
        arcHeight = 0;
    }

    public RoundBorder(int leftInsets, int arcWidth, int arcHeight)
    {
        this.leftInsets = leftInsets;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        color = Color.BLACK;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public Insets getBorderInsets (Component c)
    {
        return new Insets(0, leftInsets, 0, 0); // Set the position of the insert cursor
    }

    public boolean isBorderOpaque ()
    {
        return false;
    }

    public void paintBorder (Component c, Graphics g, int x, int y, int width, int height)
    {
        g.setColor(color);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcWidth, arcHeight);
    }
}
