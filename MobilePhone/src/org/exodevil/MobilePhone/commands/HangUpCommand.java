package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.chat.Channel;
import net.edgecraft.edgecore.chat.ChatHandler;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.Billing;
import org.exodevil.MobilePhone.Phonebook;
import org.exodevil.MobilePhone.sms.Memory;

public class HangUpCommand implements CommandExecutor {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	private final ChatHandler chatHandler = EdgeCoreAPI.chatAPI(); 
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	@SuppressWarnings("static-access")
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
				for (Channel channel : chatHandler.getInstance().getChannels().values()) {
					if (Memory.tempCALL.containsKey(userID)) { 
						//hangup by receiver
						int recID = Memory.tempCALL.get(userID);
						String numberP = Phonebook.getNumberByUser(recID);
						String numberRec = Phonebook.getNumberByUser(userID);
						Memory.tempCALL2.remove(userID);
						Memory.tempCALL.remove(recID);
						Memory.inCALL.remove(userID);
						Memory.inCALL.remove(recID);
						boolean cancel = isChannel(channel, numberP, numberRec, u);
						if (cancel == true) break;
						continue;
					} else if (Memory.tempCALL2.containsKey(userID)) {
						//hangup by player
						int recID = Memory.tempCALL.get(userID);
						String numberP = Phonebook.getNumberByUser(userID);
						String numberRec = Phonebook.getNumberByUser(recID);
						Memory.tempCALL2.remove(userID);
						Memory.tempCALL.remove(recID);
						Memory.inCALL.remove(userID);
						Memory.inCALL.remove(recID);
						boolean cancel = isChannel(channel, numberP, numberRec, u);
						if (cancel == true) break;
						continue;
					}					
				}
			}
		}
		return true;
	}

	public boolean isChannel (Channel channel, String numberP, String numberRec, User u) {
		boolean isChannel = channel.getChannelName().contains("MP_" + numberP);
		if (isChannel == true) {
			channel.delete();
			long endCall = System.currentTimeMillis();
			double time = Billing.calcTime(endCall, u);
			try {
				Billing.payCall(u.getID(), time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
