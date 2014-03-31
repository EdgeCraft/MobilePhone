package org.exodevil.MobilePhone;

import java.io.File;
import java.util.HashMap;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.exodevil.MobilePhone.commands.AnswerCallCommand;
import org.exodevil.MobilePhone.commands.CallCommand;
import org.exodevil.MobilePhone.commands.DenyCallCommand;
import org.exodevil.MobilePhone.commands.HangUpCommand;
import org.exodevil.MobilePhone.commands.NumberCommand;
import org.exodevil.MobilePhone.commands.SMSCommand;
import org.exodevil.MobilePhone.commands.SearchNumberCommand;
import org.exodevil.MobilePhone.commands.SearchUserByNumberCommand;
import org.exodevil.MobilePhone.commands.ServiceCommand;
import org.exodevil.MobilePhone.listeners.PlayerJoinListener;
import org.exodevil.MobilePhone.listeners.SignClickListener;
import org.exodevil.MobilePhone.listeners.SignPlaceListener;

public class MobilePhone extends JavaPlugin {

	public FileConfiguration config; 
	public static String name = "[MobilePhone]";
	public static MobilePhone instance;
	
	final DatabaseHandler DatabaseHandler = EdgeCoreAPI.databaseAPI();
	final UserManager UserManager = EdgeCoreAPI.userAPI();
	public static HashMap<Integer, String> numbers = new HashMap<Integer, String> ();

	@Override
	public void onEnable(){
		instance = this;
		System.out.println(name + " loading configs"); 
		PluginDescriptionFile descFile = this.getDescription();
		System.out.println(name + " Version: " + descFile.getVersion() + " by " + descFile.getAuthors());
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new SignPlaceListener(this), this);
		pm.registerEvents(new SignClickListener(this), this);
		pm.registerEvents(new PlayerJoinListener(), this );
		System.out.println(name + " update list of phone numbers");
		System.out.println(name + " plugin loaded and enabled");
		registerCommands();
	}

	public void onDisable(){
		System.out.println(name + " saving configs");
		Bukkit.getScheduler().cancelAllTasks();
		System.out.println(name + " plugin disabled");
	}

	public void loadConfig(){
		config = getConfig();
		config.options().copyDefaults(true);
			
		if (new File("plugins/MobilePhone/config.yml").exists()) {
			System.out.println(name + " configs successfully created and loaded");
		} else {
			saveDefaultConfig();
			System.out.println(name + " configs successfully created and loaded");
		}
	}
	
	public void registerCommands() {
		this.getCommand("number").setExecutor(new NumberCommand());
		this.getCommand("call").setExecutor(new CallCommand());
		this.getCommand("sms").setExecutor(new SMSCommand(this));
		this.getCommand("answer").setExecutor(new AnswerCallCommand());
		this.getCommand("deny").setExecutor(new DenyCallCommand());
		this.getCommand("hangup").setExecutor(new HangUpCommand());
		this.getCommand("searchnumber").setExecutor(new SearchNumberCommand());
		this.getCommand("searchuser").setExecutor(new SearchUserByNumberCommand());
		this.getCommand("service").setExecutor(new ServiceCommand());
	}
	
	public static MobilePhone getInstance() {
		return instance;
	}

}
