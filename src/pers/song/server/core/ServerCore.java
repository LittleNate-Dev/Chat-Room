package pers.song.server.core;

import pers.song.server.gui.LoginFrame;
import pers.song.common.gui.WarningFrame;

/**
 * This class is the core of the server and contain the main method
 * @author Nathan Song
 * @version 2020-12-14
 */

public class ServerCore
{
    public static void main (String args[])
    {
        WarningFrame.WFExisting = false;
        LoginFrame LF = new LoginFrame();
    }
}
