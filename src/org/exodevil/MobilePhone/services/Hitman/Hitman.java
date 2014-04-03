package org.exodevil.MobilePhone.services.Hitman;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class Hitman extends FixedSetPrompt{

	public Hitman() {
		super( "Diebstahl", "Banküberfall", "Mord", "Sonstiges", "Abbruch" );
	}

	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Polizeizentrale verbunden. Bitte schildern sie KURZ ihr Problem.";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		if (s.equals("Abbruch")) {
			return Prompt.END_OF_CONVERSATION;
		}
		context.setSessionData("reason", s);

		//put in db
		return Prompt.END_OF_CONVERSATION;
	}
}
