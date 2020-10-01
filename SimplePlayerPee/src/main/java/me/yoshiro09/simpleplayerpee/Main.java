package me.yoshiro09.simpleplayerpee;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.commands.PeeInfo;
import me.yoshiro09.simpleplayerpee.commands.PeeMode;
import me.yoshiro09.simpleplayerpee.commands.SuperPee;
import me.yoshiro09.simpleplayerpee.events.DrinkWaterEvent;
import me.yoshiro09.simpleplayerpee.events.PeeingEvent;
import me.yoshiro09.simpleplayerpee.events.PeeingPlayerDeathEvent;
import me.yoshiro09.simpleplayerpee.events.SessionsEvent;
import me.yoshiro09.simpleplayerpee.events.WaterLevelChangedEvent;
import me.yoshiro09.simpleplayerpee.nbtwrapper.NBT_PersistentDataHolder;
import me.yoshiro09.simpleplayerpee.runnables.DamagePlayer;
import me.yoshiro09.simpleplayerpee.runnables.RemoveWaterLevel;
import me.yoshiro09.simpleplayerpee.utils.DebuffCheck;
import me.yoshiro09.simpleplayerpee.utils.MessagesManager;
import me.yoshiro09.simpleplayerpee.utils.RandomNumber;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	private static DebuffCheck debuffCheck;
	private static NBT_PersistentDataHolder nbtWrapper;
	
	public HashMap<UUID, PeeingPlayer> peeingPlayersList = new HashMap<>();
	
	private File languageFile;
	private FileConfiguration languageConfiguration;
	private File playerdataFile;
	private FileConfiguration playerdataConfiguration;
	
	public File getLanguageFile() {
		return this.languageFile;
	}
	
	public FileConfiguration getLanguageConfig() {
		return this.languageConfiguration;
	}
	
	public File getPlayerdataFile() {
		return this.playerdataFile;
	}
	
	public FileConfiguration getPlayerdataConfig() {
		return this.playerdataConfiguration;
	}
	
	public void onEnable() {
		plugin = this;
		nbtWrapper = new NBT_PersistentDataHolder();
		debuffCheck = new DebuffCheck(this);
		
		checkFiles();
		registerEvents();
		registerCommands();
		runnables();
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new SessionsEvent(this, debuffCheck), this);
		getServer().getPluginManager().registerEvents(new PeeingEvent(this), this);
		getServer().getPluginManager().registerEvents(new DrinkWaterEvent(this, debuffCheck, new RandomNumber()), this);
		getServer().getPluginManager().registerEvents(new WaterLevelChangedEvent(debuffCheck), this);
		getServer().getPluginManager().registerEvents(new PeeingPlayerDeathEvent(this), this);
	}
	
	private void registerCommands() {
		getCommand("peemode").setExecutor(new PeeMode(this, new MessagesManager()));
		getCommand("superpee").setExecutor(new SuperPee(this, new MessagesManager()));
		getCommand("peeinfo").setExecutor(new PeeInfo(this, new MessagesManager()));
	}
	
	private void runnables() {
		new RemoveWaterLevel(this, new RandomNumber()).start();
		new DamagePlayer(this).start();
	}
	
	private void checkFiles() {
		this.languageFile = new File(getDataFolder(), "language.yml");
		this.playerdataFile = new File(getDataFolder(), "playerdata.yml");
		
		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
		
		if (!this.languageFile.exists()) 
			saveResource("language.yml", false);
		
		if (!this.playerdataFile.exists()) 
			saveResource("playerdata.yml", false);
		
		this.languageConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(this.languageFile);
		this.playerdataConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(this.playerdataFile);
	}
	
	public static Main getInstance() {
		return plugin;
	}

	public NBT_PersistentDataHolder getNBTWrapper() {
		return nbtWrapper;
	}
	
	public static DebuffCheck getDebuffCheck() {
		return debuffCheck;
	}
}
