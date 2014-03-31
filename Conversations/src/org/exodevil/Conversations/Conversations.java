package org.exodevil.Conversations;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Conversations extends JavaPlugin {
	
	public static Conversations instance;
	public static String name = "[Conversations]";
	
	@Override
	public void onEnable() {
		instance = this;
		PluginDescriptionFile descFile = this.getDescription();
		System.out.println(name + " Version: " + descFile.getVersion() + " by " + descFile.getAuthors());
		this.getCommand("conversation").setExecutor(new ConCommand());
		System.out.println(name + " Plugin wurde erfolgreich geladen.");
	}
	
	public void onDisable() {
		
	}
		
	public static Conversations getInstance() {
		return instance;
	}
	
	public void registerCommands() {
		
	}
	
}
