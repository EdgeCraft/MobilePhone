package org.exodevil.MobilePhone.commands;

import java.util.List;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.chat.Channel;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.Phonebook;
import org.exodevil.MobilePhone.listeners.IncomingCallTask;
import org.exodevil.MobilePhone.listeners.SignClickListener;
import org.exodevil.MobilePhone.services.Service;
import org.exodevil.MobilePhone.sms.Memory;

public class CallCommand implements CommandExecutor {

	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();
	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		Player player = (Player) cmds;
		User p = userManager.getUser(player.getName());
		boolean mobile = SignClickListener.hasMobile(p);
		if (mobile = false) {
			player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_mobile_in_hand"));
			return false;
		} else {
			if ((args.length != 1)) {
				cmds.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_only_one_number"));
				return true;
			}
			String number = args.toString();
			//Service-Nummern erkennen
			boolean isService = Phonebook.isServiceNumber(number);
			if (isService = true) {
				Service.start(player);
				return true;
			} else {


				User searchFor = Phonebook.getUserByNumber(number);
				Player receiver =  (Player) searchFor;
				User rec = userManager.getUser(receiver.getName());
				if (!(receiver.isOnline())) {
					player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_receiver_offline"));
					List<String> missed = Memory.missedCALL.get(p.getID());

					missed.add(player.getName());

					Memory.missedCALL.put(p.getID(), missed);
					return true;
				}
				boolean PIsInCall = Phonebook.UserIsInCall(p.getID());
				boolean RIsInCall = Phonebook.UserIsInCall(rec.getID());
				if (PIsInCall = false) {
					if (RIsInCall = false) {
						Memory.beginnCALL.add(p.getID());
						IncomingCallTask.Ringing(player, receiver);
						Memory.tempCALL.put(rec.getID(), p.getID());
						return true;
					}
					player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_receiver_busy"));
					return true;
				}
				player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_in_call"));

				return true;
			}
		}
	}

	public static void openChannel(User rec) {
		int pID = Memory.tempCALL.get(rec.getID());
		Memory.inCALL.put(pID, true);
		Memory.inCALL.put(rec.getID(), true);
		String numberP = Phonebook.getNumberByUser(pID);
		String chid = "MP_" + numberP;
		Channel channel =  new Channel(chid, false, null);
		EdgeCoreAPI.chatAPI().addChannel(channel);
		User p = userManager.getUser(pID);
		channel.addMember(p);
		channel.addMember(rec);
		long beginnCall = System.currentTimeMillis();
		Memory.callLength.put(chid, beginnCall);
	}

}
