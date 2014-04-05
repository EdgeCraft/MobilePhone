package org.exodevil.MobilePhone.services.NewsAgent;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class NewsAgent extends FixedSetPrompt{

	public NewsAgent() {
		super( "Werbeanzeige schalten", "Interview", "Sonstiges", "Abbruch" );
	}

	public String getPromptText(ConversationContext context) {
		return "Sie sind mit der Newszentrale verbunden. Bitte wählen Sie Ihr Anliegen. " + formatFixedSet();
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		if (s.equals("Abbruch")) {
			return Prompt.END_OF_CONVERSATION;
		} else  if (s.equals("Werbeanzeige schalten")){
			context.setSessionData("reason", s);
			return new NewsAgentAdvertisment();
		} else if (s.equals("Interview")) {
			context.setSessionData("reason", s);
			return new NewsAgentInterview();
		}  else if (s.equals("Sonstiges")) {
			context.setSessionData("reason", s);
			return new NewsAgentOther();
		} else {
			return Prompt.END_OF_CONVERSATION;
		}		
	}
}
