package org.exodevil.MobilePhone;

import net.edgecraft.edgeconomy.EdgeConomyAPI;
import net.edgecraft.edgeconomy.economy.BankAccount;
import net.edgecraft.edgeconomy.economy.Economy;
import net.edgecraft.edgecore.user.User;

import org.exodevil.MobilePhone.sms.Memory;

public class Billing {
	private final static Economy economy = EdgeConomyAPI.economyAPI();
		

	public static void payCall(int userID, double time) throws Exception {
		double perMinute = MobilePhone.getInstance().getConfig().getDouble("handy.proMinute");
		double Costs = perMinute * time;
		payBill(userID, Costs);
	}
	
	public static double calcTime(long endCall, User u) {
		String numberP = Phonebook.getNumberByUser(u.getID());
		String chid = "MP_" + numberP;
		long beginnCall = Memory.callLength.get(chid);
		long time = endCall - beginnCall;
		int timeNew = (int) time;
		double timeMinutes = timeNew/60000;
		return timeMinutes;
	}
	
	public static void payBill(int userID, double cost) throws Exception {
		BankAccount acc = economy.getAccount(userID);
		acc.updateBalance(acc.getBalance() - cost);
	}
}
