package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.lang.LanguageHandler;
import net.edgecraft.edgecore.user.User;
import net.edgecraft.edgecore.user.UserManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.Phonebook;

public class SearchNumberCommand implements CommandExecutor {

	private final UserManager userManager = EdgeCoreAPI.userAPI();
	protected static final LanguageHandler lang = EdgeCoreAPI.languageAPI();

	@Override
	public boolean onCommand(CommandSender cmds, Command cmd, String label,
			String[] args) {
		if (!(cmds instanceof Player)) {
			cmds.sendMessage("This command is not applicable for console");
			return true;
		}

		Player player = (Player) cmds;
		if (args.length == 0) {
			player.sendMessage("Bitte beachte die Befehlssyntax /searchnumber <Spieler>");
			return true;
		} else if (args.length > 1) {
			player.sendMessage("Bitte beachte die Befehlssyntax /searchnumber <Spieler>");
			return true;
		}

		String searchFor = args.toString();
		User search = userManager.getUser(searchFor);
		if (search != null) {
			int searchForID = search.getID();
			String number = Phonebook.getNumberByUser(searchForID);
			player.sendMessage("Der Spieler " + searchFor + " ist unter dieser Nummer erreichbar: " + number);
			return true;
		} else {
			player.sendMessage("Der angegebene Spieler konnte nicht gefunden werden.");
		}

		return true;
	}

}
