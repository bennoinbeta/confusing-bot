package main.de.confusingbot.music;

import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.manage.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class UpdateVoiceChannel
{

    public static void onPlayerLeave(VoiceChannel channel)
    {
        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());
        if (channel.getMembers().size() == 1 && channel.getIdLong() == controller.channelID && channel.getMembers().get(0).getUser().isBot())
        {
            if (controller.getPlayer().isPaused())
                controller.getPlayer().setPaused(false);

            if (controller.getPlayer().getPlayingTrack() != null)
                controller.getPlayer().stopTrack();

            AudioManager manager = channel.getGuild().getAudioManager();
            manager.closeAudioConnection();

            //Message
            controller.getMusicEmbedManager().getMusicEmbed().NoUserAnymoreEmbed();
        }
    }
}
