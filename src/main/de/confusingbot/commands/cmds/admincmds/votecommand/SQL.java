package main.de.confusingbot.commands.cmds.admincmds.votecommand;

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
    public boolean ExistInSQL(long guildid, long channelID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM votecommand WHERE " +
                "guildid = " + guildid);

        try
        {
            if (set.next()) return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long channelID, long messageid, long endTime, String creationtime)
    {
        LiteSQL.onUpdate("INSERT INTO votecommand(guildid, channelid, messageid, endTime, creationtime) VALUES(" +
                guildid + ", " + channelID + ", " + messageid + ", " + endTime +", '" + creationtime + "')");
    }

    public void removeFromSQL(long guildid, long id)
    {
        LiteSQL.onUpdate("DELETE FROM votecommand WHERE "
                + "id = " + id
                + " AND guildid = " + guildid);
    }
}
