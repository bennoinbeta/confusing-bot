package de.confusingbot.music.manage;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class Music
{

    public static AudioPlayerManager audioPlayerManager;
    public static PlayerManager playerManager;

    public Music()
    {
        audioPlayerManager = new DefaultAudioPlayerManager();
        playerManager = new PlayerManager();
    }

    public static void instantiateMusic()
    {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);

        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
    }

}
