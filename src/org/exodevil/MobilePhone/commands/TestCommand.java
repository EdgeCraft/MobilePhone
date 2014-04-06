package org.exodevil.MobilePhone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.transmissionTowers.Util;

public class TestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,
			String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player player = (Player) cmds;
			try {
				Util.buildTower(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
