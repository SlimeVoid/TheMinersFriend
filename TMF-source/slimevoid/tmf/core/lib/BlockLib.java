/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.tmf.core.lib;

import java.util.ArrayList;
import java.util.List;

import slimevoid.tmf.blocks.ores.BlockTMFOre;

public class BlockLib {

	private static String BLOCK_PREFIX = 										"tmf.";

	public static final String BLOCK_REFINERY = 		BLOCK_PREFIX + 			"refinery";
	public static final String BLOCK_GRINDER = 			BLOCK_PREFIX + 			"grinder";
	public static final String BLOCK_GEOEQUIPMENT = 	BLOCK_PREFIX + 			"geoEquip";
	public static final String BLOCK_AUTOMIXTABLE = 	BLOCK_PREFIX + 			"autoMixTable";
	
	private static final String IDLE = 											".idle";

	public static final String REFINERY_IDLE = 			BLOCK_REFINERY + 		IDLE;
	public static final String GRINDER_IDLE = 			BLOCK_GRINDER + 		IDLE;
	public static final String GEOEQUIP_IDLE = 			BLOCK_GEOEQUIPMENT + 	IDLE;
	
	private static final String ACTIVE = 										".active";

	public static final String REFINERY_ACTIVE = 		BLOCK_REFINERY + 		ACTIVE;
	public static final String GRINDER_ACTIVE = 		BLOCK_GRINDER + 		ACTIVE;
	public static final String GEOEQUIP_ACTIVE = 		BLOCK_GEOEQUIPMENT + 	ACTIVE;
	
	private static final String ORE =					BLOCK_PREFIX + 			"ore.";
	
	public static final String ORE_ARKITE = 			ORE + 					"arkite";
	public static final String ORE_BISTITE = 			ORE + 					"bistite";
	public static final String ORE_CROKERE = 			ORE + 					"crokere";
	public static final String ORE_DERNITE = 			ORE + 					"dernite";
	public static final String ORE_EGIOCLASE = 			ORE + 					"egioclase";

	//private static final String TILE = 											"tile.";

	//public static final String TILE_REFINERY = 			TILE +	 				BLOCK_REFINERY;
	//public static final String TILE_GRINDER = 			TILE + 					BLOCK_GRINDER;
	//public static final String TILE_GEOEQUIPMENT = 		TILE + 					BLOCK_GEOEQUIPMENT;
	//public static final String TILE_AUTOMIXTABLE = 		TILE + 					BLOCK_AUTOMIXTABLE;

	//private static final String CONTAINER = 									"container.";

	//public static final String CONTAINER_REFINERY = 	CONTAINER +	 			BLOCK_REFINERY;
	//public static final String CONTAINER_GRINDER = 		CONTAINER + 			BLOCK_GRINDER;
	//public static final String CONTAINER_GEOEQUIPMENT = CONTAINER + 			BLOCK_GEOEQUIPMENT;
	//public static final String CONTAINER_AUTOMIXTABLE = CONTAINER + 			BLOCK_AUTOMIXTABLE;
	
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
