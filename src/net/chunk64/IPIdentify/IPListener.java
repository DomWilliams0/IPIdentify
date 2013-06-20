package net.chunk64.IPIdentify;

import java.util.ArrayList;
import java.util.List;

import net.chunk64.IPIdentify.utils.Utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class IPListener implements Listener
{
	private IPIdentify plugin;

	public IPListener(IPIdentify pingIdentify)
	{
		this.plugin = pingIdentify;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		String ip = event.getPlayer().getAddress().getAddress().getHostAddress();
		String playerName = event.getPlayer().getName();

		// Get list
		List<String> ipList;

		// Already exists
		if (IPIdentify.ipStore.getStringList(ip) != null)
		{
			ipList = IPIdentify.ipStore.getStringList(ip);

			// If not already in list
			if (!ipList.contains(playerName)) ipList.add(playerName);

		}

		else
		{
			ipList = new ArrayList<String>();
			ipList.add(playerName);
		}

		// Save to list and file
		IPIdentify.ipList.put(ip, ipList);
		Utils.setToStore(ip, ipList);

		// TODO Alert admins if multiple ips/shares with others
	}

	@EventHandler
	public void onPing(ServerListPingEvent event)
	{
		if (IPIdentify.printPings)
		{
			String ip = event.getAddress().getHostAddress();

			// Unknown IP
			if (!IPIdentify.ipList.containsKey(ip))
			{
				plugin.getLogger().info("Pinged by unknown IP: " + ip);
				return;
			}

			// Known
			List<String> names = IPIdentify.ipList.get(ip);

			plugin.getLogger().info("Pinged by " + ip + ": " + Utils.formatPlayerList(names));

		}

	}
}
