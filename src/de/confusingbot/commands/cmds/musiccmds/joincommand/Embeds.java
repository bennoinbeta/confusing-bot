package de.confusingbot.commands.cmds.musiccmds.joincommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "join\n``` ```ConfusingBot will join your channel```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JoinUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "join\n``` ```ConfusingBot will join your channel```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void YourQueueIsEmptyInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`Ups, your Queue is empty`⚠️", channel, EmbedsUtil.showInfoTime);
    }

    public void IsAlreadyInAVoiceChannel(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`Ups, the bot is already in a VoiceChannel`⚠️", channel, EmbedsUtil.showInfoTime);
    }

}
