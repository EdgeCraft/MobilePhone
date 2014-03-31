package org.exodevil.MobilePhone.listeners;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.sms.Memory;

public class IncomingCallTask {

	private final static UserManager userManager = EdgeCoreAPI.userAPI();


	public static void Ringing (final Player player, final Player receiver){

		User u = userManager.getUser(player.getName());
		final int id = u.getID();
		do {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(MobilePhone.getInstance(), new Runnable() {
				@Override
				public void run() {
					receiver.sendMessage("Ring!!");
					receiver.sendMessage("Eingehender Anruf von: " + player.getName());
					receiver.playSound(receiver.getLocation(), Sound.LEVEL_UP, 10, 1);
				}
			}, 0L, 5*20L);
			
		} while (Memory.beginnCALL.contains(id));
	}
}
