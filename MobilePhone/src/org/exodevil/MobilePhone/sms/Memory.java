package org.exodevil.MobilePhone.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Memory {
	//Call-Memory
	public static HashMap<Integer, String> SMS = new HashMap<Integer, String> ();
	public static HashMap<Integer, List<String>> missedCALL = new HashMap<Integer, List<String> > ();
	public static HashMap<Integer, Boolean> inCALL = new HashMap<Integer, Boolean> ();
	public static HashMap<String, Long> callLength = new HashMap<String,Long>();
	public static HashMap<Integer, Integer> tempCALL = new HashMap<Integer, Integer> ();
	public static List<Integer> beginnCALL = new ArrayList<>();
	
	
	//Service-Memory
	public static HashMap<Integer, String> Police = new HashMap<Integer, String> ();
}
