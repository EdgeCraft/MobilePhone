package org.exodevil.MobilePhone.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
	OfflinePlayer[] offline = Bukkit.getOfflinePlayers();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,
			String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player player = (Player) cmds;
			player.sendMessage("" + offline.toString());
			return true;
		}
	}
}
