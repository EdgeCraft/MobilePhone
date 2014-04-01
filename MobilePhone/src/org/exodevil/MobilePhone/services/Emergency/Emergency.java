package org.exodevil.MobilePhone.services.Emergency;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class Emergency extends FixedSetPrompt{

	public Emergency() {
		super( "Verletzung", "Sonstiges", "Abbruch" );
	}

	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Polizeizentrale verbunden. Bitte wählen Sie ihr Problem.";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		if (s.equals("Abbruch")) {
			return Prompt.END_OF_CONVERSATION;
		} else  if (s.equals("Diebstahl")){
			context.setSessionData("reason", s);
			return new EmergencyReason();
		} else if (s.equals("Banküberfall")) {
			context.setSessionData("reason", s);
			return new EmergencyReason();
		} else {
			return Prompt.END_OF_CONVERSATION;
		}
		
	}
}
