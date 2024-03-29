package de.confusingbot.commands.cmds.admincmds.eventcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "event\n``` " +
                "```fix\n" + EventCommandManager.permission.name() + "\n```" +
                "```Create awesome Events!```\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "event create [#channel] [messageid] ([color]) [time] [takePartEmote] [eventName] (ROLE:[eventRoleName])\n```"
                        + "```You can create awesome Event's!```\n"
                        + "```yaml\n" + Main.prefix + "event remove [@eventRole]\n```"
                        + "```Remove Event which is connected with this role!```\n"
                        + "```yaml\n" + Main.prefix + "event announcement [#channel] [@eventRole] ([Title] MESSAGE:) [Message]\n```"
                        + "```Create a Announcement for all EventMembers!```\n"
                        + "```yaml\n" + Main.prefix + "event list\n```"
                        + "```List all active Events of this guild!```\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "event create [#channel] [messageid] ([color]) [time] [takePartEmote] [eventName] (ROLE:[eventRoleName])\n```"
                + "```You can create awesome Event's!```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "event remove [@eventRole]\n```"
                + "```Remove Event which is connected with this role!```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void AnnouncementUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "event announcement [#channel] [@eventRole] ([Title] MESSAGE:) [Message]\n```"
                + "```Create a Announcement for all EventMembers!```\n", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================

    public void NoValidIdError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void NoValidEmoteError(TextChannel channel, String emote)
    {
        EmbedManager.SendErrorEmbed("`" + emote + "` is no valid emote!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedNamesError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned the EventName or the RoleName!", channel, EmbedsUtil.showErrorTime);
    }

    public void noMentionedTimeError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a valid time!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTextChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a TextChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoExistingEventRoleError(TextChannel channel, String roleMentioned)
    {
        EmbedManager.SendErrorEmbed(roleMentioned + " is no EventRole!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "role");
    }

    public void NoMentionedEventRoleError(TextChannel channel, String role)
    {
        EmbedManager.SendErrorEmbed(role + " is no EventRole!", channel, EmbedsUtil.showErrorTime);
    }

    public void YouHaveNotMentionedAValidEmoteError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned valid a Emote!", channel, EmbedsUtil.showErrorTime);
    }


    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedEvent(TextChannel channel, Color color, String eventName)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Event");
        builder.setColor(color);
        builder.setDescription("Event called `" + eventName + "` has been created!");

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedEvent(TextChannel channel, Color color, String eventName)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Event");
        builder.setColor(color);
        builder.setDescription("Event called `" + eventName + "` has been removed!");

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void HasNoEventsInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no Events**! \nYou can create Events with`" + Main.prefix + "event create`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendListEmbed(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51Events: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedManager.SendCustomEmbedGetMessageID("Please Wait", "This needs upto 10s!", Color.pink, channel);
    }

    public void SendEventEndEmbed(TextChannel channel, String eventName, String color)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(color));
        builder.setTitle("\uD83D\uDC51Events Ends!");
        builder.setDescription("**" + eventName + "** ends!");

        EmbedManager.SendEmbed(builder, channel, 0);
    }


    public void SendAnnouncement(String title, String message, Color color, TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(color);
        builder.setTitle(title);
        builder.setDescription(message);

        EmbedManager.SendEmbed(builder, channel, 0);
    }
}
