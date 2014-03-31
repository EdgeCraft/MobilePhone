package org.exodevil.Conversations;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.entity.Player;

public class ConCommand implements CommandExecutor, ConversationAbandonedListener {

	private ConversationFactory conversationFactory;

	public ConCommand() {
		this.conversationFactory = new ConversationFactory(Conversations.getInstance())
		.withModality(true)
		.withFirstPrompt(new FirstPrompt())
		.withPrefix(new ServicePrefix())
		.withEscapeSequence("/quit")
		.thatExcludesNonPlayersWithMessage("Go away evil console!")
		.addConversationAbandonedListener(this);
	}

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,
			String[] args) {

		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			conversationFactory.buildConversation((Conversable)cmds).begin();
		}


		return true;
	}

	@Override
	public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
		if (abandonedEvent.gracefulExit()) {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation exited gracefully.");
		} else {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" + abandonedEvent.getCanceller().getClass().getName());
		}
	}


	private class ServicePrefix implements ConversationPrefix {

		public String getPrefix(ConversationContext context) {
			String First = (String)context.getSessionData("First");
			String Second = (String)context.getSessionData("Second");
			Player p = (Player)context.getSessionData("Third");
			String Third = p.getName().toString();

			if (First != null && Second == null && Third == null) {
				return ChatColor.GREEN + "Test: " + First + ": " + ChatColor.WHITE;
			}
			if (First != null && Second != null && Third == null) {
				return ChatColor.GREEN + "Test: " + First + " " + Second + ": " + ChatColor.WHITE;
			}
			if (First != null && Second != null && Third != null) {
				return ChatColor.GREEN + "Test: " + First + " " + Second + " /n " + Third + ": " + ChatColor.WHITE;
			}
			return ChatColor.GREEN + "Test: " + First + " " + Second + " /n " + Third + ": " + ChatColor.WHITE;
		}
	}



}
