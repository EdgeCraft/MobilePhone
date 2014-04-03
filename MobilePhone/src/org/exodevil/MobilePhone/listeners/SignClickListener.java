package org.exodevil.MobilePhone.listeners;

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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.Phonebook;

public class SignClickListener implements Listener {

	final UserManager userManager = EdgeCoreAPI.userAPI();
	final static Economy economy = EdgeConomyAPI.economyAPI();
	final EdgeCraftSystem EdgeSystem = EdgeCoreAPI.systemAPI();
	final DatabaseHandler db = EdgeCoreAPI.databaseAPI();
	private MobilePhone plugin;

	public SignClickListener(MobilePhone plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) throws Exception{
		User playerName = userManager.getUser(e.getPlayer().getName());
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		ItemStack inHand = e.getPlayer().getItemInHand();
		EconomyPlayer ep = economy.getEconomyPlayer(e.getPlayer().getName());
		BankAccount acc = economy.getAccount(e.getPlayer().getName());
		double Handykosten = this.plugin.getConfig().getInt("handy.kosten");
		if (!userManager.exists(e.getPlayer().getName())) {
			p.sendMessage("Ein Fehler ist aufgetreten. Bitte kontaktiere ein Teammitglied.");
		} else {
			if (b instanceof Sign){
				boolean isMobileSign = isMobileSign(b);
				if (isMobileSign == true){
					if (inHand == null){
						String hasnumber = this.plugin.getConfig().getString("numbern." + playerName + "number");
						if (hasnumber != null){
							payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
							p.sendMessage("Deine Handynumber lautet: "  + hasnumber);
						} else {
							String number = generatenumber(playerName);
							payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
							p.sendMessage("Gl�ckwunsch. Du hast dir dein erstes Handy gekauft");
							p.sendMessage("Deine Handynumber lautet: " + number);
							int id = userManager.getUser(p.getName()).getID();
							PreparedStatement registerNumber = db.prepareUpdate("INSERT INTO mobilephone_contracts (id, number) VALUES (?, default);");
							registerNumber.setInt(1, id);
							registerNumber.setString(2, number);
							registerNumber.executeUpdate();
							Phonebook.synchronizeUsers();
						}
					}else {
						p.sendMessage("Bitte w�hle einen freien Platz in deiner Hotbar aus!");
					}
				} 
			}
		}
	}

	public boolean isMobileSign(Block b) {
		String text1 = this.plugin.getConfig().getString("sign.line1");
		String text2 = this.plugin.getConfig().getString("sign.line2");
		String text3 = this.plugin.getConfig().getString("sign.line3");
		String text4 = this.plugin.getConfig().getString("sign.line4");
		BlockState bs = b.getState();
		Sign sign = (Sign) bs;
		if (sign.getLine(0).equalsIgnoreCase(text1)) {
			if(sign.getLine(1).equalsIgnoreCase(text2)) {
				if(sign.getLine(2).equalsIgnoreCase(text3)) {
					if (sign.getLine(3).equalsIgnoreCase(text4)) {
						return true;
					} else return false;
				} else return false;
			} else return false;
		} else return false;
	}

	public static String generatenumber(User eventPlayer) throws Exception {
		User playerName = eventPlayer;
		int id = playerName.getID();
		String hasnumber = Phonebook.getNumberByUser(id);
		if (hasnumber.length() <= 3) {		
			double random = Math.random();
			double random2 = random * 100000;
			int random3 = (int) random2;
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(random3);
			String number = sb.toString();
			while (number.length() != 5){
				random = Math.random();
				random2 = random * 1000000;
				random3 = (int) random2;
				sb.append("");
				sb.append(random3);
				number = sb.toString();	
				if(MobilePhone.numbers.containsKey(number)){
					continue;
				} else {
					//number und Name des Spielers in die Datenbank eintragen
					MobilePhone.numbers.put(playerName.getID(), number);
					break;
				}
			}
			hasnumber = number;
		}
		return hasnumber;
	}

	public static void buyMobile(Player p, User eventPlayer, EdgeCraftSystem EdgeSystem) {
		System.out.println("Debug#6");
		
		ItemStack ItemStack = new ItemStack(Material.CARROT_ITEM);
		ItemMeta ItemMeta = ItemStack.getItemMeta();
		ItemMeta.setDisplayName("Handy");
		ItemStack.setItemMeta(ItemMeta);
		p.setItemInHand(ItemStack);		
	}
	
	public static boolean hasMobile(User user) {
		ItemStack inHand = user.getPlayer().getItemInHand();
		Material item = inHand.getType();
		if (!(item != Material.CARROT_ITEM)) {
			ItemMeta meta = inHand.getItemMeta();
			String name = meta.getDisplayName();
			if (name.equalsIgnoreCase("Handy")) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static void payMobile(Player p, User player, BankAccount acc, EconomyPlayer ep, double Handykosten, EdgeCraftSystem EdgeSystem){
		double balanceNow = acc.getBalance();
		String pName = player.getName();
		String mobileCharge = Handykosten + "";
		if (economy.getAccount(pName) != null) {
			if (balanceNow <= Handykosten) {
				double Bargeld = ep.getCash();
				if (Bargeld <= Handykosten) {
					p.sendMessage("Du hast leider nicht genug Geld um dir ein Handy kaufen zu k�nnen");
				} else {
					p.sendMessage("Du hast dir f�r " + mobileCharge + " ein Handy gekauft.");
					buyMobile(p, player, EdgeSystem);
					try {
						ep.updateCash(Bargeld - Handykosten);
					} catch (Exception e) {
						p.sendMessage("Ein Fehler ist aufgetretet. Notiere die aktuelle Uhrzeit und melde diesen Fall einem Teammitglied");
						e.printStackTrace();
					}
					
				}
			} else {
				double balanceAfter = balanceNow - (Handykosten);
				p.sendMessage("Du hast dir f�r " + mobileCharge + " ein Handy gekauft.");
				System.out.println("Debug#7");
				buyMobile(p, player, EdgeSystem);
				p.sendMessage("Das Geld wurde von deinem Konto abgebucht");
				try {
					acc.updateBalance(balanceAfter);
				} catch (Exception e) {
					p.sendMessage("Ein Fehler ist aufgetretet. Notiere die aktuelle Uhrzeit und melde diesen Fall einem Teammitglied");
					e.printStackTrace();
				}
			}
		} 
	}
}
