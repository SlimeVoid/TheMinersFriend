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

import net.minecraft.item.ItemStack;
import slimevoid.tmf.core.TMFCore;

public class BlockLib {

	private static String		BLOCK_PREFIX		= "tmf.";

	public static final int		MAX_MACHINES		= 4;
	private static String		MACHINE_PREFIX		= BLOCK_PREFIX + "machine.";
	public static final String	BLOCK_MACHINE_BASE	= MACHINE_PREFIX + "base";

	public static final String	BLOCK_REFINERY		= MACHINE_PREFIX
														+ "refinery";
	public static final String	BLOCK_GRINDER		= MACHINE_PREFIX
														+ "grinder";
	public static final String	BLOCK_GEOEQUIPMENT	= MACHINE_PREFIX
														+ "geoEquip";
	public static final String	BLOCK_AUTOMIXTABLE	= MACHINE_PREFIX
														+ "autoMixTable";

	private static final String	IDLE				= "_idle";

	public static final String	REFINERY_IDLE		= BLOCK_REFINERY + IDLE;
	public static final String	GRINDER_IDLE		= BLOCK_GRINDER + IDLE;
	public static final String	GEOEQUIP_IDLE		= BLOCK_GEOEQUIPMENT + IDLE;

	private static final String	ACTIVE				= "_active";

	public static final String	REFINERY_ACTIVE		= BLOCK_REFINERY + ACTIVE;
	public static final String	GRINDER_ACTIVE		= BLOCK_GRINDER + ACTIVE;
	public static final String	GEOEQUIP_ACTIVE		= BLOCK_GEOEQUIPMENT
														+ ACTIVE;

	public static final String	ORE_PREFIX			= BLOCK_PREFIX + "ore.";

	public static final String	ORE_ARKITE			= ORE_PREFIX + "arkite";
	public static final String	ORE_BISTITE			= ORE_PREFIX + "bistite";
	public static final String	ORE_CROKERE			= ORE_PREFIX + "crokere";
	public static final String	ORE_DERNITE			= ORE_PREFIX + "dernite";
	public static final String	ORE_EGIOCLASE		= ORE_PREFIX + "egioclase";

	public static boolean isOre(ItemStack oreItem) {
		int id = oreItem.itemID;
		if (id == TMFCore.blockBaseId) {
			int data = oreItem.getItemDamage();
			for (EnumBlocks block : EnumBlocks.values()) {
				if (block.getUnlocalizedName().startsWith(BlockLib.ORE_PREFIX)) {
					if (block.getId() == data) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
