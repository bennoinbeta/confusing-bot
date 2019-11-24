package main.de.confusingbot.listener.botlistener;

import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class BotListener extends ListenerAdapter {

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        System.out.println("Bot joined server " + event.getGuild().getName());

        //SQL
        sql.AddGuildToSQL(event.getGuild().getIdLong(), event.getGuild().getName());

        //Message
        EmbedBuilder builder = embeds.BotJoinEmbed();

        //Send private Message
        event.getGuild().getOwner().getUser().openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(builder.build()).queue();
        });

        //Send in the default Channel if bot has permission
        try {
            event.getGuild().getDefaultChannel().sendMessage(builder.build()).queue();
        } catch (InsufficientPermissionException e) {
            //no permission in this channel
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        System.out.println("Bot left server " + event.getGuild().getName());

        //SQL
        sql.RemoveGuildFromSQL(event.getGuild().getIdLong());
    }


}