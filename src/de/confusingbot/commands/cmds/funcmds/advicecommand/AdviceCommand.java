package de.confusingbot.commands.cmds.funcmds.advicecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.json.JsonReader;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.person.Person;
import de.confusingbot.manage.person.PersonManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class AdviceCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public AdviceCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length == 1)
            {
                //Send WaitMessage
                long waitMessageId = embeds.SendWaitMessage(channel);

                //Random Color
                Random random = new Random();
                int nextInt = random.nextInt(0xffffff + 1);
                String colorCode = String.format("#%06x", nextInt);

                try
                {
                    //Check Url
                    URL adviceUrl = new URL("https://api.adviceslip.com/advice");
                    HttpURLConnection huc = (HttpURLConnection) adviceUrl.openConnection();
                    int responseCode = huc.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //Get JSON-Object
                        JSONObject jsonObject = JsonReader.readJsonFromUrl(adviceUrl);

                        //Get Advice
                        String advice = jsonObject.getJSONObject("slip").getString("advice");

                        //Get Random Person
                        Person person = PersonManager.getRandomPerson();

                        //Message
                        embeds.SendAdvice(channel, advice, colorCode, person);
                    }
                    else
                    {
                        //Error
                        embeds.SendSomethingWentWrong(channel, responseCode);
                    }
                } catch (Exception e)
                {
                    //Error
                    embeds.SendSomethingWentWrong(channel, 0);
                }

                //Delete WaitMessage
                EmbedManager.DeleteMessageByID(channel, waitMessageId);
            }
            else
            {
                //Usage
                embeds.AdviceUsage(channel);
            }

        }
    }
}
