package main.de.confusingbot.commands.cmds.musiccmds.joincommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class JoinCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public JoinCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

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
                AudioManager manager = channel.getGuild().getAudioManager();
                if (!manager.isConnected())
                {
                    GuildVoiceState state = member.getVoiceState();

                    if (state != null)
                    {
                        VoiceChannel voiceChannel = state.getChannel();
                        if (voiceChannel != null)
                        {
                            if (voiceChannel.getUserLimit() > voiceChannel.getMembers().size())
                            {
                                MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                                Music.channelID = voiceChannel.getIdLong();

                                //SQL
                                controller.updateChannel(channel, member);

                                List<AudioTrack> queue = controller.getQueue().getQueueList();
                                if (queue.size() > 0)
                                {
                                    Connect(voiceChannel, manager);

                                    //PlayTrack
                                    controller.getPlayer().playTrack(queue.get(0));
                                }
                                else
                                {
                                    //Information
                                    embeds.YourQueueIsEmptyInformation(channel);
                                }
                            }
                            else
                            {
                                //Information
                                EmbedsUtil.NoSpaceForBotInformation(channel);
                            }
                        }
                        else
                        {
                            //Information
                            EmbedsUtil.YouAreNotInAVoiceChannelInformation(channel);
                        }
                    }
                }
                else
                {
                    embeds.IsAlreadyInAVoiceChannel(channel);
                }
            }
            else
            {
                //Usage
                embeds.JoinUsage(channel);
            }
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void Connect(VoiceChannel voiceChannel, AudioManager manager)
    {
        if (!manager.isConnected())
            manager.openAudioConnection(voiceChannel);

    }
}
