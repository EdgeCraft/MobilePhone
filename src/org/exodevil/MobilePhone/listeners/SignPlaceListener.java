package org.exodevil.MobilePhone.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.exodevil.MobilePhone.MobilePhone;

public class SignPlaceListener implements Listener {

	private MobilePhone plugin;

	public SignPlaceListener(MobilePhone plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player p = e.getPlayer();
		String line0 = e.getLine(0);
		String line1 = e.getLine(1);
		String line2 = e.getLine(2);
		String line3 = e.getLine(3);
		String text1 = this.plugin.getConfig().getString("sign.line1");
		String text2 = this.plugin.getConfig().getString("sign.line2");
		String text3 = this.plugin.getConfig().getString("sign.line3");
		String text4 = this.plugin.getConfig().getString("sign.line4");
		if (line0.equalsIgnoreCase("[MobilePhone]")){
			if (line1.equalsIgnoreCase("buy")){
				if (line2.equalsIgnoreCase("")){
					if (line3.equalsIgnoreCase("")){
						if (p.hasPermission("mobilephone.sign.buy.create") || p.isOp()){
							e.setLine(0, text1);
							e.setLine(1, text2);
							e.setLine(2, text3);
							e.setLine(3, text4);
						}
					}
				}
			}
		}
	}
}
