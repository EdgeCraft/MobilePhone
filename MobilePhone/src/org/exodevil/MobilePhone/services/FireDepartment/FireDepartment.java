package org.exodevil.MobilePhone.services.FireDepartment;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.exodevil.MobilePhone.services.Police.PoliceReason;

public class FireDepartment extends FixedSetPrompt{

	public FireDepartment() {
		super( "Brand", "Sonstiges", "Abbruch" );
	}

	public String getPromptText(ConversationContext context) {
		return "Sie sind mit Feuerwehrzentrale verbunden. Bitte wählen Sie ihr Problem.";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String s) {
		if (s.equals("Abbruch")) {
			return Prompt.END_OF_CONVERSATION;
		} else  if (s.equals("Brand")){
			context.setSessionData("reason", s);
			return new FireDepartmentReason();
		} else if (s.equals("Sonstiges")) {
			context.setSessionData("reason", s);
			return new FireDepartmentReason();
		}
		return Prompt.END_OF_CONVERSATION;
	}
}
