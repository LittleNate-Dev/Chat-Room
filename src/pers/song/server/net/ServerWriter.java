package pers.song.server.net;

import com.google.gson.Gson;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.Command;
import pers.song.common.utlis.pack.MessagePack;
import pers.song.server.gui.ServerFrame;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Server uses this class to write message to clients
 * @author Nathan Song
 * @version 2020-12-18
 */

public class ServerWriter implements Runnable
{
    private ServerFrame owner;
    private MessagePack MP;
    private Socket client;
    private PrintWriter PW;
    private Gson gson;

    public ServerWriter (ServerFrame owner)
    {
        this.owner = owner;
        gson = new Gson();
    }

    @Override
    public void run()
    {
        StartWrite();
    }

    public void StartWrite()
    {
        try
        {
            PW = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            PW.println(gson.toJson(MP));
            PW.flush();
            if(MP.GetCommand() == Command.LOGOUT_REQUEST)
            {
                client.shutdownOutput();
            }
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
    }

    public void WriteSetLoginDeny (Socket client)
    {
        this.client = client;
        MP = new MessagePack(Command.LOGIN_DENY);
    }

    public void WriteSetLoginGranted (Socket client)
    {
        this.client = client;
        MP = new MessagePack(Command.LOGIN_GRANTED);
    }

    public void WriteSetLoginExist (Socket client)
    {
        this.client = client;
        MP = new MessagePack(Command.LOGIN_EXIST);
    }

    public void WriteSetAddUser (Socket client, String userName)
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.ADD_USER);
        MP.SetUserName(userName);
    }

    public void WriteSetGetUser (Socket client, Map<String, String> userList)
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.GET_USER);
        MP.SetUserList(userList);
    }

    public void WriteSetSendMessage (Socket client, MessagePack MP)
    {
        this.client = client;
        this.MP = MP;
    }

    public void WriteSetDeleteMessage (Socket client, String deleteUserName)
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.DELETE_USER);
        MP.SetUserName(deleteUserName);
    }

    public void WriteSetLogout (Socket client)
    {
        this.client = client;
        MP = new MessagePack();
        MP.SetCommand(Command.LOGOUT_REQUEST);
    }

    private void ShowExceptionAll (Exception e)
    {
        ExceptionFrame EF = new ExceptionFrame(owner, e);
    }
}
