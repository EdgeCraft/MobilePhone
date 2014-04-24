package org.exodevil.MobilePhone.util;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class TruncateWhichDBPrompt extends FixedSetPrompt {

	public TruncateWhichDBPrompt() {
		super( "service_emergency", "service_firedepartment", "service_hitman", "serice_newsAgent", "service_police", "mobilephone_towers", "mobilephone_ontracts", "all");
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return "Welche Tabelle soll geleert werden?";
	}


	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		context.setSessionData("table", s);
		return new TruncatePrompt();
	}


}
