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
import org.exodevil.MobilePhone.sms.Memory;

public class HangUpCommand implements CommandExecutor {
	
	private final UserManager userManager = EdgeCoreAPI.userAPI();
	private final ChatHandler chatHandler = EdgeCoreAPI.chatAPI(); 
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();
	
	@SuppressWarnings({ "unused", "static-access" })
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
			if (isInCall = false) {
				cmds.sendMessage(lang.getColoredMessage(u.getLanguage(), "phone_not_in_call"));
			} else {
				for (Channel channel : chatHandler.getInstance().getChannels().values()) {
					boolean isChannel = channel.getChannelName().startsWith("MP_");
					if (isChannel = true) {
						channel.delete();
						long endCall = System.currentTimeMillis();
						double time = Billing.calcTime(endCall, u);
						
						try {
							Billing.payCall(u.getID(), time);
						} catch (Exception e) {
							//Error-Handling
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
		
		return true;
	}
}
