package de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "question ([Title]) QUESTION: [Question] [roles to mention]\n```" +
                " ```Create a custom TextChannel (in the QuestionCategory) where only your question exists```");

        HelpManager.admin.add("```yaml\n" + Main.prefix + "question category\n```" +
                " ```Create a question category to unlock the question feature```" +
                "```fix\n" + QuestionManager.questionCategoryPermission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=N7HfwrymW0s&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=1)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "question [Title] QUESTION: [Question] [mentioned role]\n```"
                        + "```Create a custom TextChannel (in the QuestionCategory) where only your question exists```"
                        + "```yaml\n" + Main.prefix + "question close```"
                        + "```Close the question in which you wrote this command```"
                        + "```yaml\n" + Main.prefix + "question info```"
                        + "```Show you some information about the asked question```"
                        + "```yaml\n" + Main.prefix + "question category\n```"
                        + " ```Create a question category to unlock the question feature```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void QuestionCategoryGeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "question category create [categoryId]\n```"
                        + "```Create a question category (-> the members can use the question command)```"
                        + "```yaml\n" + Main.prefix + "question category remove\n```"
                        + " ```Remove the question category```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void QuestionCloseUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "question close```"
                + "```Close the question in which you wrote this command```", channel, EmbedsUtil.showUsageTime);
    }

    public void QuestionCategoryCreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "question category create [categoryId]\n```"
                + "```Create a question category (-> the members can use the question command)```", channel, EmbedsUtil.showUsageTime);
    }

    public void QuestionCategoryRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "question category remove\n```"
                + " ```Remove the question category```", channel, EmbedsUtil.showUsageTime);
    }

    public void QuestionInfoUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "question info```"
                + "```Show you some information about the asked question```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void OnlyOneAllowedQuestionCategory(TextChannel channel)
    {
        EmbedsUtil.OnlyOneAllowedToExistError(channel, "QuestionCategory");
    }

    public void NoPermissionForClosingThisQuestionChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You have no permission to close this question!", channel, EmbedsUtil.showErrorTime);
    }

    public void YouAreNotInAQuestionChannelError(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("Ups, I think this is **no QuestionChannel**..", channel, EmbedsUtil.showErrorTime);
    }

    public void YouCanOnlyMentionOneRoleInAQuestionError(TextChannel channel, int mentionableRoles)
    {
        EmbedManager.SendInfoEmbed("Sry you can only mention **" + mentionableRoles + " roles**!", channel, EmbedsUtil.showErrorTime);
    }

    public void ServerHasNoQuestionCategoryError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "QuestionCategory");
    }

    public void QuestionCategoryDoesNotExistAnyMore(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("It looks like that the question category has been deleted!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedCategoryToQuestionCategory(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully create a QuestionCategory called" + name, channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedCategoryToQuestionCategory(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully removed a QuestionCategory called" + name, channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void QuestionChannelWillBeDeletedInXSeconds(TextChannel channel, int deletedInSeconds)
    {
        EmbedManager.SendInfoEmbed("The question will be deleted in " + deletedInSeconds + "s!", channel, 0);
    }

    public void MinXWords(TextChannel channel, int words)
    {
        EmbedManager.SendInfoEmbed("You need **min " + words + " words** for creating a question!", channel, EmbedsUtil.showInfoTime);
    }

    public void ThisServerHasNoExistingQuestionCategoryInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("**This server doesn't support this feature!**\nInform a **Admin** about that!", channel, EmbedsUtil.showInfoTime);
    }

    public void SendDeleteQuestionInfo(TextChannel channel, Member member, long timeleft)
    {
        EmbedManager.SendCustomEmbedGetMessageID("This question will be closed in " + timeleft + " hours!",
                member.getAsMention() + " If nobody writes anything within " + timeleft + " hours, this question will be deleted\uD83D\uDE10",
                Color.decode("#e03d14"), channel);
    }

    public void SendQuestionInfo(TextChannel channel, Member requester, Member questionAsker, long timeleft, LocalDateTime creationTime, String category)
    {
        DateTimeFormatter creationDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String creationDateString = creationDateFormatter.format(creationTime);
        DateTimeFormatter creationTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String creationTimeString = creationTimeFormatter.format(creationTime);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a1f542"));
        builder.setTitle("❓Question Info:");
        builder.addField("Creation Date", creationDateString, false);
        builder.addField("Creation Time", creationTimeString, false);
        builder.addField("Time Left", timeleft + "h", false);
        builder.addField("Question Asker", questionAsker.getAsMention(), false);
        builder.addField("Category", category, false);
        builder.setFooter("Requested by " + requester.getEffectiveName());

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 30);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public EmbedBuilder CreateQuestionEmbed(Member member, String questionTitle, String question, String roleString)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a1f542"));
        builder.setDescription("**❓ Question** form " + member.getAsMention() + "\n\n\n");
        builder.addField(questionTitle + "\n\n", question, false);
        builder.addField("", roleString, false);
        builder.setTimestamp(OffsetDateTime.now());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());

        return builder;
    }
}
