package org.exodevil.Conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class SecondPrompt extends StringPrompt {
	
	@Override
	public boolean blocksForInput(ConversationContext context){
		context.getForWhom().sendRawMessage("Dies ist ein Test");
		return true;		
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		Conversations.getInstance().getServer().broadcastMessage(context.getForWhom().toString());
		context.setSessionData("Second", s);
		return new ThirdPrompt(context.getPlugin());
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		return "Sie sind mit Polizeizentrale verbunden. Bitte schildern sie KURZ ihr Problem.";
	}



}
