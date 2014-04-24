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
import org.exodevil.MobilePhone.listeners.IncomingCallTask;
import org.exodevil.MobilePhone.listeners.BuyMobileTask;
import org.exodevil.MobilePhone.listeners.WatchCostsTask;
import org.exodevil.MobilePhone.util.Memory;
import org.exodevil.MobilePhone.util.Phonebook;

public class CallCommand implements CommandExecutor {

	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();
	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}
		Player player = (Player) cmds;
		User p = userManager.getUser(player.getName());
		boolean mobile = BuyMobileTask.hasMobile(p);
		if (mobile == false) {
			player.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_mobile_in_hand"));
			return true;
		} else {
			if (Memory.hasConnection.contains(p.getID())) {
				player.sendMessage("Du hast keinen Empfang.");
				return true;
			}
			if ((args.length != 1)) {
				cmds.sendMessage(lang.getColoredMessage(p.getLanguage(), "phone_only_one_number"));
				return true;
			}
			String number = args[0].toString();
			User searchFor = Phonebook.getUserByNumber(number);
			if (searchFor == null) {
				player.sendMessage("Nummer nicht gefunden");
				return true;
			}
			Player receiver =  Bukkit.getPlayer(searchFor.getName());
			User rec = userManager.getUser(receiver.getName());
			String numberPlayer = Phonebook.getNumberByUser(p.getID());
			if (numberPlayer.equals(number)) {
				player.sendMessage("Du kannst dich nicht selber anrufen.");
				return true;
			}
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
					String numberP = Phonebook.getNumberByUser(p.getID());
					Memory.beginnCALL.put(p.getID(), true);
					Memory.beginnCALL.put(rec.getID(), true);
					IncomingCallTask.Ringing(player, receiver);
					IncomingCallTask.Occupied(player, receiver);
					Memory.isP.put("MP_" + numberP, p.getID());
					Memory.isRec.put("MP_" + numberP, rec.getID());
					Memory.receiver.put(rec.getID(), p.getID());
					Memory.caller.put(p.getID(), rec.getID());
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

	public static void openChannel(int recID, int pID) {
		Memory.inCALL.put(pID, true);
		Memory.inCALL.put(recID, true);
		String numberP = Phonebook.getNumberByUser(pID);
		String chid = "MP_" + numberP;
		System.out.println(chid);
		Channel channel =  new Channel(chid, false, Material.CARROT_ITEM);
		EdgeCoreAPI.chatAPI().addChannel(channel);
		User p = userManager.getUser(pID);
		User rec = userManager.getUser(recID);
		channel.addMember(p);
		channel.addMember(rec);
		long beginnCall = System.currentTimeMillis();
		System.out.println("Beginn: " + beginnCall);
		Memory.callLength.put(chid, beginnCall);
		WatchCostsTask.Watch(pID, recID);
	}

}
