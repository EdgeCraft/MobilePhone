package org.exodevil.MobilePhone.util;

import java.util.List;
import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.exodevil.MobilePhone.MobilePhone;


public class Phonebook {

	private final static UserManager userManager = EdgeCoreAPI.userAPI();
	final static DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	public static void synchronizeUsers() throws Exception {
		for (User user : userManager.getUsers().values()) {
			if (user != null) loadNumber(user.getID());
		}
	}

	public static void loadNumber (int id) throws Exception {
		List<Map<String, Object>> results = db.getResults("SELECT * FROM mobilephone_contracts WHERE id = '" + id + "';");
		for (int i = 0; i < results.size(); i++) {
			int userID = 0;
			String number = "";
			for (Map.Entry<String, Object> entry : results.get(i).entrySet()) {
				if (entry.getKey().equals("id")) {
					userID = Integer.valueOf(entry.getValue().toString());
				} else if(entry.getKey().equals("number")) {
					number = entry.getValue().toString();
				}
			}
			MobilePhone.numbers.put(userID, number);
		}
	}

	public static User getUserByNumber(String number) {
		int id = 0;

		for (Map.Entry<Integer, String> entry : MobilePhone.numbers.entrySet()) {
			String searchFor = entry.getValue();

			if (searchFor.equals(number)) {
				id = entry.getKey();
				break;
			}
		}
		return userManager.getUser(id);
	}

	public static String getNumberByUser(int id) {
		return MobilePhone.numbers.containsKey(id) ? MobilePhone.numbers.get(id).toString() : "";
	}	
	
	public static boolean UserIsInCall(int id) {
		return Memory.inCALL.containsKey(id) ? Memory.inCALL.get(id) : false;
	}
	
	public static String Police = "1";
	public String FireDepartment = "2";
	public String Emergency = "3";
	public String Hitman = "4";
	
	public static boolean isServiceNumber(String number) {
		switch (number){
		case "110":
			//Polizei
			return true;
		case "112":
			//Feuerwehr
			return true;
		case "113":
			//Arzt
			return true;
		case "666":
			//Auftragskiller
			return true;
		case "533":
			//Newsreporter
			return true;
		}
		return false;
	}
}
