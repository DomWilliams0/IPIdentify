package net.chunk64.IPIdentify;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class IPListener implements Listener
{
	private IPIdentify plugin;
	public IPListener(IPIdentify pingIdentify)
	{
		this.plugin = pingIdentify;
	}
	
	
	@EventHandler
	public void onPing (ServerListPingEvent event)
	{
		
		
		
		
	}
	
	
	
	
}
