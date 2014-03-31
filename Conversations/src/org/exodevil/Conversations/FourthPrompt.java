package org.exodevil.Conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class FourthPrompt extends MessagePrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		Player p = (Player)context.getSessionData("Third");
		String player = p.getName();
		String First =  (String)context.getSessionData("First");
		String Second = (String)context.getSessionData("Second");
		return "Hier ein paar Infos " + player + " " + First + " " + Second;
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return Prompt.END_OF_CONVERSATION;
	}

}
