package de.confusingbot.commands.cmds.admincmds.rolebordercommand;

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
        HelpManager.admin.add("```yaml\n" + Main.prefix + "roleborder\n``` " +
                "```Create RoleBorders which can be used as an seperator between roles```" +
                "```fix\n" + RoleBorderCommandManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=3)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "roleborder create [roleName]\n```"
                        + "```Create a RoleBorder which can be used as an seperator between roles```"
                        + "```yaml\n" + Main.prefix + "roleborder add [@role]\n```"
                        + "```Add @role to the RoleBorders```"
                        + "```yaml\n" + Main.prefix + "roleborder remove [@role]\n```"
                        + "```Remove the @role form the roleborders```"
                        + "```yaml\n" + Main.prefix + "roleborder list\n```"
                        + "```List all roleborders of this server```", channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "roleborder add [@role]\n```"
                + "```Add @role to the RoleBorders```", channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "roleborder create [roleName]\n```"
                + "```Create a RoleBorder which can be used as an seperator between roles```", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```Remove the @role form the roleborders```"
                + "```yaml\n" + Main.prefix + "roleborder list\n```", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void RoleBorderAlreadyExistsError(TextChannel channel)
    {
        EmbedsUtil.AlreadyExistsError(channel, "RoleBorder");
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void RoleDoesNotExistError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "This roleborder");
    }

    public void RoleBorderNameIsToLongError(TextChannel channel, String name)
    {
        EmbedManager.SendErrorEmbed("You role name(**" + name + "**) is  to long!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyCreateRoleBorder(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully create the role **" + name + "**", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyAddedRoleBorder(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulAdded(channel, roleName, "the RoleBorders");
    }

    public void SuccessfullyRemovedRoleBorder(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulRemoved(channel, roleName, "the RoleBorders");
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void HasNoRoleBordersInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no RoleBorders**! \nYou can add RoleBorders with`" + Main.prefix + "roleborder add [@role]`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendRoleBorderListEmbed(TextChannel channel, String description){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDD27RoleBorders: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

}
