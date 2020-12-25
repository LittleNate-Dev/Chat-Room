package pers.song.client.net;

import pers.song.client.gui.ChatFrame;
import pers.song.client.gui.LoginFrame;
import pers.song.common.gui.ExceptionFrame;
import pers.song.common.gui.WarningFrame;
import pers.song.common.utlis.pack.Command;
import pers.song.common.utlis.pack.MessagePack;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Client uses this class to communicate with server
 * @author Nathan Song
 * @version 2020-12-24
 */

public class ClientNetController
{
    private Window owner;
    private LoginFrame LF;
    private ChatFrame CF;
    private Map<String, String> userList;

    //region Net parameters
    private String userName;
    private String password;
    private Socket client;
    private int port;
    private ClientReader CR;
    private ClientLoginReader CLR;
    private Thread forCR;
    private Thread forCLR;
    //endregion

    public ClientNetController (Window owner)
    {
        this.owner = owner;
        port = 3060;
        userList = new HashMap<String, String>();
        userList.put("全体成员", "ALL");
    }

    public void ConnectServer (String IP) // Connect to the server
    {
        ClientLoginConnector CLC = new ClientLoginConnector(LF, IP, port, this);
        Thread forCLC = new Thread(CLC);
        forCLC.start();
    }

    public void DisconnectServer () // Disconnect to the server
    {
        ClientWriter CW = new ClientWriter(owner);
        CW.WriteSetLogout(client, userName);
        CW.StartWrite();
        CR.CloseReader();
        forCR.interrupt();
    }

    public void SendLoginInfo () // Send the login info to the server
    {
        MessagePack SMP = new MessagePack();
        SMP.SetCommand(Command.LOGIN_REQUEST);
        SMP.SetUserName(userName);
        SMP.SetPassword(password);
        ClientWriter CW = new ClientWriter(owner);
        CW.WriteSetLoginInfo(client, userName, password);
        Thread forCW = new Thread(CW);
        forCW.start();
        CLR = new ClientLoginReader(owner, client, this);
        forCLR = new Thread(CLR);
        forCLR.start();
    }

    public void AnalysisMessage (MessagePack MP) // Analysis message received from the server
    {
        switch (MP.GetCommand())
        {
            //region case LOGIN_DENY
            case LOGIN_DENY:
                WarningFrame WF_DENY = new WarningFrame(owner, "提示", "用户名或密码错误");
                break;
            //endregion

            //region case LOGIN_GRANTED
            case LOGIN_GRANTED:
                LF.SetChatFrame();
                CR = new ClientReader(owner, client, this);
                forCR = new Thread(CR);
                forCR.start();
                CF.setTitle("微薪-" + userName);
                break;
            //endregion

            //region case LOGIN_EXIST
            case LOGIN_EXIST:
                WarningFrame WF_EXIST = new WarningFrame(owner, "提示", "不能重复登录！");
                break;
            //endregion

            //region case LOGOUT_REQUEST
            case LOGOUT_REQUEST:
                CF.ReturnToLogin();
                userList.clear();
                userList.put("全体成员", "ALL");
                try
                {
                    client.close();
                }
                catch (IOException e)
                {
                    ShowExceptionAll(e);
                }
                break;
            //endregion

            //region case ADD_USER
            case ADD_USER:
                AddUser(MP);
                break;
            //endregion

            //region case GET_USER
            case GET_USER:
                SetExistedUser(MP);
                break;
            //endregion

            //region case SEND_MESSAGE
            case SEND_MESSAGE:
                CF.UpdateMessage(MP);
                break;
            //endregion

            //region case DELETE_USER
            case DELETE_USER:
                DeleteUser(MP);
                break;
            //endregion
        }
    }

    private void AddUser (MessagePack MP) // When receive the ADD_USER command, use this method to add the new user
    {
        userList.put(MP.GetUserName(), MP.GetUserName());
        CF.UpdateTable(userList);
    }

    public void GetExistedUser () // When the login succeed, use this method to get the users in the server
    {
        ClientWriter CW = new ClientWriter(owner);
        CW.WriteSetGetUser(client, userName);
        CW.StartWrite();
    }

    public void SendMessage (String message, String receiver) // Use this method to send message to the server
    {
        ClientWriter CW = new ClientWriter(owner);
        CW.WriteSetSendMessage(client, message, userName, receiver);
        CW.StartWrite();
    }

    private void DeleteUser (MessagePack MP) // When receive the DELETE_USER command, use this method to delete the logout user
    {
        userList.remove(MP.GetUserName());
        CF.UpdateTable(userList);
    }

    private void SetExistedUser (MessagePack MP) // Update the user list
    {
        for (String userName : MP.GetUserList().keySet())
        {
            userList.put(userName, userName);
        }
        CF.UpdateTable(userList);
    }

    public void SetLoginFrame (LoginFrame LF) // Use this method to set the owner of this object
    {
        this.LF = LF;
    }

    public void SetChatFrame (ChatFrame CF) // Use this method to set the owner of this object
    {
        this.CF = CF;
    }

    public void SetOwner (Window owner) // Use this method to set the owner of this object
    {
        this.owner = owner;
    }

    public void SetUser (String userName, String password) // Set the user of this client
    {
        this.userName = userName;
        this.password = password;
    }

    public void SetSocket (Socket client) // Use this method to set the socket
    {
        this.client = client;
    }

    public String GetUserName () // Return the user name of this client
    {
        return userName;
    }

    private void ShowExceptionAll (Exception e) // Create a Exception frame to show the exception
    {
        ExceptionFrame EF = new ExceptionFrame(owner, "程序异常", "程序发生异常！异常信息如下：", e);
    }
}
