package org.exodevil.MobilePhone.services.Hitman;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class HitmanBounty extends NumericPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		return "Wie viel ist Ihnen die Sache wert?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number number) {
		context.setSessionData("bounty", number);
		return null;
	}

}
