package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.util.Memory;

public class DenyCallCommand implements CommandExecutor {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();
	
	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player receiver = (Player) cmds;
			User rec = userManager.getUser(receiver.getName());
			if (Memory.beginnCALL.containsKey(rec.getID())) {
				Memory.beginnCALL.remove(rec.getID());
				int pID = Memory.receiver.get(rec.getID());
				int recID = rec.getID();
				Memory.beginnCALL.remove(recID);
				
				receiver.sendMessage("Anruf abgelehnt.");
				User p = userManager.getUser(pID);
				Player player = Bukkit.getPlayer(p.getName());
				player.sendMessage("Anruf wurde nicht angenommen.");				
				return true;
			} else {
				receiver.sendMessage(lang.getColoredMessage(rec.getLanguage(), "phone_not_called"));
			}
			
			return true;
		}
		

	}
}
