package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.List;

public class MessageCommand implements ServerCommand {

    private String messageKey = "MESSAGE:";

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        //- message add welcome [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: Welcome @newMember to the server look at #rule
        //- message remove welcome

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(Permission.ADMINISTRATOR)) {
            if (args.length >= 2) {
                switch (args[1]) {
                    case "add":
                        if (args.length >= 3) {
                            switch (args[2]) {
                                case "welcome":
                                    MessageAddCommand(channel, args, message, MessageManager.welcomeMessageKey);
                                    break;
                                case "leave":
                                    //TODO
                                    break;
                                default:
                                    //Usage
                                    MessageManager.embeds.GeneralUsage(channel);
                                    break;
                            }
                        } else {
                            //Usage
                            MessageManager.embeds.GeneralUsage(channel);
                        }
                        break;
                    case "remove":
                        if (args.length >= 3) {
                            switch (args[2]) {
                                case "welcome":
                                    MessageRemoveCommand(channel, args, MessageManager.welcomeMessageKey);
                                    break;
                                case "leave":
                                    //TODO
                                    break;
                                default:
                                    //Usage
                                    MessageManager.embeds.GeneralUsage(channel);
                                    break;
                            }
                        } else {
                            //Usage
                            MessageManager.embeds.GeneralUsage(channel);
                        }
                        break;
                    default:
                        //Usage
                        MessageManager.embeds.GeneralUsage(channel);
                        break;
                }
            } else {
                //Usage
                MessageManager.embeds.GeneralUsage(channel);
            }
        } else {
            //Error
            MessageManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void MessageAddCommand(TextChannel channel, String[] args, Message message, String messageKey) {
        Guild guild = channel.getGuild();
        if (args.length > 3) {
            if (!MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), messageKey)) {
                List<TextChannel> mentionedChannel = message.getMentionedChannels();

                //Get channel where to send the message
                TextChannel messageChannel = mentionedChannel.get(0);
                mentionedChannel.remove(0);

                List<Role> mentionedRoles = message.getMentionedRoles();
                List<User> mentionedUser = message.getMentionedUsers();

                if (args[3].contains(messageChannel.getName())) {
                    //Get Color
                    String defaultColor = "#ffa500";
                    String color = defaultColor;
                    if (args[4].startsWith("#") && ColorExists(args[4])) {
                        color = args[4];
                    }

                    boolean hasChosenNewColor = !color.equals(defaultColor);
                    if ((args.length >= 4 && !hasChosenNewColor) || args.length >= 5) {
                        int startIndex = hasChosenNewColor ? 5 : 4;

                        String wholeMessage = getWholeMessage(args, startIndex);
                        String title = "";
                        String m = "";

                        if (wholeMessage.contains(messageKey)) {
                            String[] messageAndTitle = wholeMessage.split(messageKey);
                            title = messageAndTitle[0];
                            m = messageAndTitle[1];
                        }

                        //SQL
                        MessageManager.sql.MessageAddToSQL(guild.getIdLong(),
                                messageChannel.getIdLong(),
                                color,
                                messageKey,
                                title,
                                getMentionableMessage(m, mentionedChannel, mentionedRoles, mentionedUser));

                        //Message
                        MessageManager.embeds.SuccessfullyAddedMessage(channel, messageKey);
                    } else {
                        //Error
                        MessageManager.embeds.NoMessageDefinedError(channel, messageKey);
                    }
                } else {
                    //Error
                    MessageManager.embeds.NoMessageChannelMentionedError(channel, messageKey);
                }
            } else {
                //Error
                MessageManager.embeds.MessageAlreadyExistsError(channel, messageKey);
            }
        } else {
            //Usage
            MessageManager.embeds.MessageAddUsage(channel);
        }
    }

    private void MessageRemoveCommand(TextChannel channel, String[] args, String messageKey) {
        Guild guild = channel.getGuild();
        if (args.length == 3) {
            //SQL
            MessageManager.sql.MessageRemoveFromSQL(guild.getIdLong(), messageKey);

            //Message
            MessageManager.embeds.SuccessfullyRemovedMessage(channel, messageKey);
        } else {
            //Usage
            MessageManager.embeds.MessageAddUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private boolean ColorExists(String hexColor) {
        try {
            Color color = Color.decode(hexColor);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getWholeMessage(String[] args, int startIndex) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < args.length; i++) {
            builder.append(args[i] + " ");
        }
        String wholeMessage = builder.toString();
        return wholeMessage.trim();
    }

    private String getMentionableMessage(String message, List<TextChannel> mentionedChannel, List<Role> mentionedRoles, List<User> mentionedUsers) {
        String mentionableMessage = "";
        StringBuilder builder = new StringBuilder();
        String[] words = message.split(" ");

        for (String word : words) {
            for (TextChannel channel : mentionedChannel) {
                word = channel.getAsMention();
            }

            for (Role role : mentionedRoles) {
                word = role.getAsMention();
            }

            for (User user : mentionedUsers) {
                word = user.getAsMention();
            }

            builder.append(word + " ");
        }

        mentionableMessage = builder.toString().trim();


        return mentionableMessage;
    }


}
