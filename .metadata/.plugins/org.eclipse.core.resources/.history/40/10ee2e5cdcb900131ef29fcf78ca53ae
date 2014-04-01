package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.Phonebook;

public class NumberCommand implements CommandExecutor {
	
	private final UserManager userManager = EdgeCoreAPI.userAPI();
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label, String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		
		Player player = (Player) cmds;
		User user = this.userManager.getUser(player.getName());
		int id = user.getID();
		String number = Phonebook.getNumberByUser(id);
		player.sendMessage(lang.getColoredMessage(user.getLanguage(), "phone_your_number").replace("[0]", number));
		return true;	
	}
}
