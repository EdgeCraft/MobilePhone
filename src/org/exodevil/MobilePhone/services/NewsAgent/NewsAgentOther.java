package org.exodevil.MobilePhone.services.NewsAgent;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class NewsAgentOther extends StringPrompt {

	@Override
	public Prompt acceptInput(ConversationContext context, String args) {
		String reason = (String)context.getSessionData("reason");
		Player player = (Player)context.getForWhom();
		String ad = args;
		//do something with input
		context.getForWhom().sendRawMessage("Debug: Not available");
		context.getForWhom().sendRawMessage("Bitte haben etwas Geduld. Ihr Anliegen wird gleich von einem Angestellten bearbeitet.");
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Bitte schildern sie KURZ ihr Anliegen.";
	}

}
