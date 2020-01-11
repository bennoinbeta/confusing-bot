package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEvents
{
    //Called all 5Min
    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM event");
            while (set.next())
            {
                long id = set.getLong("id");
                long guildid = set.getLong("guildid");
                long channelid = set.getLong("channelid");
                long messageid = set.getLong("messageid");
                long roleid = set.getLong("roleid");
                String hexColor = set.getString("color");
                String eventName = set.getString("name");

                int endTime = set.getInt("time");
                String creationTime = set.getString("creationtime");

                long timeleft = CommandsUtil.getTimeLeftInHours(creationTime, endTime);
                //long timeleft = CommandsUtil.getTimeLeftInMinutes(creationTime, endTime);

                if (timeleft <= 0)
                {
                    //SQL
                    EventCommandManager.sql.removeFromSQL(guildid, id);

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildid);
                    if (guild != null)
                    {
                        //Remove EventRole
                        Role eventRole = guild.getRoleById(roleid);
                        if (eventRole != null) eventRole.delete().queue();

                        TextChannel channel = guild.getTextChannelById(channelid);
                        if (channel != null)
                        {
                            Message message = CommandsUtil.getLatestesMessageByID(channel, messageid);
                            if (message != null)
                            {
                                //Show that the vote has ended
                                CommandsUtil.reactEmote("❌", channel, messageid, true);

                                //Message
                                EventCommandManager.embeds.SendEventEndEmbed(channel, eventName, hexColor);
                            }
                        }
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}