package slimevoid.tmf.core.lib;

public class NamingLib {

	public static final String MINING_TOOL_BELT = "Mining Tool Belt";

	public static final String MOTION_SENSOR = "Motion Sensor";
	public static final String MOTION_SENSOR_SETTINGS = "Sensor Settings";
	
	private static final String ARKITE = "Arkite";
	private static final String BISTITE = "Bistite";
	private static final String CROKERE = "Crokere";
	private static final String DERNITE = "Dernite";
	private static final String EGIOCLASE = "Egioclase";

	private static final String ORE =  "Ore";
	public static final String ORE_ARKITE = makeName(ARKITE, ORE);
	public static final String ORE_BISTITE = makeName(BISTITE, ORE);
	public static final String ORE_CROKERE = makeName(CROKERE, ORE);
	public static final String ORE_DERNITE = makeName(DERNITE, ORE);
	public static final String ORE_EGIOCLASE = makeName(EGIOCLASE, ORE);
	
	public static final String REFINERY = "Refinery";
	public static final String GRINDER = "Grinder";
	public static final String GEOEQUIP = "Geological Equipment";
	
	private static String makeName(String str1, String str2) {
		return str1 + " " + str2;
	}
}
