package slimevoid.tmf.core.lib;

import java.util.ArrayList;
import java.util.List;

import slimevoid.tmf.blocks.ores.BlockTMFOre;

public class BlockLib {
	
	private static List<BlockTMFOre> ores;
	
	public static void init() {
		ores = new ArrayList<BlockTMFOre>();
	}
	
	public static void registerTMFOre(BlockTMFOre ore) {
		if (!ores.contains(ore)) {
			ores.add(ore);
		}
	}
	
	public static int getOreCount() {
		return ores.size();
	}
	
	public static List<BlockTMFOre> getRegisteredOres() {
		return ores;
	}
}
