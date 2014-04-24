package org.exodevil.MobilePhone.util;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class TruncatePrompt extends NumericPrompt {

	private static int random3;
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		double random = Math.random();
		double random2 = random * 100000;
		random3 = (int) random2;
		return "Gib zur Bestätigung den folgenden Code ein: " + random3;
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number args) {
		if (args.equals(random3)) {
			String table = (String)context.getSessionData("table");
			try {
				TruncateDatabase.execute(table);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return END_OF_CONVERSATION;
		} else {
			return new TruncatePrompt();
		}
	}
	
}
