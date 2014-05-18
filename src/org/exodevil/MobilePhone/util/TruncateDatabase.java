package org.exodevil.MobilePhone.util;

import java.sql.PreparedStatement;

import net.edgecraft.edgecore.EdgeCoreAPI;
import net.edgecraft.edgecore.db.DatabaseHandler;

public class TruncateDatabase {

	private static final DatabaseHandler db = EdgeCoreAPI.databaseAPI();



	public static void execute (String table) throws Exception {
			PreparedStatement truncate = db.prepareStatement("TRUNCATE TABLE " + table);
			truncate.executeUpdate();
	}
}
