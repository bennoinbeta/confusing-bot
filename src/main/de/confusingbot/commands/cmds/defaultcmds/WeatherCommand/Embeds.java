package main.de.confusingbot.commands.cmds.defaultcmds.WeatherCommand;

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
        HelpManager.useful.add("```yaml\n" + Main.prefix + "weather [cityName/cityId]\n``` ```Will give you some weather information!```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void WeatherUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "weather [cityName/cityId]\n``` ```Will give you some weather information!```", channel, EmbedsUtil.showUsageTime);
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
    public void LocationDoesNotExistInformation(TextChannel channel, String location)
    {
        EmbedManager.SendInfoEmbed("Couldn't find `" + location + "`!", channel, EmbedsUtil.showInfoTime);
    }

    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedsUtil.SendWaitMessage(channel, "Checking weather stations..");
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendWeather(TextChannel channel, String city, String description, double tempInDegree, double feelsLikeTempInDegree, double windSpeed, int humidity, String imageUrl, String color)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.decode(color));
        builder.addField("Description", description, false);
        builder.addField("Degree", tempInDegree + "°C", true);
        builder.addField("Feels Like", feelsLikeTempInDegree + "°C", true);
        builder.addField("Wind Speed", windSpeed + "km/h", true);
        builder.addField("Humidity", humidity + "%", true);
        builder.setTitle("\uD83C\uDFD9 " + city);
        builder.setThumbnail(imageUrl);

        EmbedManager.SendEmbed(builder, channel, 30);
    }
}
