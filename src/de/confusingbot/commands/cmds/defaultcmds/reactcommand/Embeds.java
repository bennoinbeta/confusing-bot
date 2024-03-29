package de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "react [textchannel] [messageID] [Emotji's]\n``` ```React with the [Emotji's] on a message[messageID]\uD83D\uDC39```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ReactCommandUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "react [textchannel] [messageID] [Emotji's]\n``` ```React with the [Emotji's] on a message[messageID]\uD83D\uDC39```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoMessageIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void YouHaveNotMentionedAValidEmoteError(TextChannel channel){
        EmbedManager.SendErrorEmbed("You haven't mentioned valid a Emote!", channel, EmbedsUtil.showErrorTime);
    }

    public void YouHaveNotMentionedATextChannelError(TextChannel channel){
        EmbedManager.SendUsageEmbed("You haven't mentioned a TextChannel!", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedEmotes(TextChannel channel, ArrayList<String> validEmotes, ArrayList<String> noValidEmotes)
    {
        String succeededEmotes =  validEmotes.size() > 0 ? String.join(", ", validEmotes) : "none";
        String failedEmotes =  noValidEmotes.size() > 0 ? String.join(", ", noValidEmotes) : "none";

        EmbedManager.SendSuccessEmbed("You successfully reacted!\n\n **\uD83D\uDCD7Succeeded Emotes:**\n" + succeededEmotes + "\n\n**\uD83D\uDCD5Failed Emotes:**\n" + failedEmotes, channel, 10);
    }


}
