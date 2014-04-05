package org.exodevil.MobilePhone.services.NewsAgent;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class NewsAgentInterviewTopic extends StringPrompt {

	@Override
	public Prompt acceptInput(ConversationContext context, String args) {
		context.setSessionData("topic", args);
		return END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Um welches Thema soll es gehen?";
	}

}
