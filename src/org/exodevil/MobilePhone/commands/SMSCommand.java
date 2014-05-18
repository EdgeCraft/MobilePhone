package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgeconomy.EdgeConomyAPI;
import net.edgecraft.edgeconomy.economy.Economy;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.listeners.BuyMobileTask;
import org.exodevil.MobilePhone.util.Memory;
import org.exodevil.MobilePhone.util.Phonebook;

public class SMSCommand implements CommandExecutor {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	private final Economy economy = EdgeConomyAPI.economyAPI();

	private MobilePhone plugin;

	public SMSCommand(MobilePhone plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player player = (Player) cmds;
			User user = userManager.getUser(player.getName());
			boolean mobile = BuyMobileTask.hasMobile(user);
			if (mobile == false) {
				player.sendMessage("Bitte nimm dein Handy in die Hand");
				return false;
			} else {
				if (Memory.hasConnection.contains(user.getID())) {
					player.sendMessage("Du hast keinen Empfang.");
					return true;
				}
				if ((args.length < 2)) {
					cmds.sendMessage("Bitte beachte die Syntax /sms <Nummer> <Nachricht>");
					return true;
				} else {
					String text = "";
					for (int i = 1; i < args.length; i++) {
						text = text + args[i];
						if (i + 1 < args.length) {
							text =  text + " ";
						}
					}
					String args0 = "";
					args0 = args[0].toString();
					User rec = Phonebook.getUserByNumber(args0);
					double SMSCost = this.plugin.getConfig().getDouble("sms.kosten");
					if (rec != null) {
						Player recipient = Bukkit.getPlayer(rec.getName());
						if (recipient == null) {
							int id = rec.getID();
							Memory.SMS.put(id,"Von " + player.getName() + ": " + text);
							player.sendMessage("Dir wurden " + SMSCost + " abgezogen");
							player.sendMessage("Deine Nachricht wurde erfolgreich zugestellt.");
							return true;
						}
						double balanceP = economy.getAccount(user.getID()).getBalance();
						
							boolean hasMobile = MobilePhone.numbers.containsKey(rec.getID());
							if (hasMobile == false) {
								player.sendMessage("Dein eingegebener Empf�nger besitzt kein Handy");
								return true;
							} else {
								if (balanceP >= SMSCost) {
									player.sendMessage("Dir wurden " + SMSCost + " abgezogen");
									recipient.sendMessage("Du hast eine Nachricht von: " + player.getName());
									recipient.sendMessage(text);
									player.sendMessage("Deine Nachricht wurde erfolgreich zugestellt.");
									return true;
								} else {
									player.sendMessage("Du hast nicht genug Geld auf deinem Konto.");
									return true;
								}
							}
					} else {
						cmds.sendMessage("Die angegebene Nummer konnte nicht gefunden werden.");
						return true;
					}
				}
			}
		}
	}
}
