package net.chunk64.IPIdentify;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class IPIdentify extends JavaPlugin
{

	// TODO
	// Save ips on join
	// Config option for printing to console
	// - if more than 3 people per ip, don't send all names - "and x more"
	//

	// Config variables
	public static boolean printPings;

	public static Map<String, List<String>> ipList = new HashMap<String, List<String>>();

	public static FileConfiguration ipStore = new YamlConfiguration();
	public static File ipStoreFile;

	public void onEnable()
	{
		// Initialize
		ipStoreFile = new File(getDataFolder(), "ipStore.yml");
//		ipStore = (ipStoreFile.exists()) ? YamlConfiguration.loadConfiguration(ipStoreFile) : new YamlConfiguration();
		
		// Register events
		getServer().getPluginManager().registerEvents(new IPListener(this), this);

		// Config
		createConfigs(true);
		loadConfig();

		// Load all IPs to map
		loadIps();
	}

	public void onDisable()
	{
	}

	private void loadConfig()
	{
		reloadConfig();
		printPings = getConfig().getBoolean("printPings");
	}

	private void createConfigs(boolean logger)
	{
		// Config
		if (!new File(getDataFolder(), "config.yml").exists())
		{
			saveDefaultConfig();
			if (logger) getLogger().warning("config.yml not found, creating...");
		}
		else if (logger) getLogger().info("config.yml found and loaded.");

		// ipStore
		File store = new File(getDataFolder(), "ipStore.yml");
		if (!store.exists())
		{
			if (logger) getLogger().warning("ipStore.yml not found, creating...");
			try
			{
				store.createNewFile();
				ipStore.options().header("This is where all IPs and player names are stored.");
				ipStore.options().pathSeparator(',');
				ipStore.save(store);
			} catch (Exception e)
			{
			}

		}
		else if (logger) getLogger().info("ipStore.yml found and loaded.");

	}

	private void loadIps()
	{
		try
		{
			ipStore.load(new File(getDataFolder(), "ipStore.yml"));
		} catch (Exception e)
		{
		}
		
		for (String s : ipStore.getKeys(false))
		{
			System.out.println(s);
		}
		
		
	}

}