package org.exodevil.MobilePhone.transmissionTowers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Tower {
	private Location location;
	private static Map<Integer, Location> towers = new LinkedHashMap<>();
	final static DatabaseHandler db = EdgeCoreAPI.databaseAPI();

	protected Tower() {}

	public Location getLocation() {
		return location;
	}

	public static Map<Integer, Location> getTowers() throws Exception {
		List<Map<String, Object>> results = db.getResults("SELECT * FROM mobilephone_towers");
		for (int i = 0; i < results.size(); i++) {
			World world = null;
			double x = 0;
			double y = 0;
			double z = 0;
			for (Map.Entry<String, Object> entry : results.get(i).entrySet()) {
				if (entry.getKey().equals("world")) {
					world = Bukkit.getWorld(entry.getKey().toString());
				} else if (entry.getKey().equals("x")) {
					x = Double.valueOf(entry.getValue().toString());
				} else if (entry.getKey().equals("y")) {
					y = Double.valueOf(entry.getValue().toString());
				} else if (entry.getKey().equals("z")) {
					z = Double.valueOf(entry.getValue().toString());
				}
			}
			Location loc = new Location(world, x, y, z);
			int id = Util.greatestID();
			towers.put(id, loc);
		}
		return towers;
	}
	
	
}


