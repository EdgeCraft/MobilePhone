package org.exodevil.MobilePhone.services.Hitman;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

public class HitmanFinish extends MessagePrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		//put in db
		//return abh�ngig von kopfgeld-h�he
		context.getForWhom().sendRawMessage("Debug: Not available");
		return "Wir werden uns umgehend darum k�mmern.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return END_OF_CONVERSATION;
	}

}
