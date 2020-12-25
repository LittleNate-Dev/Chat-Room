package pers.song.server.dao;

import pers.song.common.gui.ExceptionFrame;

import java.awt.*;
import java.sql.*;

/**
 * Use this class to control the database
 * @author Nathan Song
 * @version 2020-12-16
 */

public class DBController
{
    //region Controller parameters
    private String URL;
    private String userName;
    private String password;
    private Connection connection;
    private Statement statement;
    private ResultSet rs;
    private PreparedStatement ps;
    //endregion

    private String exceptionMessage;
    private Window owner;

    public DBController (String userName, String password)
    {
        URL = "jdbc:mysql://localhost:3306/csd?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
        this.userName = userName;
        this.password = password;
    }

    public void SetOwner (Window owner) // Set the owner of this DBC
    {
        this.owner = owner;
    }

    public String GetException () // Return the exception message
    {
        return exceptionMessage;
    }

    public boolean ConnectDB () // Connect to the database
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, userName, password);
            statement = connection.createStatement();
            return true;
        }
        catch (ClassNotFoundException e)
        {
            ShowExceptionAll(e);
            return false;
        }
        catch (SQLException throwables)
        {
            ShowExceptionAll(throwables);
            return false;
        }
    }

    public void  DisconnectDB ()
    {
        try
        {
            connection.close();
        }
        catch (SQLException throwables)
        {
            ShowExceptionAll(throwables);
            throwables.printStackTrace();
        }
    }

    public boolean CheckLogin (String userName, String password)
    {
        try
        {
            ps = connection.prepareStatement("select password from user_info where name = ?");
            ps.setString(1, userName);
            rs = ps.executeQuery();
            String getPassword = "";
            try
            {
                while (rs.next())
                {
                    getPassword = rs.getString(1);
                }
            }
            catch (SQLException throwables)
            {
                ShowExceptionAll(throwables);
            }
            if (password.equals(getPassword))
            {
                return  true;
            }
            else
            {
                System.out.println("deny");
                return false;
            }
        }
        catch (SQLException throwables)
        {
            ShowExceptionAll(throwables);
            return false;
        }
    }

    private void ShowExceptionAll (Exception e) // Use this method to show the exception message on a dialog
    {
        exceptionMessage = e.getMessage();
        if (!exceptionMessage.equals("Access denied for user '"+userName+"'@'localhost' (using password: YES)"))
        {
            ExceptionFrame EF = new ExceptionFrame(owner, "程序异常", "程序异常！异常信息如下：", e);
        }
    }
}
