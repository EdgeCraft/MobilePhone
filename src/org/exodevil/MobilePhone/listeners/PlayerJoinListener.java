package org.exodevil.MobilePhone.listeners;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.exodevil.MobilePhone.sms.Memory;

public class PlayerJoinListener implements Listener {
	
	private final UserManager userManager = EdgeCoreAPI.userAPI();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		User u = userManager.getUser(p.getName());
		if (Memory.SMS.containsKey(u.getID())) {
			p.sendMessage("Du hast eine Nachricht erhalten");
			p.sendMessage(Memory.SMS.get(u.getID()));
			Memory.SMS.remove(u.getID());
		}
		
		if (Memory.missedCALL.containsKey(u.getID())) {
			   
			   for (String missed : Memory.missedCALL.get(u.getID())) {
			        p.sendMessage("Verpasster Anruf von " + missed);
			    }
			}
	}
}
