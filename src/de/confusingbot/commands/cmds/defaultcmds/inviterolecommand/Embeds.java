package de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "invite```"
                + "```Get special roles by inviting as many people as possible^^```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "invite add [@role] [inviteCount]\n```"
                        + "```Add @role to the InviteRole```"
                        + "```yaml\n" + Main.prefix + "invite remove [@role]\n```"
                        + "```Remove @role from the InviteRoles```"
                        + "```yaml\n" + Main.prefix + "invite info\n```"
                        + "```Shows you how to get a special InviteRole on this server```"
                        + "```yaml\n" + Main.prefix + "invite stats\n```"
                        + "```Shows your invite stats```"
                        + "```yaml\n" + Main.prefix + "invite leaderboard\n```"
                        + "```Shows you the top inviter of this server```", channel, EmbedsUtil.showUsageTime);
    }

    public void InviteAddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "invite add [@role] [inviteCount]\n```"
                + "```Add @role to the InviteRole```", channel, EmbedsUtil.showUsageTime);
    }

    public void InviteRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "invite remove [@role]\n```"
                + "```Remove @role from the InviteRoles```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }


    public void RoleDoesNotExistError(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The role with the id " + roleid + " doesn't exist on this server!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void NoNumberError(TextChannel channel, String numberString)
    {
        EmbedsUtil.NoNumberError(channel, numberString);
    }

    public void InviteRoleAlreadyExists(TextChannel channel, String role)
    {
        EmbedManager.SendErrorEmbed(role + " is already an InviteRole", channel, EmbedsUtil.showErrorTime);
    }

    public void RoleIsNoInviteRoleError(TextChannel channel, String role)
    {
        EmbedManager.SendErrorEmbed(role + " is no InviteRole!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedInviteRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You successfully added " + role.getAsMention() + " to the InviteRoles", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedInviteRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You successfully removed " + role.getAsMention() + " from the InviteRoles", channel, EmbedsUtil.showSuccessTime);
    }


    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendInviteLeaderBoard(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a1f542"));
        builder.setTitle("LeaderBoard");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

    public void SendInfo(TextChannel channel, String list)
    {
        EmbedBuilder builder = new EmbedBuilder();

        String listString;
        if (list != null && !list.equals(""))
        {
            listString = list;
        }
        else
        {
            listString = "This server does not support invite roles!";
        }

        builder.setTitle("\uD83D\uDD0EInformation");
        builder.setColor(Color.decode("#60f4b4"));
        builder.setDescription("**Do you want to get special roles?** \n" + listString + "\n\n **Than create a infinity InviteLink and invite as many people as possible\uD83D\uDCDE**\n (Note that temporary invites won't count\uD83E\uDD14)");

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 30);
    }

    public void SendListEmbed(TextChannel channel, String list)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("InviteRoleList");
        builder.setColor(Color.decode("#60f4b4"));
        builder.setDescription(list);

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

    public void SendUserInviteStats(TextChannel channel, int uses, LocalDateTime creationDate, Member member, String inviteLink, Invite.Channel inviteChannel)
    {
        DateTimeFormatter creationDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String creationDateString = creationDateFormatter.format(creationDate);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#60f4b4"));
        builder.setTitle("Invite Stats from " + member.getEffectiveName());
        builder.addField("Uses", Integer.toString(uses), true);
        builder.addField("Creation Date", creationDateString, true);
        builder.addField("InviteLink", inviteLink, true);
        builder.addField("TextChannel", inviteChannel.getName(), true);

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 30);
    }

}
