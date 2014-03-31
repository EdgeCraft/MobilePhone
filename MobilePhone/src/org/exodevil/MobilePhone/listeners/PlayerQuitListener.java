package org.exodevil.MobilePhone.listeners;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.chat.Channel;
import net.edgecraft.edgecore.chat.ChatHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.exodevil.MobilePhone.Billing;
import org.exodevil.MobilePhone.sms.Memory;

public class PlayerQuitListener implements Listener {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	private final ChatHandler chatHandler = EdgeCoreAPI.chatAPI(); 

	@SuppressWarnings({ "unused", "static-access" })
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		User u = userManager.getUser(p.getName());
		int userID = u.getID();
		boolean isInCall = Memory.inCALL.containsKey(userID);
		if (isInCall = true) {
			for (Channel channel : chatHandler.getInstance().getChannels().values()) {
				boolean isChannel = channel.getChannelName().startsWith("MP_");
				if (isChannel = true) {
					channel.delete();
					long endCall = System.currentTimeMillis();
					double time = Billing.calcTime(endCall, u);
					try {
						Billing.payCall(u.getID(), time);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				}

			}
		}
		
		if (Memory.beginnCALL.contains(u.getID())) {
			Memory.beginnCALL.remove(u.getID());
		}		
	}
}