package de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.Main;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInvites
{

    //Needed Permissions
    Permission MANAGE_SERVER = Permission.MANAGE_SERVER;
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;

    public void onSecond()
    {
        try
        {
            ResultSet set = SQLManager.onQuery("SELECT * FROM inviterole");
            if(set != null)
            {
                while (set.next())
                {
                    int inviteCount = set.getInt("invitions");
                    long roleID = set.getLong("roleid");
                    long guildID = set.getLong("guildid");

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                    if (guild != null)
                    {
                        Member bot = guild.getSelfMember();

                        if (bot.hasPermission(MANAGE_SERVER))
                        {
                            if (bot.hasPermission(MANAGE_ROLES))
                            {
                                guild.retrieveInvites().queue(invites -> {

                                    for (Invite invite : invites)
                                    {
                                        if (invite.getMaxAge() != 0) continue;//Check if invite is a perma invite!

                                        if (invite.getUses() >= inviteCount)
                                        {
                                            Role role = guild.getRoleById(roleID);
                                            if (role != null)
                                            {
                                                Member member = guild.getMemberById(invite.getInviter().getId());
                                                if (member != null)
                                                {
                                                    //Add role
                                                    CommandsUtil.AddOrRemoveRoleFromMember(guild, member, role, true);
                                                }
                                            }
                                            else
                                            {
                                                //Error
                                                InviteRoleManager.embeds.RoleDoesNotExistError(guild.getDefaultChannel(), roleID);

                                                //SQL
                                                InviteRoleManager.sql.RemoveRoleFromSQL(guildID, roleID);
                                            }
                                        }
                                    }
                                });
                            }
                            else
                            {
                                //Error
                                EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_ROLES, "InviteCommand | Can't add role to member!");
                            }
                        }
                        else
                        {
                            //Error
                            EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_SERVER, "InviteCommand | Can't read server invites!");
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
