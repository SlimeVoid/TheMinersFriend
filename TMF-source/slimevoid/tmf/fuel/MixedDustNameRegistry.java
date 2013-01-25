package slimevoid.tmf.fuel;

import java.util.HashMap;
import java.util.Map;

import slimevoid.tmf.minerals.items.ItemMineralMixedDust;

public class MixedDustNameRegistry {
	private static Map<String,String> nameMapping = new HashMap<String,String>();
	
	public static void addName(int meta, String name) {
		addName(
				ItemMineralMixedDust.getBurnTimeLevel(meta),
				ItemMineralMixedDust.getBurnSpeedLevel(meta),
				ItemMineralMixedDust.getBurnWidthLevel(meta),
				name
		);
	}
	public static void addName(int aL, int bL, int cL, String name) {
		synchronized(nameMapping) {
			nameMapping.put(getKey(aL,bL,cL), name);
		}
	}
	
	public static String getName(int meta) {
		return getName(
				ItemMineralMixedDust.getBurnTimeLevel(meta),
				ItemMineralMixedDust.getBurnSpeedLevel(meta),
				ItemMineralMixedDust.getBurnWidthLevel(meta)
		);
	}
	public static String getName(int aL, int bL, int cL) {
		String out = null;
		synchronized(nameMapping) {
			out = nameMapping.get(getKey(aL,bL,cL));
		}
		if ( out != null )
			return out;
		
		return getDefaultName(aL,bL,cL);
	}
	
	private static String getKey(int aL, int bL, int cL) {
		return aL+","+bL+","+cL;
	}
	
	private static String getDefaultName(int aL, int bL, int cL) {
		return getKey(aL,bL,cL);
	}
}
