package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
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
                int time = set.getInt("time");
                String info = set.getString("info");
                String title = set.getString("title");
                String color = set.getString("color");

                //TODO create a state when member guild or channel is null
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null) return;
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null) return;

                if (getTimeBetweenCreationTimeAndNow(creationTime) % time == 0)
                {
                      RepeatInfoCommandManager.embeds.SendInfoEmbed(channel, color, title, info);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Calculate TimeLeft
    private long getTimeBetweenCreationTimeAndNow(String creationTimeString)
    {
        //get CreationTime
        LocalDateTime creationTime = LocalDateTime.parse(creationTimeString, formatter);

        //Get currentTime
        String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(formatter);
        LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);

        //Calculate timeleft
        Duration duration = Duration.between(creationTime, currentTime);
        long differentInHours = (duration.toMinutes());//if in toMinutes - 60 and not - 1!

        return differentInHours;
    }
}
