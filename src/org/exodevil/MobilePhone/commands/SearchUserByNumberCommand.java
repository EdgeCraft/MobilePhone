package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.user.User;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.Phonebook;

public class SearchUserByNumberCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand (CommandSender cmds, Command cmd, String label, String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		if ((args.length != 1)) {
			cmds.sendMessage("Bitte gib nur eine Nummer ein. /searchuser <Nummer>");
		}
		Player player = (Player) cmds;
		String number = args.toString();
		User searchFor = Phonebook.getUserByNumber(number);
		
		player.sendMessage(MobilePhone.name + "Die Handynummer " + number + "gehört " + searchFor);
		return true;	
	}
}
