package org.exodevil.MobilePhone.services.Hitman;

import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class HitmanTarget extends StringPrompt {

	final static DatabaseHandler db = EdgeCoreAPI.databaseAPI();
	private final static UserManager userManager = EdgeCoreAPI.userAPI();
	
	@Override
	public Prompt acceptInput(ConversationContext context, String args) {
		Map<Integer, User> users = userManager.getUsers();
		if (users.containsValue(args.toString())) {
			User target = userManager.getUser(args.toString());
			context.setSessionData("who", target);
			return new HitmanBounty();
		} else {
			context.getForWhom().sendRawMessage("Dieser Name ist mir in dieser Stadt nicht bekannt.");
			return new HitmanTarget();
		}
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Wer ist das Ziel?";
	}


}
