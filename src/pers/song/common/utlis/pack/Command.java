package pers.song.common.utlis.pack;

/**
 * This enum contains the command between the server and clients
 * @author Nathan Song
 * @version 2020-12-16
 */

public enum Command
{
    LOGIN_REQUEST, LOGIN_DENY, LOGIN_GRANTED, LOGIN_EXIST, SEND_MESSAGE, LOGOUT_REQUEST, ADD_USER, DELETE_USER, GET_USER
}
