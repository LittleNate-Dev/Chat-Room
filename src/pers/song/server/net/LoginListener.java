package pers.song.server.net;

import pers.song.common.gui.ExceptionFrame;
import pers.song.server.gui.ServerFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This thread will listen the connect from clients
 * @author Nathan Song
 * @version 2020-12-16
 */

public class LoginListener implements Runnable
{
    private ServerNetController SNC;
    private ServerFrame owner;

    //region Net parameters
    private ServerSocket server;
    private Socket client;
    //endregion

    public LoginListener (ServerFrame owner, ServerSocket server, ServerNetController SNC)
    {
        this.owner = owner;
        this.server = server;
        this.SNC = SNC;
    }

    @Override
    public void run ()
    {
        while (!server.isClosed())
        {
            try
            {
                client = server.accept();
                SNC.SetClient(client);
                ServerLoginReader SLR = new ServerLoginReader(owner, client, SNC);
                Thread forSLR = new Thread(SLR);
                forSLR.start();
            }
            catch (IOException e)
            {
                if(!e.getMessage().equals("Socket closed"))
                {
                    ShowExceptionAll(e);
                }
            }
        }
    }

    public void CloseListener ()
    {
        try
        {
            server.close();
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
        owner.UpdateConnectNumber("0");
    }

    private void ShowExceptionAll (Exception e)
    {
        ExceptionFrame EF = new ExceptionFrame(owner, "程序异常", "程序发生异常！异常信息如下：", e);
    }
}
