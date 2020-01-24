package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateRepeatInfo
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo");
            while (set.next())
            {
                long guildID = set.getLong("guildid");
                long channelID = set.getLong("channelid");
                int timeInHours = set.getInt("time");
                int id = set.getInt("id");
                String info = set.getString("info");
                String title = set.getString("title");
                String color = set.getString("color");


                //Check if RepeatInfo is sendable
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null)
                {
                    RepeatInfoCommandManager.sql.removeFormSQL(guildID, id);
                    return;
                }
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null)
                {
                    RepeatInfoCommandManager.sql.removeFormSQL(guildID, id);
                    return;
                }

                long differenceInMinutes = CommandsUtil.getTimeLeft(creationTime, timeInHours, false);

                if (differenceInMinutes % (timeInHours * 60) == 0)
                {
                    RepeatInfoCommandManager.embeds.SendInfoEmbed(channel, color, title, info);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
