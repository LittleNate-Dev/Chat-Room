package pers.song.common.utlis.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * You can use this class to get the current time
 * @author Nathan Song
 * @version 2020-12-16
 */

public class CurrentTime
{
    public static String GetTime () // Get the current time, like "12-18 12:10:10"
    {
        String time;
        String pattern = "MM-dd HH:mm:ss z";
        SimpleDateFormat SDF = new SimpleDateFormat(pattern);
        time = SDF.format(new Date());
        time = time + "\n";
        return time;
    }
}
