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
	public static int TaskID;

	public static void Ringing (final Player player, final Player receiver){

		User u = userManager.getUser(player.getName());
		final int id = u.getID();
			TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MobilePhone.getInstance(), new Runnable() {
				@Override
				public void run() {
					System.out.println("Debug#2");
					if (Memory.beginnCALL.containsKey(id)) {
					receiver.sendMessage("Ring!!");
					receiver.sendMessage("Eingehender Anruf von: " + player.getName());
					receiver.playSound(receiver.getLocation(), Sound.LEVEL_UP, 10, 1);
					} else {
						Bukkit.getScheduler().cancelTask(TaskID);
					}
				}
			}, 0L, 5*20L);
			
	}
	
	public static void Occupied (final Player player, final Player receiver) {
		final User p = userManager.getUser(player.getName());
		final User rec = userManager.getUser(receiver.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(MobilePhone.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (Memory.beginnCALL.containsKey(p.getID())) {
					Bukkit.getScheduler().cancelTask(TaskID);
					player.sendMessage("Der angerufene Teilnehmer ist zur Zeit nicht erreichbar.");
					Memory.beginnCALL.remove(p.getID());
					Memory.beginnCALL.remove(rec.getID());
					Memory.tempCALL2.remove(p.getID());
					Memory.tempCALL.remove(rec.getID());
				}				
			}
		}, 1200L);
	}	
}
