package org.exodevil.MobilePhone.services.Police;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class Police extends FixedSetPrompt{

	public Police() {
		super( "Diebstahl", "Banküberfall", "Mord", "Sonstiges", "Abbruch" );
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
			return new PoliceReason();
		} else if (s.equals("Banküberfall")) {
			context.setSessionData("reason", s);
			return new PoliceReason();
		} else if (s.equals("Mord")) {
			context.setSessionData("reason", s);
			return new PoliceReason();
		} else if (s.equals("Sonstiges")) {
			context.setSessionData("reason", s);
			return new PoliceReason();
		} else {
			return Prompt.END_OF_CONVERSATION;
		}		
	}
}
