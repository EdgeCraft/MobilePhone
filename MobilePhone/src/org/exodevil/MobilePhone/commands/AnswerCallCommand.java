package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.sms.Memory;

public class AnswerCallCommand implements CommandExecutor {
	
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	private final UserManager userManager = EdgeCoreAPI.userAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player player = (Player) cmds;
			User p = userManager.getUser(player.getName());
			if (Memory.beginnCALL.contains(p.getID())) {
				CallCommand.openChannel(p);
				return true;
			} else {
				player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_not_called"));
			}
			
			return true;
		}
		

	}
	
}
