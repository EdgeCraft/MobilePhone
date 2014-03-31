package org.exodevil.Conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.PlayerNamePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ThirdPrompt extends PlayerNamePrompt  {

	public ThirdPrompt(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Wer?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Player player) {
		context.setSessionData("Third", player);
		return new FourthPrompt();
	}
	
    @Override
    protected String getFailedValidationText(ConversationContext context, String invalidInput) {
        return invalidInput + " ist nicht da!";
    }

}