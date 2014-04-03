package org.exodevil.MobilePhone.services.Police;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class PoliceReason extends StringPrompt {

	private final static UserManager userManger = EdgeCoreAPI.userAPI();
	final DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	@Override
	public boolean blocksForInput(ConversationContext context){
		return true;		
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		context.setSessionData("problem", s);
		String reason = (String)context.getSessionData("reason");
		String problem = (String)context.getSessionData("problem");
		Player player = (Player)context.getForWhom();
		int playerID = userManger.getUser(player.getName()).getID();
		try {
			updateDB(playerID, reason, problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.getForWhom().sendRawMessage(ChatColor.GREEN + "[Service] " + "Deine Anzeige wurde aufgenommen.");
		context.getForWhom().sendRawMessage(ChatColor.GREEN + "[Service] " + "Dir wird in K�rze geholfen, sobald eine freie Streife verf�gbar ist.");
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Polizeizentrale verbunden. Bitte schildern sie KURZ ihr Problem.";
	}

	public void updateDB(int userID, String reason, String problem) throws Exception {
		List<Map<String, Object>> results = db.getResults("SELECT MAX(serviceid) FROM service_police;");
		List<Map<String, Object>> equal = db.getResults("SELECT MAX(serviceid) FROM service_empty");
		int serviceID = 0;
		if (!results.equals(equal)) {
			for (int i = 0; i < results.size(); i++) {
				for (Map.Entry<String, Object> entry : results.get(i).entrySet()) {
					serviceID = Integer.valueOf(entry.getValue().toString());
					serviceID++;
				}
			}
		} else {
			serviceID = 1;
		}
		PreparedStatement updateService = db.prepareUpdate("INSERT INTO service_police (serviceid, userid, reason, problem) VALUES (?, ?, ?, ?);");
		updateService.setInt(1, serviceID);
		updateService.setInt(2, userID);
		updateService.setString(3, reason);
		updateService.setString(4, problem);
		updateService.executeUpdate();
	}
}
