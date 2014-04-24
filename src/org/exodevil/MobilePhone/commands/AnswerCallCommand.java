package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCore;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.command.AbstractCommand;
import net.edgecraft.edgecore.command.Level;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.listeners.BuyMobileTask;
import org.exodevil.MobilePhone.util.Memory;

public class AnswerCallCommand extends AbstractCommand {

	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	
	private static final AnswerCallCommand instance = new AnswerCallCommand();
	
	public static AnswerCallCommand getInstance() {
		return instance;
	}

	@Override
	public Level getLevel() {
		return Level.USER;
	}

	@Override
	public String[] getNames() {
		return new String[] { "answer" };
	}

	@Override
	public boolean runImpl(Player player, User user, String[] args) throws Exception {
		Player receiver = player;
		User rec = userManager.getUser(receiver.getName());
		if (Memory.beginnCALL.containsKey(rec.getID())) {
			boolean hasMobile = BuyMobileTask.hasMobile(rec);
			if (hasMobile == false) {
				receiver.sendMessage(lang.getColoredMessage(rec.getLanguage(), "phone_mobile_in_hand"));
				return true;
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

	@Override
	public void sendUsageImpl(CommandSender cmds) {
		cmds.sendMessage(EdgeCore.usageColor + "answer");
	}

	@Override
	public boolean validArgsRange(String[] args) {
		return args.length == 1;
	}

	/*@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player receiver = (Player) cmds;
			User rec = userManager.getUser(receiver.getName());
			if (Memory.beginnCALL.containsKey(rec.getID())) {
				boolean hasMobile = BuyMobileTask.hasMobile(rec);
				if (hasMobile == false) {
					receiver.sendMessage(lang.getColoredMessage(rec.getLanguage(), "phone_mobile_in_hand"));
					return true;
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


	}*/

}
