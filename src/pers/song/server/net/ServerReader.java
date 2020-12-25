package pers.song.server.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.Command;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Server uses this class to read message from clients
 * @author Nathan Song
 * @version 2020-12-18
 */

public class ServerReader implements Runnable
{
    private Window owner;
    private ServerNetController SNC;
    private Socket client;
    private BufferedReader BR;
    private Gson gson;

    public ServerReader (Window owner, Socket client, ServerNetController SNC)
    {
        this.owner = owner;
        this.client = client;
        this.SNC = SNC;
        gson = new Gson();
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                String gsonContent;
                BR = new BufferedReader(new InputStreamReader(client.getInputStream()));
                gsonContent = BR.readLine();
                MessagePack MP = gson.fromJson(gsonContent, MessagePack.class);
                SNC.SetClient(client);
                SNC.AnalysisMessage(MP);
                if (MP.GetCommand() == Command.LOGOUT_REQUEST)
                {
                    break;
                }
            }
            catch (IOException e)
            {
                ShowExceptionAll(e);
            }
        }
    }

    public void CloseReader ()
    {
        try
        {
            BR.close();
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
    }

    private void ShowExceptionAll (Exception e)
    {
        ExceptionFrame EF = new ExceptionFrame(owner, "程序异常", "程序发生异常！异常信息如下：", e);
    }
}
