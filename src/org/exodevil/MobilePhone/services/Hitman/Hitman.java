package org.exodevil.MobilePhone.services.Hitman;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class Hitman extends FixedSetPrompt{

	public Hitman() {
		super( "Auftrag erstellen", "Abbruch" );
	}

	public String getPromptText(ConversationContext context) {
		return "Hi. Was kann ich für Sie tun?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		if (s.equals("Abbruch")) {
			return Prompt.END_OF_CONVERSATION;
		} else  if (s.equals("Auftrag erstellen")){
			context.setSessionData("reason", s);
			return new HitmanTarget();
		}   else {
			return Prompt.END_OF_CONVERSATION;
		}		
	}
}
