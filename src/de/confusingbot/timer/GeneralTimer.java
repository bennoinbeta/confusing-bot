package de.confusingbot.timer;

import de.confusingbot.commands.cmds.admincmds.eventcommand.UpdateEvents;
import de.confusingbot.commands.cmds.admincmds.repeatinfocommand.UpdateRepeatInfo;
import de.confusingbot.commands.cmds.admincmds.votecommand.UpdateVotes;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.UpdateInfos;
import de.confusingbot.commands.cmds.defaultcmds.inviterolecommand.UpdateInvites;
import de.confusingbot.commands.cmds.defaultcmds.questioncommand.UpdateQuestionChannels;
import de.confusingbot.commands.cmds.defaultcmds.youtubecommand.UpdateYouTubeAnnouncements;

import java.util.concurrent.*;

public class GeneralTimer
{

    private UpdateQuestionChannels updateQuestionChannels;
    private UpdateVotes updateVotes;
    private UpdateRepeatInfo updateRepeatInfo;
    private UpdateEvents updateEvents;
    private UpdateInfos updateInfos;
    private UpdateInvites updateInvites;
    private UpdateYouTubeAnnouncements updateYouTubeAnnouncements;


    private ScheduledExecutorService shortScheduler;
    //private ScheduledExecutorService longScheduler;

    public GeneralTimer()
    {
        shortScheduler = Executors.newScheduledThreadPool(1);
        //longScheduler = Executors.newScheduledThreadPool(1);

        this.updateQuestionChannels = new UpdateQuestionChannels();
        this.updateVotes = new UpdateVotes();
        this.updateRepeatInfo = new UpdateRepeatInfo();
        this.updateEvents = new UpdateEvents();
        this.updateInfos = new UpdateInfos();
        this.updateInvites = new UpdateInvites();
        this.updateYouTubeAnnouncements = new UpdateYouTubeAnnouncements();
    }

    //Timer
    public void startTimer()
    {
        shortScheduler.scheduleAtFixedRate(new Runnable()
                                           {
                                               public void run()
                                               {
                                                   updateQuestionChannels.onSecond();
                                                   updateVotes.onSecond();
                                                   updateRepeatInfo.onSecond();
                                                   updateEvents.onSecond();
                                                   updateInvites.onSecond();
                                                   updateYouTubeAnnouncements.onSecond();
                                               }
                                           },
                0,
                60 * 5,// every 5min
                TimeUnit.SECONDS);

        /*
        longScheduler.scheduleAtFixedRate(new Runnable()
                                          {
                                              public void run()
                                              {
                                                  updateInfos.onSecond();
                                              }
                                          },
                0,
                60 * 60 * 12,// every 12h
                TimeUnit.SECONDS);
                */
    }

    public void stopTimer()
    {
        shortScheduler.shutdown();
        //longScheduler.shutdown();
    }
}