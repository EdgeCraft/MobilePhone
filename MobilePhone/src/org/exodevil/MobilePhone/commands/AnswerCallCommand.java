package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.listeners.SignClickListener;
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
			Player receiver = (Player) cmds;
			User rec = userManager.getUser(receiver.getName());
			if (Memory.beginnCALL.containsKey(rec.getID())) {
				boolean hasMobile = SignClickListener.hasMobile(rec);
				if (hasMobile == false) {
					receiver.sendMessage(lang.getColoredMessage(rec.getLanguage(), "phone_mobile_in_hand"));
				}
				int pID = Memory.receiver.get(rec.getID());
				Memory.beginnCALL.remove(rec.getID());
				Memory.beginnCALL.remove(pID);
				CallCommand.openChannel(rec.getID(), pID);
				return true;
			} else {
				receiver.sendMessage(lang.getColoredMessage(rec.getLanguage(), "phone_not_called"));
			}
			
			return true;
		}
		

	}
	
}
