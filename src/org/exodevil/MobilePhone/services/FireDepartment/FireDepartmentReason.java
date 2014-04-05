package org.exodevil.MobilePhone.services.FireDepartment;

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

public class FireDepartmentReason extends StringPrompt {
	
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
		context.getForWhom().sendRawMessage(ChatColor.GREEN + "[Service] " + "Deine Meldung wurde aufgenommen. Dir wird in K�rze geholfen");
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Bitte schildern sie KURZ ihr Problem.";
	}
	
	public void updateDB(int userID, String reason, String problem) throws Exception {
		int serviceID = greatestID();
		PreparedStatement updateService = db.prepareStatement("INSERT INTO service_fire_department (serviceid, userid, reason, problem) VALUES (?, ?, ?, ?);");
		updateService.setInt(1, serviceID);
		updateService.setInt(2, userID);
		updateService.setString(3, reason);
		updateService.setString(4, problem);
		updateService.executeUpdate();
	}
	
	 public int greatestID() throws Exception {
		  List<Map<String, Object>> tempVar = db.getResults("SELECT COUNT(id) AS amount FROM service_fire_department");
		  int tempID = Integer.parseInt(String.valueOf(tempVar.get(0).get("amount")));
		  
		  if (tempID <= 0) return 1;

		  return tempID;
		 }

}
