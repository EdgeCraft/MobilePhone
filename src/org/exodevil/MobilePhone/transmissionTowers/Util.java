package org.exodevil.MobilePhone.transmissionTowers;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;

public class Util {

	final static DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	public static void buildTower (Player p) throws Exception {
		Location loc = p.getLocation();
		double xloc = loc.getX();
		double yloc = loc.getY();
		double zloc = loc.getZ();
		/*Block first = (Block) MobilePhone.getInstance().getConfig().get("");
		Block second = (Block) MobilePhone.getInstance().getConfig().get("");
		Block third = (Block) MobilePhone.getInstance().getConfig().get("");
		Block top = (Block) MobilePhone.getInstance().getConfig().get("");
		 */
		Block first = new Location(loc.getWorld(), xloc, yloc, zloc).getBlock();
		Block second = new Location(loc.getWorld(), xloc, yloc + 1, zloc).getBlock();
		Block third = new Location(loc.getWorld(), xloc, yloc + 2, zloc).getBlock();
		Block top = new Location(loc.getWorld(), xloc, yloc + 3, zloc).getBlock();

		first.setType(Material.IRON_BLOCK);
		p.teleport(first.getLocation().add(0, 1, 0));

		second.setType(Material.IRON_BLOCK);
		p.teleport(second.getLocation().add(0, 1, 0));

		third.setType(Material.IRON_BLOCK);
		p.teleport(third.getLocation().add(0, 1, 0));

		top.setType(Material.BEACON);
		p.teleport(top.getLocation().add(0, 1, 0));
		PreparedStatement registerTower = db.prepareStatement("INSERT INTO mobilephone_towers (id, world, x, y, z) VALUES (?, ?, ?, ?, ?)");
		int id = greatestID();
		registerTower.setInt(1, id);
		registerTower.setString(2, top.getWorld().getName().toString());
		registerTower.setDouble(3, top.getX());
		registerTower.setDouble(4, top.getY());
		registerTower.setDouble(5, top.getZ());
		registerTower.executeUpdate();

	}


	double maxRadius = 0; // Dein verkackter maximaler radius zu nem turm :D

	public boolean isTowerReachable(Player p) throws Exception {

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
