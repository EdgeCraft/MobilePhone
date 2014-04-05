package org.exodevil.MobilePhone.commands;

import java.sql.PreparedStatement;

import net.edgecraft.edgeconomy.EdgeConomyAPI;
import net.edgecraft.edgeconomy.economy.BankAccount;
import net.edgecraft.edgeconomy.economy.Economy;
import net.edgecraft.edgeconomy.economy.EconomyPlayer;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;
import net.edgecraft.edgecore.system.EdgeCraftSystem;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.Phonebook;
import org.exodevil.MobilePhone.listeners.BuyMobileTask;

public class BuyMobileCommand implements CommandExecutor {

	final UserManager userManager = EdgeCoreAPI.userAPI();
	final Economy economy = EdgeConomyAPI.economyAPI();
	final EdgeCraftSystem EdgeSystem = EdgeCoreAPI.systemAPI();
	final DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player p = (Player) cmds;
			EconomyPlayer ep = economy.getEconomyPlayer(p.getName());
			BankAccount acc = economy.getAccount(p.getName());
			double Handykosten = MobilePhone.getInstance().getConfig().getInt("handy.kosten");
			User playerName = userManager.getUser(p.getName());
				String hasnumber = Phonebook.getNumberByUser(playerName.getID());
				if (hasnumber.length() >= 3){
					BuyMobileTask.payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
					p.sendMessage("Deine Handynumber lautet: "  + hasnumber);
				} else {
					try {
						String number = BuyMobileTask.generatenumber(playerName);
						BuyMobileTask.payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
						p.sendMessage("Gl�ckwunsch. Du hast dir dein erstes Handy gekauft");
						p.sendMessage("Deine Handynumber lautet: " + number);
						int id = userManager.getUser(p.getName()).getID();
						PreparedStatement registerNumber = db.prepareStatement("INSERT INTO mobilephone_contracts (id, number) VALUES (?, ?);");
						registerNumber.setInt(1, id);
						registerNumber.setString(2, number);
						registerNumber.executeUpdate();
						Phonebook.synchronizeUsers();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return true;
		}
	}
}
