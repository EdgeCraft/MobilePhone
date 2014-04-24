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
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.util.Phonebook;

public class BuyMobileTask implements Listener {

	final UserManager userManager = EdgeCoreAPI.userAPI();
	final static Economy economy = EdgeConomyAPI.economyAPI();
	final EdgeCraftSystem EdgeSystem = EdgeCoreAPI.systemAPI();
	final DatabaseHandler db = EdgeCoreAPI.databaseAPI();
	private MobilePhone plugin;

	public BuyMobileTask(MobilePhone plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) throws Exception{
		if (!(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		User playerName = userManager.getUser(e.getPlayer().getName());
		Player p = e.getPlayer();
		Material m = e.getClickedBlock().getType();
		ItemStack inHand = e.getPlayer().getItemInHand();
		EconomyPlayer ep = economy.getEconomyPlayer(e.getPlayer().getName());
		BankAccount acc = economy.getAccount(e.getPlayer().getName());
		double Handykosten = this.plugin.getConfig().getInt("handy.kosten");
		int id = userManager.getUser(p.getName()).getID();
		String hasnumber = Phonebook.getNumberByUser(id);
		if (!userManager.exists(e.getPlayer().getName())) {
			p.sendMessage("Ein Fehler ist aufgetreten. Bitte kontaktiere ein Teammitglied.");
			return;
		} else {
			if (m == Material.WALL_SIGN || m == Material.SIGN_POST){
				Sign sign = (Sign) e.getClickedBlock().getState();
				boolean isMobileSign = isMobileSign(sign);
				if (isMobileSign == true){
					if (hasnumber.length() >= 3 ){
						payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
						p.sendMessage("Deine Handynumber lautet: "  + hasnumber);
					} else {
						if (inHand == null || inHand.getType() == Material.AIR || inHand.getAmount() == 0){
							String number = generatenumber(playerName);
							payMobile(p, playerName, acc, ep, Handykosten, EdgeSystem);
							p.sendMessage("Gl�ckwunsch. Du hast dir dein erstes Handy gekauft");
							p.sendMessage("Deine Handynumber lautet: " + number);
							PreparedStatement registerNumber = db.prepareStatement("INSERT INTO mobilephone_contracts (id, number) VALUES (?, ?);");
							registerNumber.setInt(1, id);
							registerNumber.setString(2, number);
							registerNumber.executeUpdate();
							Phonebook.synchronizeUsers();
						} else {
							p.sendMessage("Bitte w�hle einen freien Platz in deiner Hotbar aus!");
						}
					}
				}
			}
		}
	}

	public boolean isMobileSign(Sign sign) {
		String text1 = this.plugin.getConfig().getString("sign.line1");
		String text2 = this.plugin.getConfig().getString("sign.line2");
		String text3 = this.plugin.getConfig().getString("sign.line3");
		String text4 = this.plugin.getConfig().getString("sign.line4");
		if (sign.getLine(0).equalsIgnoreCase(text1) == true) {
			if(sign.getLine(1).equalsIgnoreCase(text2) == true) {
				if(sign.getLine(2).equalsIgnoreCase(text3) == true) {
					if (sign.getLine(3).equalsIgnoreCase(text4) == true) {
						return true;
					} else
						return false;
				} else
					return false;
			} else
				return false;
		} else
			return false;
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
					MobilePhone.numbers.put(playerName.getID(), number);
					break;
				}
			}
			hasnumber = number;
		}
		return hasnumber;
	}

	public static void buyMobile(Player p, User eventPlayer, EdgeCraftSystem EdgeSystem) {
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
