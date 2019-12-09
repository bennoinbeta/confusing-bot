package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelid, int time, String color, String title, String info)
    {
        LiteSQL.onUpdate("INSERT INTO repeatinfo(guildid, channelid, time, color, title, info) VALUES(" +
                guildid + ", " + channelid + ", " + time + ", '" + color + "', '" + title + "', '" + info +"')");
    }

    public void removeFormSQL(long guildid, int id)
    {
        LiteSQL.onUpdate("DELETE FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);
    }

    public List<Integer> getRepeatInfoIDs(long guildid)
    {
        List<Integer> list = new ArrayList<>();

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid);

            while (set.next())
            {
                list.add(set.getInt("id"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public String getRepeatInfoByID(long guildid, int id)
    {
        String repeatInfoString = "error";

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid
                    + " AND id = " + id);

            while (set.next())
            {
                repeatInfoString = set.getString("info");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return repeatInfoString;
    }

    public ResultSet GetRepeatInfoResultSet(long guildid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid);

        return set;
    }
}
