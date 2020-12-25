package pers.song.server.net;

import pers.song.common.gui.ExceptionFrame;
import pers.song.common.utlis.pack.MessagePack;
import pers.song.common.utlis.time.CurrentTime;
import pers.song.server.dao.DBController;
import pers.song.server.gui.ServerFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Server uses this class to communicate with clients
 * @author Nathan Song
 * @version 2020-12-22
 */

public class ServerNetController
{
    private DBController DBC;
    private ServerFrame owner;
    private Map<String, Socket> userList;
    private Map<String, Thread> threadList;
    private File file;
    private FileOutputStream FOS;
    private OutputStreamWriter LOGOS;

    //region Net parameters
    private ServerSocket server;
    private Socket client;
    private LoginListener LL;
    private int clientCounter;
    //endregion

    public ServerNetController (ServerFrame owner, DBController DBC)
    {
        this.owner = owner;
        this.DBC = DBC;
        userList = new HashMap<String, Socket>();
        threadList = new HashMap<String, Thread>();
        clientCounter = 0;
    }

    public void StartServer () // Power on the server
    {
        try
        {
            file = new File("Server_LOG.txt");
            FOS = new FileOutputStream(file, true);
            LOGOS = new OutputStreamWriter(FOS, "utf-8");
        }
        catch (FileNotFoundException e)
        {
            ShowExceptionAll(e);
        }
        catch (UnsupportedEncodingException e)
        {
            ShowExceptionAll(e);
        }
        try
        {
            server = new ServerSocket(3060);
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
        LL = new LoginListener(owner, server,this);
        Thread forLL = new Thread(LL);
        forLL.start();
        clientCounter = 0;
        owner.AppendMessageContent(CurrentTime.GetTime() + "服务器启动成功\n\n");
        WriteLog(CurrentTime.GetTime() + "服务器启动成功\n\n");
    }

    public void CloseServer () // Power off the server
    {
        SendLogout();
        try
        {
            if (server != null)
            {
                server.close();
            }
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
        if (LL != null)
        {
            LL.CloseListener();
        }
        clientCounter = 0;
        owner.AppendMessageContent(CurrentTime.GetTime() +  "服务器关闭成功\n\n");
        WriteLog(CurrentTime.GetTime() +  "服务器关闭成功\n\n");
        try
        {
            LOGOS.close();
            FOS.close();
        }
        catch (IOException e)
        {
            ShowExceptionAll(e);
        }
    }

    public void AnalysisMessage (MessagePack MP) // Analysis message received from clients
    {
        switch (MP.GetCommand())
        {
            //region case LOGIN_REQUEST
            case LOGIN_REQUEST:
                owner.AppendMessageContent(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求登录\n\n");
                WriteLog(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求登录\n\n");
                if (DBC.CheckLogin(MP.GetUserName(), MP.GetPassword()))
                {
                    if (!userList.containsKey(MP.GetUserName()))
                    {
                        ServerWriter SW = new ServerWriter(owner);
                        SW.WriteSetLoginGranted(client);
                        Thread forSW = new Thread(SW);
                        forSW.start();
                        clientCounter = clientCounter + 1;
                        owner.UpdateConnectNumber(Integer.toString(clientCounter));
                        owner.UpdateUserList(MP.GetUserName(), client.getInetAddress().toString());
                        userList.put(MP.GetUserName(), client);
                        ServerReader SR = new ServerReader(owner, client, this);
                        Thread forSR = new Thread(SR);
                        forSR.setName(MP.GetUserName());
                        threadList.put(MP.GetUserName(), forSR);
                        forSR.start();
                        owner.AppendMessageContent(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 已登录服务器\n\n");
                        WriteLog(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 已登录服务器\n\n");
                        SendAddUser(MP);
                    }
                    else
                    {
                        ServerWriter SW = new ServerWriter(owner);
                        SW.WriteSetLoginExist(client);
                        Thread forSW = new Thread(SW);
                        forSW.start();
                        owner.AppendMessageContent(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求拒绝\n\n");
                        WriteLog(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求拒绝\n\n");
                    }
                }
                else
                {
                    ServerWriter SW = new ServerWriter(owner);
                    SW.WriteSetLoginDeny(client);
                    Thread forSW = new Thread(SW);
                    forSW.start();
                    owner.AppendMessageContent(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求拒绝\n\n");
                    WriteLog(CurrentTime.GetTime() + MP.GetUserName() + " " + client.getInetAddress() +" 请求拒绝\n\n");
                }
                break;
            //endregion

            //region case GET_USER
            case GET_USER:
                SendExistedUser(MP);
                break;
            //endregion

            //region case LOGOUT_REQUEST
            case LOGOUT_REQUEST:
                owner.AppendMessageContent(CurrentTime.GetTime() + MP.GetUserName() + "请求退出\n\n");
                WriteLog(CurrentTime.GetTime() + MP.GetUserName() + "请求退出\n\n");
                SendDeleteUser(MP.GetUserName());
                Socket removeClient = userList.get(MP.GetUserName());
                userList.remove(MP.GetUserName());
                try
                {
                    removeClient.close();
                    clientCounter = clientCounter - 1;
                    owner.UpdateConnectNumber(Integer.toString(clientCounter));
                    Thread removeThread = threadList.get(MP.GetUserName());
                    removeThread.interrupt();
                    owner.ResetTable(userList);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            //endregion

            //region case SEND_MESSAGE
            case SEND_MESSAGE:
                SendMessage(MP);
                owner.AppendMessageContent(MP.GetSendTime() + " " + MP.GetUserName() + " TO " + MP.GetReceiver() + "\n" + MP.GetMessage() + "\n\n");
                WriteLog(MP.GetSendTime() + " " + MP.GetUserName() + " TO " + MP.GetReceiver() + "\n" + MP.GetMessage() + "\n\n");
                break;
            //endregion
        }
    }

    private void SendExistedUser (MessagePack MP) // Send the Existed users to the new client
    {
        Map<String, String> sendUserList = new HashMap<String, String>();
        for (String userName : userList.keySet())
        {
            if(!userName.equals(MP.GetUserName()))
            {
                sendUserList.put(userName, userName);
            }
        }
        ServerWriter SW = new ServerWriter(owner);
        SW.WriteSetGetUser(userList.get(MP.GetUserName()), sendUserList);
        SW.StartWrite();
    }

    private void SendAddUser (MessagePack MP) // Add the new client to the user list
    {
        for (String userName : userList.keySet())
        {
            if(!userName.equals(MP.GetUserName()))
            {
                ServerWriter SW = new ServerWriter(owner);
                SW.WriteSetAddUser(userList.get(userName), MP.GetUserName());
                SW.StartWrite();
            }
        }
    }

    private void SendMessage (MessagePack MP) // Deliver the message to the client
    {
        if (MP.GetReceiver().equals("全体成员"))
        {
            for (String userName : userList.keySet())
            {
                if(!userName.equals(MP.GetUserName()))
                {
                    ServerWriter SW = new ServerWriter(owner);
                    SW.WriteSetSendMessage(userList.get(userName), MP);
                    SW.StartWrite();
                }
            }
        }
        else
        {
            for (String userName : userList.keySet())
            {
                if(userName.equals(MP.GetReceiver()))
                {
                    ServerWriter SW = new ServerWriter(owner);
                    SW.WriteSetSendMessage(userList.get(userName), MP);
                    SW.StartWrite();
                }
            }
        }
    }

    private void SendDeleteUser (String deleteUserName) // Send DELETE_USER command to clients
    {
        for (String userName : userList.keySet())
        {
            if(!userName.equals(deleteUserName))
            {
                ServerWriter SW = new ServerWriter(owner);
                SW.WriteSetDeleteMessage(userList.get(userName), deleteUserName);
                SW.StartWrite();
            }
        }
    }

    private void SendLogout () // When the server is power off, notify client that server is closed
    {
        for (String userName : userList.keySet())
        {
            ServerWriter SW = new ServerWriter(owner);
            SW.WriteSetLogout(userList.get(userName));
            SW.StartWrite();
        }
    }

    public void SetClient (Socket client)
    {
        this.client = client;
    }

    public void CLearUserList ()
    {
        userList.clear();
    }

    public void ClearThreadList ()
    {
        threadList.clear();
    }

    private void WriteLog (String log) // Write the log to the Server_LOG.txt
    {
        try
        {
            LOGOS.write(log);
            LOGOS.flush();
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
