package org.exodevil.MobilePhone.services.FireDepartment;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class FireDepartmentReason extends StringPrompt {
	
	private final static UserManager userManger = EdgeCoreAPI.userAPI();
	
	@Override
	public boolean blocksForInput(ConversationContext context){
		return true;		
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		context.setSessionData("problem", s);
		String service = (String)context.getSessionData("serviceType");
		String reason = (String)context.getSessionData("reason");
		String problem = (String)context.getSessionData("problem");
		Player player = (Player)context.getForWhom();
		int playerID = userManger.getUser(player.getName()).getID();
		//put in db
		player.sendMessage("Deine Meldung wurde aufgenommen. Dir wird in Kürze geholfen.");
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Feuerwehrzentrale verbunden. Bitte schildern sie KURZ ihr Problem.";
	}

}
