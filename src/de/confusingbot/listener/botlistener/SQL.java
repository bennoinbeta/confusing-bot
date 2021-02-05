package de.confusingbot.listener.botlistener;

import de.confusingbot.manage.sql.LiteSQL;
import de.confusingbot.manage.sql.SQLManager;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void AddGuildToSQL(long guildid, String name)
    {
        //' will execute a near "s": syntax error
        name = name.replace("'", "");

        LiteSQL.onUpdate("INSERT INTO servers(guildid, name) VALUES (" +
                guildid + ", '" + name + "')");
    }

    public void RemoveGuildFromSQL(long guildid)
    {
        for (String name : SQLManager.tabelNames)
        {
            LiteSQL.onUpdate("DELETE FROM " + name + " WHERE "
                    + "guildid = " + guildid);
        }
    }
}