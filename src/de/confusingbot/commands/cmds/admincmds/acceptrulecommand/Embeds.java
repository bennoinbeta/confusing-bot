package de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

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
        HelpManager.admin.add("```yaml\n" + Main.prefix + "acceptrule``` " +
                "```Create a awesome rule system, where the user has to accept your rules to unlock the server```" +
                "```fix\n" + AcceptRuleManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=7)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules accepted] ([@role rules not accepted])\n```"
                        + "```Add a emotji to your rules on which the user can click and automaticlly unlock special channels```\n"
                        + "```yaml\n" + Main.prefix + "acceptrule remove\n```"
                        + "```Remove the AcceptRule```\n"
                        + "```yaml\n" + Main.prefix + "acceptrule show\n```"
                        + "```Shows the AcceptedRule of this server```\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules accepted] ([@role rules not accepted])\n```"
                + "```Add a emotji to your rules on which the user can click and automaticlly unlock special channels```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "acceptrule remove\n```"
                + "```Remove the AcceptRule```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void ShowUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "acceptrule show\n```"
                + "```Shows the AcceptedRule of this server```\n", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void OnlyOneAcceptRuleAllowedError(TextChannel channel)
    {
        EmbedsUtil.OnlyOneAllowedToExistError(channel, "AcceptRule");
    }

    public void NoExistingAcceptRuleError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "AcceptRule");
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void RoleDoesNotExistAnymore(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The Role with the id **" + roleid + "** from the AcceptRules doesn't exist anymore!", channel, EmbedsUtil.showErrorTime);
    }

    public void RoleBorderDoesNotExistAnymore(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The Role with the id **" + roleid + "** from the RoleBorders doesn't exist anymore!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedAcceptRule(TextChannel channel)
    {
        EmbedsUtil.SuccessfulAdded(channel, "AcceptRule", "this server");
    }

    public void SuccessfulRemovedAcceptRule(TextChannel channel)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "AcceptRule", "this server");
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void ServerHasNoAcceptedRuleInformation(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("This server has no **AcceptedRule** you can add one with `add`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void ShowAcceptRule(TextChannel channel, String textChannelString, String notAcceptedRoleString, String acceptedRoleString, String emote)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("✅AcceptedRule: ");
        builder.addField("TextChannel", textChannelString, false);
        builder.addField("NotAcceptedRole", notAcceptedRoleString, false);
        builder.addField("AcceptedRole", acceptedRoleString, false);
        builder.addField("Emote", emote, false);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

}
