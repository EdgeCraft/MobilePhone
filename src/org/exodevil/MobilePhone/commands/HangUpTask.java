package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.chat.Channel;
import net.edgecraft.edgecore.chat.ChatHandler;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.listeners.PlayerQuitTask;
import org.exodevil.MobilePhone.listeners.WatchCostsTask;
import org.exodevil.MobilePhone.util.Billing;
import org.exodevil.MobilePhone.util.Memory;

public class HangUpTask implements CommandExecutor {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	private static final ChatHandler chatHandler = EdgeCoreAPI.chatAPI(); 
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		Player p = (Player) cmds;
		User u = userManager.getUser(p.getName());
		int userID = u.getID();


		if (args.length > 0) {
			p.sendMessage(lang.getColoredMessage(u.getLanguage(), "phone_hangup_syntax"));
			return true;
		} else if (args.length == 0) {
			boolean isInCall = Memory.inCALL.containsKey(userID);
			if (isInCall == false) {
				cmds.sendMessage(lang.getColoredMessage(u.getLanguage(), "phone_not_in_call"));
			} else {
				PlayerQuitTask.cancelCall(u);
			}
		}
		return true;
	}

	public static void cancelChannel (String numberP, String numberRec, User u) {
		Channel channel = chatHandler.getChannel("MP_" + numberP);
		if (channel == null) channel = chatHandler.getChannel("MP_" + numberRec);
		channel.delete();
		Bukkit.getScheduler().cancelTask(WatchCostsTask.TaskID);
		long endCall = System.currentTimeMillis();
		System.out.println("Ende: " + endCall);
		double time = Billing.calcTime(endCall, u);
		try {
			Billing.payCall(u.getID(), time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
