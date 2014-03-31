package org.exodevil.MobilePhone.services.Police;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class PoliceReason extends StringPrompt {
	
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
		player.sendMessage("Deine Anzeige wurde aufgenommen. Dir wird in K�rze geholfen, sobald eine freie Streife verf�gbar ist.");
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Polizeizentrale verbunden. Bitte schildern sie KURZ ihr Problem.";
	}

}
