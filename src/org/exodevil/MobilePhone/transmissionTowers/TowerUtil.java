package org.exodevil.MobilePhone.transmissionTowers;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;

public class TowerUtil {

	final static DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	public static void buildTower (Player p) throws Exception {
		Location loc = p.getLocation();
		double xloc = loc.getX();
		double yloc = loc.getY();
		double zloc = loc.getZ();
		Block down = (Block) MobilePhone.getInstance().getConfig().get("towers.blocks.down");
		Block middown = (Block) MobilePhone.getInstance().getConfig().get("towers.blocks.middown");
		Block midtop = (Block) MobilePhone.getInstance().getConfig().get("towers.blocks.midtop");
		Block top = (Block) MobilePhone.getInstance().getConfig().get("towers.blocks.top");
		
		Block first = new Location(loc.getWorld(), xloc, yloc, zloc).getBlock();
		Block second = new Location(loc.getWorld(), xloc, yloc + 1, zloc).getBlock();
		Block third = new Location(loc.getWorld(), xloc, yloc + 2, zloc).getBlock();
		Block fourth = new Location(loc.getWorld(), xloc, yloc + 3, zloc).getBlock();

		first.setType(down.getType());
		p.teleport(first.getLocation().add(0, 1, 0));

		second.setType(middown.getType());
		p.teleport(second.getLocation().add(0, 1, 0));

		third.setType(midtop.getType());
		p.teleport(third.getLocation().add(0, 1, 0));

		fourth.setType(top.getType());
		p.teleport(fourth.getLocation().add(0, 1, 0));
		PreparedStatement registerTower = db.prepareStatement("INSERT INTO mobilephone_towers (id, world, x, y, z) VALUES (?, ?, ?, ?, ?)");
		int id = greatestID();
		registerTower.setInt(1, id);
		registerTower.setString(2, fourth.getWorld().getName().toString());
		registerTower.setDouble(3, fourth.getX());
		registerTower.setDouble(4, fourth.getY());
		registerTower.setDouble(5, fourth.getZ());
		registerTower.executeUpdate();
	}


	static double maxRadius = MobilePhone.getInstance().getConfig().getDouble("towers.maxdistance");

	public static boolean isTowerReachable(Player p) throws Exception {

		for (Location loc : Tower.getTowers().values()) {

			double distance = loc.distanceSquared(p.getLocation());

			if (Math.pow(distance, 2) < maxRadius) {
				return true;
			}
		}

		return false;
	}


	public static int greatestID() throws Exception {
		List<Map<String, Object>> tempVar = db.getResults("SELECT COUNT(id) AS amount FROM mobilephone_towers");
		int tempID = Integer.parseInt(String.valueOf(tempVar.get(0).get("amount")));
		if (tempID <= 0) return 1;
		return tempID;
	}
}
