package pers.song.client.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.Command;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Clients use this class to read message from the server
 * @author Nathan Song
 * @version 2020-12-18
 */

public class ClientReader implements Runnable
{
    private Window owner;
    private ClientNetController CNC;
    private Socket client;
    private BufferedReader BR;
    private Gson gson;

    public ClientReader (Window owner, Socket client, ClientNetController CNC)
    {
        this.owner = owner;
        this.client = client;
        this.CNC = CNC;
        gson = new Gson();
    }

    @Override
    public void run ()
    {
        while (true)
        {
            try
            {
                String gsonContent;
                BR = new BufferedReader(new InputStreamReader(client.getInputStream()));
                gsonContent = BR.readLine();
                MessagePack MP = gson.fromJson(gsonContent, MessagePack.class);
                if (MP != null)
                {
                    CNC.AnalysisMessage(MP);
                    if (MP.GetCommand() == Command.LOGOUT_REQUEST)
                    {
                        break;
                    }
                }
            }
            catch (IOException e)
            {
                ShowExceptionAll(e);
            }
        }
    }

    public void CloseReader () // Close the client reader
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

    private void ShowExceptionAll (Exception e) // Create a Exception frame to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(owner, e);
    }
}
