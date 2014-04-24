package org.exodevil.MobilePhone.commands;

import net.edgecraft.edgecore.EdgeCore;
import net.edgecraft.edgecore.command.AbstractCommand;
import net.edgecraft.edgecore.command.Level;
import net.edgecraft.edgecore.user.User;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.transmissionTowers.TowerUtil;

public class TestCommand extends AbstractCommand {
	
	private static final TestCommand instance = new TestCommand();
	
	private TestCommand() { super(); }
	
	public static TestCommand getInstance() {
		return instance;
	}
	
	@Override
	public Level getLevel() {
		return Level.ADMIN;
	}

	@Override
	public String[] getNames() {
		return new String[] { "test" };
	}

	@Override
	public boolean runImpl(Player player, User user, String[] args) throws Exception {
		try {
			TowerUtil.buildTower(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void sendUsageImpl(CommandSender cmds) {
		cmds.sendMessage(EdgeCore.usageColor + "/test");
	}

	@Override
	public boolean validArgsRange(String[] args) {
		return args.length == 1;
	}

}
