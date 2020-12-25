package pers.song.client.net;

import pers.song.client.gui.LoginFrame;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.gui.WarningFrame;

import java.io.IOException;
import java.net.Socket;

/**
 * ClientNetController use this class to try to connect to the server
 * @author Nathan Song
 * @version 2020-12-24
 */

public class ClientLoginConnector implements Runnable
{
    private LoginFrame LF;
    private ClientNetController CNC;
    private String IP;
    private int port;
    private Socket client;

    public ClientLoginConnector (LoginFrame LF, String IP, int port, ClientNetController CNC)
    {
        this.LF = LF;
        this.IP = IP;
        this.port = port;
        this.CNC = CNC;
    }

    @Override
    public void run ()
    {
        try
        {
            client = new Socket(IP, port);
            LF.ConnectTrue();
            CNC.SetSocket(client);
            CNC.SendLoginInfo();
        }
        catch (IOException e)
        {
            WarningFrame WF = new WarningFrame(LF, "提示", "无法连接到服务器！");
            LF.ConnectFalse();
        }
    }

    private void ShowExceptionAll (Exception e) // Create a Exception frame to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(LF, "程序异常", "程序发生异常！异常信息如下：", e);
    }
}
