package org.exodevil.MobilePhone.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;
import org.exodevil.MobilePhone.util.Phonebook;

import net.edgecraft.edgecore.EdgeCore;
import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.command.AbstractCommand;
import net.edgecraft.edgecore.command.Level;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

public class SearchCommand extends AbstractCommand {
	
	private final UserManager userManager = EdgeCoreAPI.userAPI();
	
	private static final SearchCommand instance = new SearchCommand();
	
	public static SearchCommand getInstance() {
		return instance;
	}

	@Override
	public Level getLevel() {
		return Level.GUEST;
	}

	@Override
	public String[] getNames() {
		return new String[] { "search" };
	}

	@Override
	public void sendUsageImpl(CommandSender cmds) {
		cmds.sendMessage(EdgeCore.usageColor + "/search user");
		cmds.sendMessage(EdgeCore.usageColor + "/search number");
	}

	@Override
	public boolean validArgsRange(String[] args) {
		return args.length == 2;
	}

	@Override
	public boolean runImpl(Player player, User user, String[] args) throws Exception {
		if (args[1].equalsIgnoreCase("number")) {
			if (args.length != 3) {
				sendUsage(player);
				return true;
			}
			String number = args[2].toString();
			User searchFor = Phonebook.getUserByNumber(number);
			Player searchedFor = Bukkit.getPlayer(searchFor.getName());
			player.sendMessage(MobilePhone.name + " Die Handynummer " + number + " gehört " + searchedFor.getName());
			return true;
		}

		if (args[1].equalsIgnoreCase("user")) {
			if (args.length != 3) {
				sendUsage(player);
				return true;
			}
			String searchFor = args[2].toString();
			User search = userManager.getUser(searchFor);
			if (search != null) {
				int searchForID = search.getID();
				String number = Phonebook.getNumberByUser(searchForID);
				player.sendMessage("Der Spieler " + searchFor + " ist unter dieser Nummer erreichbar: " + number);
				return true;
			} else {
				player.sendMessage("Der angegebene Spieler konnte nicht gefunden werden.");
			}
		}
		return true;
	}
}
