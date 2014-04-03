package org.exodevil.MobilePhone.listeners;

import net.edgecraft.edgeconomy.EdgeConomyAPI;
import net.edgecraft.edgeconomy.economy.BankAccount;
import net.edgecraft.edgeconomy.economy.Economy;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.Billing;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.Phonebook;
import org.exodevil.MobilePhone.commands.HangUpCommand;

public class WatchCostsTask {
	private final static UserManager userManager = EdgeCoreAPI.userAPI();
	private final static Economy economy = EdgeConomyAPI.economyAPI();
	public static int TaskID;
	
	public static void Watch (final int pID, final int recID){
		final User p = userManager.getUser(pID);
		final User rec = userManager.getUser(recID);
		final Player player = Bukkit.getPlayer(p.getName());
		final Player receiver = Bukkit.getPlayer(rec.getName());
		TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MobilePhone.getInstance(), new Runnable() {
				@Override
				public void run() {
					long endCall = System.currentTimeMillis();
					double time = Billing.calcTime(endCall, p);
					double costs = Billing.calcCosts(p.getID(), time);
					String control = "Kostenkontrolle Gesprächszeit: " + time + " Minuten. Gesprächskosten: " + costs;
					player.sendMessage(control);
					receiver.sendMessage(control);
					//fehlerhaft
					/*BankAccount acc = economy.getAccount(pID);
					String numberRec = Phonebook.getNumberByUser(recID);
					String numberP = Phonebook.getNumberByUser(pID);
					if (costs >= acc.getBalance()) {
						HangUpCommand.cancelChannel(numberP, numberRec, p);
						player.sendMessage("Anruf wurde beendet. Dein Konto ist leer.");
						Bukkit.getScheduler().cancelTask(TaskID);
					}*/
				}
			}, 0L, 30*20L);
	}
}
