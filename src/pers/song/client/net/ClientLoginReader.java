package pers.song.client.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Client uses this class to read login info from the server
 * @author Nathan Song
 * @version 2020-12-16
 */

public class ClientLoginReader implements Runnable
{
    private Window owner;
    private ClientNetController CNC;
    private Socket client;
    private BufferedReader BR;
    private Gson gson;

    public ClientLoginReader (Window owner, Socket client, ClientNetController CNC)
    {
        this.owner = owner;
        this.client = client;
        this.CNC = CNC;
        gson = new Gson();
    }

    @Override
    public void run ()
    {
        ReadMessage();
    }

    private void ReadMessage () // Read the login message from the server
    {
        try
        {
            String gsonContent;
            BR = new BufferedReader(new InputStreamReader(client.getInputStream()));
            gsonContent = BR.readLine();
            MessagePack MP = gson.fromJson(gsonContent, MessagePack.class);
            CNC.AnalysisMessage(MP);
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
    }

    private void ShowExceptionAll (Exception e) //  Create the exception window to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(owner, e);
    }
}
