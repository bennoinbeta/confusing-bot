package de.confusingbot.commands.cmds.admincmds.rolecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.List;

public class RoleCommand implements ServerCommand
{

    private Embeds embeds = RoleCommandManager.embeds;

    public RoleCommand()
    {
        embeds.HelpEmbed();
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

        // args0  args1 args2  args3
        //- role create <Name> #color
        //- role delete @role

        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(channel, RoleCommandManager.permission))
            {
                if (args.length >= 2)
                {
                    switch (args[1])
                    {
                        case "create":
                            CreateRole(args, channel, guild);
                            break;
                        case "delete":
                            DeleteRole(args, channel, message);
                            break;
                        default:
                            //Usage
                            embeds.GeneralUsage(channel);
                            break;
                    }
                }
                else
                {
                    //Usage
                    embeds.GeneralUsage(channel);
                }
            }
            else
            {
                //Error
                embeds.NoPermissionError(channel, RoleCommandManager.permission);
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void CreateRole(String[] args, TextChannel channel, Guild guild)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            int length = args.length;
            if (length > 2)
            {
                StringBuilder builder = new StringBuilder();
                String roleName;
                Color roleColor = new Color(255, 255, 255);

                if (args[length - 1].startsWith("#") && args.length > 3)//if the last arg is a HexColor
                {
                    for (int i = 2; i < length - 1; i++) builder.append(args[i] + " ");

                    roleName = builder.toString().trim();
                    String hexColor = args[length - 1];

                    if (CommandsUtil.isColor(hexColor))
                    {
                        roleColor = Color.decode(hexColor);
                    }
                    else
                    {
                        embeds.NoHexColorError(channel, hexColor);
                        return;
                    }
                }
                else
                {
                    for (int i = 2; i < length; i++) builder.append(args[i] + " ");
                    roleName = builder.toString().trim();
                }
                //Create Role
                createRole(guild, roleName, roleColor);
                //Message
                embeds.SuccessfullyCreatedRole(channel, roleName, roleColor);
            }
            else
            {
                //Usage
                embeds.CreateUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    private void DeleteRole(String[] args, TextChannel channel, Message message)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            if (args.length > 2)
            {
                List<Role> roles = message.getMentionedRoles();
                if (!roles.isEmpty())
                {
                    Role role = roles.get(0);
                    try
                    {
                        //Delete Role
                        role.delete().complete();

                        //Message
                        embeds.SuccessfullyDeletedRole(channel, role.getName());
                    } catch (Exception e)
                    {
                        //Error
                        embeds.CouldNotDeleteRole(channel, role.getAsMention());
                    }
                }
                else
                {
                    //Error
                    embeds.HaveNotMentionedRoleError(channel);
                }
            }
            else
            {
                //Usage
                embeds.DeleteUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void createRole(Guild guild, String roleName, Color roleColor)
    {
        RoleAction roleAction = guild.createRole();
        roleAction.setName(roleName);
        roleAction.setColor(roleColor);
        roleAction.setMentionable(true);
        roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
        roleAction.queue();
    }
}
