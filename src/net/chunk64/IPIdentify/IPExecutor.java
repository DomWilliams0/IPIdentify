package net.chunk64.IPIdentify;

import java.util.List;

import net.chunk64.IPIdentify.utils.Utils;
import net.minecraft.server.v1_5_R3.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class IPExecutor implements CommandExecutor
{
	@SuppressWarnings("unused")
	private IPIdentify plugin;

	public IPExecutor(IPIdentify ipIdentify)
	{
		this.plugin = ipIdentify;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (cmd.getName().equalsIgnoreCase("ipidentify"))
		{

			// Show help
			if (args.length == 0)
			{
				// Show help
				sender.sendMessage("pls help");
				return true;
			}

			// Toggle ping alerts
			if (args.length == 1 && args[0].equalsIgnoreCase("pingalerts"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
					return true;
				}

				Player p = (Player) sender;
				String toggle;

				if (IPIdentify.pingAlerts.contains(p.getName()))
				{
					IPIdentify.pingAlerts.remove(p.getName());
					toggle = ChatColor.RED + "disabled";
				}
				else
				{
					IPIdentify.pingAlerts.add(p.getName());
					toggle = ChatColor.GREEN + "enabled";
				}

				p.sendMessage(ChatColor.GRAY + "Ping alerts were " + toggle + ChatColor.GRAY + "!");
				return true;

			}

			// Ping
			if (args.length <= 2 && args[0].equalsIgnoreCase("ping"))
			{
				try
				{
					// Offline player
					if (Bukkit.getPlayer(args[1]) == null) throw new IllegalArgumentException("Invalid player!");

					Player target = Bukkit.getPlayer(args[1]);

					CraftPlayer cp = (CraftPlayer) target;
					final EntityPlayer ep = cp.getHandle();
					int ping = ep.ping;

					ChatColor c;
					if (ping <= 50)
						c = ChatColor.GREEN;
					else if (ping <= 100)
						c = ChatColor.GOLD;
					else
						c = ChatColor.RED;

					sender.sendMessage(ChatColor.GREEN + target.getName() + ChatColor.GRAY + "'s ping is " + c + ping + ChatColor.GRAY + ".");

					return true;

				} catch (IllegalArgumentException e)
				{
					sender.sendMessage(ChatColor.RED + "That player is not online!");
					return true;
				} catch (Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " ping [online player]");
					return true;
				}

			}

			// iplookup
			if (args.length <= 2 && args[0].equalsIgnoreCase("lookup"))
			{
				try
				{
					String ip = args[1];
					
					// Not valid
					if (!IPIdentify.ipMap.containsKey(ip))
					{
						sender.sendMessage(ChatColor.RED + ip + " is not associated with any players!");
						return true;
					}
					
					List<String> names = IPIdentify.ipMap.get(ip);
					
					sender.sendMessage(ChatColor.GRAY + "Players known to have IP " + ChatColor.GREEN + ip + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + Utils.formatFullList(names));
					return true;
					
					
				} catch (Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " lookup [ip address]");
					e.printStackTrace();
					return true;
				}

			}

		}

		return false;
	}
}
