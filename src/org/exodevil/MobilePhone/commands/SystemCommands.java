package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCore;
import net.edgecraft.edgecore.command.AbstractCommand;
import net.edgecraft.edgecore.command.Level;
import net.edgecraft.edgecore.user.User;

import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.util.TruncateWhichDBPrompt;

public class SystemCommands extends AbstractCommand {
	
	private ConversationFactory conversationFactory;
	
	private static final SystemCommands instance = new SystemCommands();
	
	public static SystemCommands getInstance() {
		return instance;
	}

	public SystemCommands () {
		this.conversationFactory = new ConversationFactory(MobilePhone.getInstance())
		.withModality(true)
		.withFirstPrompt(new TruncateWhichDBPrompt())
		.withEscapeSequence("/cancel")
		.thatExcludesNonPlayersWithMessage("Go away evil console!");
	}
	
	
	@Override
	public Level getLevel() {
		return Level.ADMIN;
	}

	@Override
	public String[] getNames() {
		return new String[] { "mb" };
	}

	@Override
	public boolean runImpl(Player player, User user, String[] args) throws Exception {
		System.out.println("Debug#4");
		if (args[2].equalsIgnoreCase("truncate")) {
			System.out.println("Debug#1");
			if (args.length == 2) {
				System.out.println("Debug#2");
				conversationFactory.buildConversation((Conversable)player).begin();
				System.out.println("Debug#3");
			}
		}
		return true;
	}

	@Override
	public void sendUsageImpl(CommandSender cmds) {
		cmds.sendMessage(EdgeCore.usageColor + "/mb");
		
	}

	@Override
	public boolean validArgsRange(String[] args) {
		return args.length == 2;
	}

}
