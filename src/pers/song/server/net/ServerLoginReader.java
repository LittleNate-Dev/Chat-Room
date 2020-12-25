package pers.song.server.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Server uses this class to read the login from clients
 * @author Nathan Song
 * @version 2020-12-16
 */

public class ServerLoginReader implements Runnable
{
    private Window owner;
    private ServerNetController SNC;
    private Socket client;
    private BufferedReader BR;
    private Gson gson;

    public ServerLoginReader (Window owner, Socket client, ServerNetController SNC)
    {
        this.owner = owner;
        this.client = client;
        this.SNC = SNC;
        gson = new Gson();
    }

    @Override
    public void run ()
    {
        ReadMessage();
    }

    private void ReadMessage ()
    {
        try
        {
            String gsonContent;
            BR = new BufferedReader(new InputStreamReader(client.getInputStream()));
            gsonContent = BR.readLine();
            MessagePack MP = gson.fromJson(gsonContent, MessagePack.class);
            SNC.SetClient(client);
            SNC.AnalysisMessage(MP);
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
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
