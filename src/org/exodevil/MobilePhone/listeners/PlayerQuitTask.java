package org.exodevil.MobilePhone.listeners;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.exodevil.MobilePhone.commands.HangUpTask;
import org.exodevil.MobilePhone.util.Memory;
import org.exodevil.MobilePhone.util.Phonebook;

public class PlayerQuitTask implements Listener {

	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		User u = userManager.getUser(p.getName());
		int userID = u.getID();
		boolean isInCall = Memory.inCALL.containsKey(userID);
		if (isInCall == true) {
			cancelCall(u);
		}
		if (Memory.beginnCALL.containsKey(u.getID())) {
			Memory.beginnCALL.remove(u.getID());
		}		
	}
	
	
	public static void cancelCall(User u) {
		if (Memory.isP.containsValue(u.getID())) {
			//Quit von Anrufer
			int pID = u.getID();
			int recID = Memory.caller.get(pID);
			User payer = userManager.getUser(pID);
			String numberRec = Phonebook.getNumberByUser(recID);
			String numberP = Phonebook.getNumberByUser(pID);
			Memory.inCALL.remove(pID);
			Memory.inCALL.remove(recID);
			HangUpTask.cancelChannel(numberP, numberRec, payer);
		} else if (Memory.isRec.containsValue(u.getID())) {
			//Quit von Receiver
			int recID = u.getID();
			int pID = Memory.receiver.get(recID);
			User payer = userManager.getUser(pID);
			String numberRec = Phonebook.getNumberByUser(recID);
			String numberP = Phonebook.getNumberByUser(pID);
			Memory.inCALL.remove(pID);
			Memory.inCALL.remove(recID);
			HangUpTask.cancelChannel(numberP, numberRec, payer);
		}
	}
}