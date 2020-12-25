package pers.song.common.utlis.pack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class encapsulate the content of the message
 * @author Nathan Song
 * @version 2020-12-17
 */

public class MessagePack
{
    private Command command;
    private String message;
    private String userName;
    private String password;
    private String receiver;
    private String sendTime;
    private Map<String, String> userList;

    public MessagePack ()
    {
        message = "";
        userName = "";
        password = "";
        receiver = "";
        sendTime = "";
        userList = new HashMap<String, String>();
    }

    public MessagePack (Command command)
    {
        this.command = command;
        switch (command)
        {
            case LOGIN_DENY:
                SetLoginDeny();
                break;
            case LOGIN_GRANTED:
                SetLoginGranted();
                break;
            case LOGIN_REQUEST:
                SetLoginRequest();
                break;
        }
    }

    public void SetCommand (Command command)
    {
        this.command = command;
    }

    public Command GetCommand ()
    {
        return command;
    }

    public void SetMessage (String message)
    {
         this.message = message;
    }

    public String GetMessage ()
    {
        return message;
    }

    public void SetUserName (String userName)
    {
        this.userName = userName;
    }

    public String GetUserName ()
    {
        return userName;
    }

    public void SetPassword (String password)
    {
        this.password = password;
    }

    public String GetPassword ()
    {
        return password;
    }

    public void SetReceiver (String receiver)
    {
        this.receiver = receiver;
    }

    public String GetReceiver ()
    {
        return receiver;
    }

    public void SetSendTime ()
    {
        String pattern = "MM-dd HH:mm:ss z";
        SimpleDateFormat SDF = new SimpleDateFormat(pattern);
        sendTime = SDF.format(new Date());
    }

    public String GetSendTime ()
    {
        return sendTime;
    }

    public void SetUserList (Map<String, String> userList)
    {
        this.userList = userList;
    }

    public Map<String, String> GetUserList ()
    {
        return userList;
    }

    public String GetFullMessage () // Return the message content
    {
        String fullMessage;
        fullMessage = userName + " " + sendTime + "\n" + message + "\n\n";
        return fullMessage;
    }

    public String GetFullMessageAt () // Return the message content but with the receiver
    {
        String fullMessage;
        fullMessage = userName + " " + sendTime + "\n" + "@我的" + " " + message + "\n\n";
        return fullMessage;
    }

    public void SetLoginDeny ()
    {
        command = Command.LOGIN_DENY;
        message = "";
        userName = "";
        password = "";
        receiver = "";
        sendTime = "";
    }

    public void SetLoginGranted ()
    {
        command = Command.LOGIN_GRANTED;
        message = "";
        userName = "";
        password = "";
        receiver = "";
        sendTime = "";
    }

    public void SetLoginRequest ()
    {
        command = Command.LOGIN_REQUEST;
        message = "";
        userName = "";
        password = "";
        receiver = "";
        sendTime = "";
    }
}
