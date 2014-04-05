package org.exodevil.MobilePhone.services.NewsAgent;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class NewsAgentInterview extends StringPrompt {
	
	private final static UserManager userManger = EdgeCoreAPI.userAPI();

	@Override
	public Prompt acceptInput(ConversationContext context, String args) {
		User user = UserManager.getInstance().getUser(args);
		//if user = reporter 
		//context.setSessionData("who", reporter);
		return new NewsAgentInterviewTopic();
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Welcher Reporter soll das Gespräch führen?";
	}
}
