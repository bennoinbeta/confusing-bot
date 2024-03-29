package de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.List;

public class ReactRolesCommand implements ServerCommand
{

    public ReactRolesCommand()
    {
        ReactRoleManager.embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //By errors watch this: https://www.youtube.com/watch?v=68JMMzjZhJI
        //   args[0]   args[1]    args[2]     args[3]      args[4]   args[5]
        //- reactrole    add     [channel]  [message id]   [emotjis]  [Rolle]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            //Check Permission
            if (member.hasPermission(channel, ReactRoleManager.permission))
            {
                if (args.length >= 2)
                {
                    switch (args[1])
                    {
                        case "add":
                            addCommand(message, args, channel);
                            break;
                        case "remove":
                            removeCommand(message, args, channel);
                            break;
                        case "list":
                            ListCommand(channel.getGuild(), channel);
                            break;
                        default:
                            //Usage
                            ReactRoleManager.embeds.GeneralUsage(channel);
                            break;
                    }
                }
                else
                {
                    //Usage
                    ReactRoleManager.embeds.GeneralUsage(channel);
                }
            }
            else
            {
                //Error
                ReactRoleManager.embeds.NoPermissionError(channel, ReactRoleManager.permission);
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        long messageid = ReactRoleManager.embeds.SendWaitMessage(channel);
        Runnable r = new ListReactRolesRunnable(guild, channel, messageid);
        Thread t = new Thread(r);
        t.start();
    }

    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            if (args.length >= 6)
            {
                List<TextChannel> channels = message.getMentionedChannels();
                List<Role> roles = message.getMentionedRoles();

                if (!channels.isEmpty() && !roles.isEmpty())
                {
                    TextChannel textChannel = channels.get(0);//args 2
                    Role role = roles.get(0);//args 5
                    String messageIDString = args[3]; //args 3
                    String emoteString = CommandsUtil.getEmote(message, args[4]);//args 4

                    if (CommandsUtil.isNumeric(messageIDString))
                    {
                        long messageID = Long.parseLong(messageIDString);

                        if (CommandsUtil.messageIdExists(channel, messageID))
                        {
                            if (!emoteString.isEmpty() && emoteString != null)
                            {
                                if (!ReactRoleManager.sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                                {
                                    if (CommandsUtil.reactEmote(emoteString, textChannel, messageID, true))
                                    {
                                        //SQL
                                        ReactRoleManager.sql.addToSQL(message.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, role.getIdLong());

                                        //Message
                                        ReactRoleManager.embeds.SuccessfullyAddedReactRole(channel, role);
                                    }
                                    else
                                    {
                                        //Error
                                        ReactRoleManager.embeds.NoValidEmoteError(channel, emoteString);
                                    }
                                }
                                else
                                {
                                    //Error
                                    ReactRoleManager.embeds.ReactRoleAlreadyExistsError(channel);
                                }
                            }
                            else
                            {
                                //Error
                                ReactRoleManager.embeds.NoValidEmoteError(channel, emoteString);
                            }
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.NoValidIdError(channel, messageIDString);
                        }
                    }
                    else
                    {
                        //Error
                        ReactRoleManager.embeds.NoValidIdError(channel, messageIDString);
                    }
                }
                else
                {
                    //Usage
                    ReactRoleManager.embeds.AddUsage(channel);
                }
            }
            else
            {
                //Usage
                ReactRoleManager.embeds.AddUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    private void removeCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 6)
        {
            List<TextChannel> channels = message.getMentionedChannels();
            List<Role> roles = message.getMentionedRoles();

            if (!channels.isEmpty() && !roles.isEmpty())
            {
                Role role = roles.get(0);
                String messageIDString = args[3];
                String emoteString = CommandsUtil.getEmotes(message, Arrays.asList(args[4])).get(0);
                TextChannel textChannel = channels.get(0);//args 2

                if (CommandsUtil.isNumeric(messageIDString))
                {
                    long messageID = Long.parseLong(messageIDString);

                    if (!emoteString.isEmpty() && emoteString != null)
                    {
                        if (CommandsUtil.messageIdExists(textChannel, messageID))
                        {
                            if (ReactRoleManager.sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                            {
                                //React
                                boolean success = CommandsUtil.reactEmote(emoteString, textChannel, messageID, false);

                                if (!success)
                                    ReactRoleManager.embeds.MessageIdDoesNotExistAnymore(channel, messageIDString);

                                //SQL
                                ReactRoleManager.sql.removeFromSQL(message.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, role.getIdLong());

                                //Message
                                ReactRoleManager.embeds.SuccessfullyRemovedReactRole(channel, role);
                            }
                            else
                            {
                                //Error
                                ReactRoleManager.embeds.ReactRoleNotExistsError(channel, role.getAsMention());
                            }
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.NoValidIdError(channel, messageIDString);
                        }
                    }
                    else
                    {
                        //Error
                        ReactRoleManager.embeds.NoValidEmoteError(channel, emoteString);
                    }
                }
                else
                {
                    //Error
                    ReactRoleManager.embeds.NoValidIdError(channel, messageIDString);
                }
            }
            else
            {
                //Usage
                ReactRoleManager.embeds.RemoveUsage(channel);
            }
        }
        else
        {
            //Usage
            ReactRoleManager.embeds.RemoveUsage(channel);
        }
    }
}

