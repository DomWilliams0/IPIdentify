package net.chunk64.IPIdentify;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class IPIdentify extends JavaPlugin
{

	// TODO
	// Save ips on join
	// Config option for printing to console
	// - if more than 3 people per ip, don't send all names - "and x more"
	//

	public static boolean printPings;
	
	public void onEnable()
	{
		// Register events
		getServer().getPluginManager().registerEvents(new IPListener(this), this);
		
		// Config
		createConfig(true);
		loadConfig();
	}

	public void onDisable()
	{

	}

	private void loadConfig()
	{
		reloadConfig();
		printPings = getConfig().getBoolean("printPings");
	}

	private void createConfig(boolean logger)
	{
		// Config
		if (!new File(getDataFolder(), "config.yml").exists())
		{
			saveDefaultConfig();
			if (logger) getLogger().warning("config.yml not found, creating...");
		}
		else if (logger) getLogger().info("config.yml found and loaded.");
	}

}