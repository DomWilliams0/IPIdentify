package net.chunk64.IPIdentify;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class IPIdentify extends JavaPlugin
{

	// TODO
	// Save ips on join
	// Config option for printing to console
	// - if more than 3 people per ip, don't send all names - "and x more"
	// /looup pl

	// Config variables
	public static boolean printPings;

	public static Map<String, List<String>> ipMap = new HashMap<String, List<String>>();
	
	public static Set<String> pingAlerts = new HashSet<String>();
	
	public static String prefix = "&8[&2IPIdentify&8]&7: ";

	public static FileConfiguration ipStore;
	public static File ipStoreFile;

	public void onEnable()
	{
		// Initialize
		ipStoreFile = new File(getDataFolder(), "ipStore.yml");
		ipStore = (ipStoreFile.exists()) ? YamlConfiguration.loadConfiguration(ipStoreFile) : new YamlConfiguration();

		// Register events
		getServer().getPluginManager().registerEvents(new IPListener(this), this);
		
		// Register commands
		getCommand("ipidentify").setExecutor(new IPExecutor(this));

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

		// Clear current map
		ipMap.clear();

		// Get every 4th key
		int count = 0;
		for (String s : ipStore.getKeys(true))
		{
			count++;
			if (count == 4)
			{
				count = 0;

				// Add to map
				List<String> names = ipStore.getStringList(s);
				ipMap.put(s, names);
			}
		}

	}

}