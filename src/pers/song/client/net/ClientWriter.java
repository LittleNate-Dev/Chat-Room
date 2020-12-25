package pers.song.client.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.Command;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Clients use this class to send message to the server
 * @author Nathan Song
 * @version 2020-12-17
 */

public class ClientWriter implements Runnable
{
    private Window owner;
    private Socket client;
    private MessagePack MP;
    private PrintWriter PW;
    private Gson gson;

    public ClientWriter (Window owner)
    {
        this.owner = owner;
        gson = new Gson();
    }

    @Override
    public void run ()
    {
        StartWrite();
    }

    public void StartWrite () // Start writing procedure
    {
        try
        {
            PW = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            PW.println(gson.toJson(MP));
            PW.flush();
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
    }

    public void WriteSetLoginInfo (Socket client, String userName, String password) // Write Login info to the server
    {
        this.client = client;
        MP = new MessagePack(Command.LOGIN_REQUEST);
        MP.SetUserName(userName);
        MP.SetPassword(password);
    }

    public void WriteSetLogout (Socket client, String userName) // Write Logout info to the server
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.LOGOUT_REQUEST);
        MP.SetUserName(userName);
    }

    public void WriteSetGetUser (Socket client, String userName) // Write GetUser info to the server
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.GET_USER);
        MP.SetUserName(userName);
    }

    public void WriteSetSendMessage (Socket client, String message, String userName, String receiver) // Set the message that will send to the server
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.SEND_MESSAGE);
        MP.SetMessage(message);
        MP.SetUserName(userName);
        MP.SetReceiver(receiver);
        MP.SetSendTime();
    }

    private void ShowExceptionAll (Exception e) // Create a Exception frame to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(owner, "程序异常", "程序发生异常！异常信息如下：", e);
    }
}
