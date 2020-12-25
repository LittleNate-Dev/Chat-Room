package pers.song.client.core;

import pers.song.client.gui.LoginFrame;
import pers.song.common.gui.WarningFrame;

/**
 * This class is the core of the client and contain the main method
 * @author Nathan Song
 * @version 2020-12-14
 */

public class ClientCore
{
    public static void main (String args[])
    {
        WarningFrame.WFExisting = false;
        LoginFrame LF = new LoginFrame();
    }
}
