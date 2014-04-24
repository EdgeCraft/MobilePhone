package org.exodevil.MobilePhone.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Memory {
	//SMS-Memory
	public static HashMap<Integer, String> SMS = new HashMap<Integer, String> ();
	
	//Call-Memory
	public static HashMap<Integer, List<String>> missedCALL = new HashMap<Integer, List<String> > ();
	public static HashMap<Integer, Boolean> inCALL = new HashMap<Integer, Boolean> ();
	public static HashMap<String, Long> callLength = new HashMap<String,Long>();
	public static HashMap<String, Integer> isRec = new HashMap<String, Integer> ();
	public static HashMap<String, Integer> isP = new HashMap<String, Integer> ();
	public static HashMap<Integer, Integer> receiver = new HashMap<Integer, Integer> ();
	public static HashMap<Integer, Integer> caller = new HashMap<Integer, Integer> ();
	public static HashMap<Integer, Boolean> beginnCALL = new HashMap<Integer, Boolean> ();
	//Service-Memory
	public static HashMap<Integer, String> Police = new HashMap<Integer, String> ();
	
	
	//Connection-Memory
	public static List<Integer> hasConnection = new ArrayList<>();
}
