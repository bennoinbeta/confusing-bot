package main.de.confusingbot.commands.help;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandsUtil
{

    public static String[] messageToArgs(Message message)
    {
        String m = message.getContentDisplay();
        m.replace("   ", " ");
        m.replace("  ", " ");
        String[] args = m.substring(Main.prefix.length()).split(" ");

        return args;
    }

    public static boolean isNumeric(String strNum)
    {
        try
        {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException | NullPointerException nfe)
        {
            return false;
        }
        return true;
    }

    public static boolean isColor(String hexColor)
    {
        try
        {
            Color color = Color.decode(hexColor);
            return true;
        } catch (NumberFormatException e)
        {

        }
        return false;
    }

    public static List<String> getEmotes(Message message, List<String> emoteString)
    {
        List<Emote> emotesInMessage = message.getEmotes();
        List<String> customemotes = new ArrayList<>();

        List<String> emotes = new ArrayList<>();

        for (Emote emote : emotesInMessage)
        {
            emotes.add(String.valueOf(emote.getIdLong()));
            customemotes.add(":" + emote.getName() + ":");
        }

        for (int i = 0; i < emoteString.size(); i++)
        {
            String emote = emoteString.get(i);
            if (!customemotes.contains(emote))
            {
                emotes.add(emote);
            }
        }
        return emotes;
    }

    public static void reactEmote(String emoteString, TextChannel channel, long messageid, boolean add)
    {
        if (CommandsUtil.isNumeric(emoteString))//if emoteString is a emoteID
        {
            Emote emote = channel.getGuild().getEmoteById(Long.parseLong(emoteString));
            if (add)
                channel.addReactionById(messageid, emote).queue();
            else
                channel.removeReactionById(messageid, emote).queue();
        }
        else
        {
            if (add)
                channel.addReactionById(messageid, emoteString).queue();
            else
                channel.removeReactionById(messageid, emoteString).queue();
        }
    }

    public static List<Long> getLatestMessages(MessageChannel channel)
    {
        List<Long> messages = new ArrayList<>();

        for (Message message : channel.getIterableHistory().cache(false))
        {
            messages.add(message.getIdLong());
        }
        return messages;
    }

    public static void AddOrRemoveRoleFromAllMembers(Guild guild, long roleid, boolean add){
        List<Member> members = guild.getMembers();

        Role role = guild.getRoleById(roleid);
        if(role == null) return;

        for(Member member : members){
            if(!member.getUser().isBot()){
                if(add)
                    guild.addRoleToMember(member, role).queue();
                else
                    guild.removeRoleFromMember(member, role).queue();
            }
        }
    }

    //Calculate TimeLeft
    public static long getTimeLeft(String creationTimeString, int endTime)
    {
        long timeLeft = -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get CreationTime
        LocalDateTime creationTime = LocalDateTime.parse(creationTimeString, formatter);

        //Get currentTime
        String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(formatter);
        LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);

        //Calculate timeleft
        Duration duration = Duration.between(creationTime, currentTime);
        long differentInHours = (duration.toHours());
        //System.out.println("Different in minutes " + differentInHours);
        timeLeft = endTime - differentInHours;

        return timeLeft;
    }
}

