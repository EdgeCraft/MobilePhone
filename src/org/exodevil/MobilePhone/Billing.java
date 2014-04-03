package org.exodevil.MobilePhone;

import net.edgecraft.edgeconomy.EdgeConomyAPI;
import net.edgecraft.edgeconomy.economy.BankAccount;
import net.edgecraft.edgeconomy.economy.Economy;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.sms.Memory;

public class Billing {
	private final static Economy economy = EdgeConomyAPI.economyAPI();
	private final static UserManager userManager = EdgeCoreAPI.userAPI();
		

	public static void payCall(int userID, double time) throws Exception {
		double costs = calcCosts(userID, time);
		payBill(userID, costs, time);
	}
	
	public static double calcCosts(int userID, double time) {
		double perMinute = MobilePhone.getInstance().getConfig().getDouble("handy.proMinute");
		double costs = perMinute * time;
		double costsRounded = Math.round(costs * 100.0) /100.0;
		return costsRounded;
	}
	
	public static double calcTime(long endCall, User u) {
		String numberP = Phonebook.getNumberByUser(u.getID());
		String chid = "MP_" + numberP;
		long beginnCall = Memory.callLength.get(chid);
		long time = endCall - beginnCall;
		double timeNew = (double) time;
		double timeMinutes = timeNew/60000;
		double timeMinutesRounded = Math.round(timeMinutes*100.0) / 100.0;
		return timeMinutesRounded;
	}
	
	public static void payBill(int userID, double cost, double time) throws Exception {
		BankAccount acc = economy.getAccount(userID);
		acc.updateBalance(acc.getBalance() - cost);
		User u = userManager.getUser(userID);
		Player p = Bukkit.getPlayer(u.getName());
		p.sendMessage("Dir wurden " + cost + " f�r " + time + " Minuten Gespr�chszeit berechnet.");
	}
}
