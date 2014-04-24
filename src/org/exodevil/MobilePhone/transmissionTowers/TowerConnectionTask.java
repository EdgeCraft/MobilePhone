package org.exodevil.MobilePhone.transmissionTowers;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.util.Memory;

public class TowerConnectionTask {

	static int TaskID;

	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	public static boolean checkConnection (final Player player) {
		boolean connected = false;
		try {
			connected = TowerUtil.isTowerReachable(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connected;
	}

	public static void updateConnections () {
		TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MobilePhone.getInstance(), new Runnable() {
			@Override
			public void run () {
				for (User user : userManager.getUsers().values()) {
					Player player = Bukkit.getPlayer(user.getName());
					boolean connected = checkConnection(player);
					if (connected == true) {
						Memory.hasConnection.add(user.getID());
					}
					if (connected == false) {
						if (Memory.hasConnection.contains(user.getID())) {
							Memory.hasConnection.remove(user.getID());
						}
					}
				}
			}
		}, 0L, 30*20L);
	}
}
