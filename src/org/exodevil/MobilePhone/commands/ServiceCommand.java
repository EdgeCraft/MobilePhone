package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

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
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.services.WhichServicePrompt;
import org.exodevil.MobilePhone.util.Memory;

public class ServiceCommand implements CommandExecutor, ConversationAbandonedListener {

	private ConversationFactory conversationFactory;
	private static final UserManager userManager = EdgeCoreAPI.userAPI();

	public ServiceCommand () {
		this.conversationFactory = new ConversationFactory(MobilePhone.getInstance())
		.withModality(true)
		.withPrefix(new ServicePrefix())
		.withFirstPrompt(new WhichServicePrompt())
		.withEscapeSequence("/service Abbruch")
		.thatExcludesNonPlayersWithMessage("Go away evil console!")
		.addConversationAbandonedListener(this);
	}


	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,	String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		} else {
			Player player = (Player) cmds;
			User p = userManager.getUser(player.getName());
			if (Memory.hasConnection.contains(p.getID())) {
				player.sendMessage("Du hast keinen Empfang.");
				return true;
			}
			conversationFactory.buildConversation((Conversable)cmds).begin();
		}


		return true;
	}


	@Override
	public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
		if (abandonedEvent.gracefulExit()) {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Anruf beendet");
		} else {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation cancelled by: " + abandonedEvent.getCanceller().getClass().getName());
		}
	}

	private class ServicePrefix implements ConversationPrefix {

		public String getPrefix(ConversationContext context) {
			return ChatColor.GREEN + "[Service] " + ChatColor.WHITE;
		}
	}
}
