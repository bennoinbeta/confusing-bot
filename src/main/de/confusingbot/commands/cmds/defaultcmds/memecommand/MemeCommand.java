package main.de.confusingbot.commands.cmds.defaultcmds.memecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MemeCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public MemeCommand()
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
                //Get Random Color
                Random random = new Random();
                int nextInt = random.nextInt(0xffffff + 1);
                String colorCode = String.format("#%06x", nextInt);

                try
                {
                    //Check Url
                    URL memeURL = new URL("https://meme-api.herokuapp.com/gimme");
                    HttpURLConnection huc = (HttpURLConnection) memeURL.openConnection();
                    int responseCode = huc.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //Get JSON-Object
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
                        String jsonString = bufferedReader.readLine();
                        JSONObject jsonObject = new JSONObject(jsonString);

                        //Get Data
                        String imageUrl = jsonObject.getString("url");
                        String title = jsonObject.getString("title");
                        String url = jsonObject.getString("postLink");

                        //Message
                        embeds.SendMeme(channel, title, imageUrl, url, colorCode);
                    }
                    else
                    {
                        //Error
                        embeds.SendSomethingWentWrong(channel);
                    }

                } catch (Exception e)
                {
                    //Error
                    embeds.SendSomethingWentWrong(channel);
                }
            }
            else
            {
                //Usage
                embeds.MemeUsage(channel);
            }

        }
    }
}
