package main.de.confusingbot.commands.cmds.funcmds.catcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.person.Person;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "cat\n``` ```Shows you a Cat```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void CatUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "cat\n``` ```Shows you a Cat```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void SendSomethingWentWrong(TextChannel channel, int errorCode)
    {
        EmbedsUtil.SendSomethingWentWrong(channel, errorCode);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedsUtil.SendWaitMessage(channel, "Searching cute Cat..");
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendCat(TextChannel channel, String imageUrl, Person person, String color)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(person.name, null, person.imageUrl);
        builder.setColor(Color.decode(color));
        builder.setImage(imageUrl);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

}