package org.exodevil.MobilePhone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.util.TruncateWhichDBPrompt;

public class SystemCommand implements CommandExecutor {

	private ConversationFactory conversationFactory;

	public SystemCommand () {
		this.conversationFactory = new ConversationFactory(MobilePhone.getInstance())
		.withModality(true)
		.withFirstPrompt(new TruncateWhichDBPrompt())
		.withEscapeSequence("/cancel")
		.thatExcludesNonPlayersWithMessage("Go away evil console!");
	}

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label, String[] args) {
		if (!(cmds instanceof Player)) return true;
		Player player = (Player) cmds;
		if (args[1].equalsIgnoreCase("truncate")) {
			conversationFactory.buildConversation((Conversable)player).begin();
			return true;
		} else {
			player.sendMessage(args[0] + " is not a valid argument for /mp");
			return true;
		}
	}

}
