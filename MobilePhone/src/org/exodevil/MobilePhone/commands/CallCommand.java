package org.exodevil.MobilePhone.commands;

import java.util.List;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.chat.Channel;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.Phonebook;
import org.exodevil.MobilePhone.listeners.IncomingCallTask;
import org.exodevil.MobilePhone.listeners.SignClickListener;
import org.exodevil.MobilePhone.sms.Memory;

public class CallCommand implements CommandExecutor {

	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();
	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		System.out.println("Debug#4");
		Player player = (Player) cmds;
		User p = userManager.getUser(player.getName());
		boolean mobile = SignClickListener.hasMobile(p);
		if (mobile == false) {
			player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_mobile_in_hand"));
			return true;
		} else {
			if ((args.length != 1)) {
				cmds.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_only_one_number"));
				return true;
			}
			System.out.println("Debug#5");
			String number = args[0].toString();
			User searchFor = Phonebook.getUserByNumber(number);
			System.out.println("Mobile " + number);
			System.out.println("Mobile " + searchFor);
			Player receiver =  Bukkit.getPlayer(searchFor.getName());
			User rec = userManager.getUser(receiver.getName());
			System.out.println("Mobile " + receiver);
			if (!(receiver.isOnline())) {
				player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_receiver_offline"));
				List<String> missed = Memory.missedCALL.get(p.getID());

				missed.add(player.getName());

				Memory.missedCALL.put(p.getID(), missed);
				return true;
			}
			boolean PIsInCall = Phonebook.UserIsInCall(p.getID());
			boolean RIsInCall = Phonebook.UserIsInCall(rec.getID());
			if (PIsInCall == false) {
				if (RIsInCall == false) {
					Memory.beginnCALL.put(p.getID(), true);
					Memory.beginnCALL.put(rec.getID(), true);
					System.out.println("Debug#1");
					IncomingCallTask.Ringing(player, receiver);
					IncomingCallTask.Occupied(player, receiver);
					Memory.tempCALL.put(rec.getID(), p.getID());
					Memory.tempCALL2.put(p.getID(), rec.getID());
					System.out.println("Debug#3");
					return true;
				} else {
					player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_receiver_busy"));
					return true;
				}
			} else {
				player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_in_call"));
				
			}
			
			return true;
		}
	}

	public static void openChannel(User rec) {
		int pID = Memory.tempCALL.get(rec.getID());
		Memory.inCALL.put(pID, true);
		Memory.inCALL.put(rec.getID(), true);
		String numberP = Phonebook.getNumberByUser(pID);
		String chid = "MP_" + numberP;
		System.out.println(chid);
		Channel channel =  new Channel(chid, false, null);
		System.out.println("Debug#8");
		EdgeCoreAPI.chatAPI().addChannel(channel);
		User p = userManager.getUser(pID);
		channel.addMember(p);
		channel.addMember(rec);
		long beginnCall = System.currentTimeMillis();
		Memory.callLength.put(chid, beginnCall);
	}

}
