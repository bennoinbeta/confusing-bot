package main.de.confusingbot.commands.cmds.musiccmds.queuecommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.queue.Queue;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class QueueCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());
        Queue queue = controller.getQueue();

        if (args.length == 1)
        {
            //List
            ListQueueCommand(channel, args, queue);
        }
        else if (args.length >= 2)
        {
            switch (args[1])
            {
                case "clear":
                    //Clear
                    ClearQueueCommand(channel, args, queue);
                    break;
                case "delete":
                    //Delete
                    DeleteAtIndexCommand(channel, args, queue);
                    break;
                default:
                    //Usage
                    embeds.GeneralUsage(channel);
                    break;
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListQueueCommand(TextChannel channel, String[] args, Queue queue)
    {
        List<AudioTrack> tracks = queue.getQueueList();
        if (tracks.size() > 0)
        {
            List<String> queueStrings = createQueueString(tracks);

            for (int i = 0; i < queueStrings.size(); i++)
            {
                //Message
                embeds.SendMusicQueueEmbed(channel, queueStrings.get(i), i == 0);
                //System.out.println(queueStrings.get(i) + "\n----------------------------------------------------\n");
            }
        }
        else
        {
            //Message
            embeds.NoExistingMusicQueueEmbed(channel);
        }
    }

    private void ClearQueueCommand(TextChannel channel, String[] args, Queue queue)
    {
        if (args.length == 2)
        {
            //Clear Queue
            queue.getQueueList().clear();

            //Message
            embeds.SuccessfullyClearedMusicQueue(channel);
        }
        else
        {
            //Usage
            embeds.ClearQueueUsage(channel);
        }
    }

    private void DeleteAtIndexCommand(TextChannel channel, String[] args, Queue queue)
    {
        if (args.length == 3)
        {
            if (args[2] != null)
            {
                if (CommandsUtil.isNumeric(args[2]))
                {
                    int index = Integer.parseInt(args[2]) - 1;//because the user won't start counting by 0
                    List<AudioTrack> queueList = queue.getQueueList();
                    if (index <= queueList.size() && index >= 0)
                    {
                        //Message
                        embeds.SuccessfullyDeletedTrackAtIndex(channel, index, queueList.get(index).getInfo().title);

                        queue.DeleteAtIndex(index);
                    }
                    else
                    {
                        //Error
                        embeds.CouldNotDeleteTrackAtIndex(channel, index);
                    }
                }
                else
                {
                    EmbedsUtil.NoNumberError(channel, args[2]);
                }
            }
            else
            {
                //Usage
                embeds.DeleteAtIndexUsage(channel);
            }
        }
        else
        {
            //Usage
            embeds.DeleteAtIndexUsage(channel);
        }

    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<String> createQueueString(List<AudioTrack> tracks)
    {
        List<String> queueString = new ArrayList<>();
        int maxQueueCharLength = 1500;
        int messageCounter = 0;

        queueString.add("");

        for (AudioTrack track : tracks)
        {
            //Reset queueString
            if (queueString.get(messageCounter).length() >= maxQueueCharLength)
            {
                queueString.add("");
                messageCounter++;
            }

            //Create QueueString
            String url = track.getInfo().uri;
            String title = track.getInfo().title;
            String author = track.getInfo().author;
            queueString.set(messageCounter,
                    queueString.get(messageCounter) + "\uD83C\uDFB5 **[" + title + "](" + url + ")** " + author + "\n");
        }

        return queueString;
    }
}