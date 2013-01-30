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

import cpw.mods.fml.client.FMLClientHandler;

public class ResourceLib {
	public final static String PATH_PREFIX = "/TheMinersFriend/";
	
	public static final String BLOCK_TEXTURE_PATH = 	PATH_PREFIX + 		"terrain.png";
	
	public static final String RESOURCE_SPRITE_PATH = 	PATH_PREFIX + 		"resources/resources.png";

	public static final String ARMOR_PREFIX = PATH_PREFIX + "armor/";
	
	public static final String IRON_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfiron.png";
	public static final String GOLD_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfgold.png";
	public static final String DIAMOND_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfdiamond.png";

	public static final String GUI_PREFIX = PATH_PREFIX + "gui/";
	
	public static final String ITEM_SPRITE_PATH = 		GUI_PREFIX + 		"items.png";
	public static final String GUI_TOOLBELT = 			GUI_PREFIX + 		"toolbeltGui.png";
	public static final String GUI_GEOEQUIP = 			GUI_PREFIX + 		"geoequip.png";
	public static final String GUI_GRINDER = 			GUI_PREFIX + 		"grinder.png";
	public static final String GUI_REFINERY = 			GUI_PREFIX + 		"refinery.png";
	public static final String GUI_AUTOMIXTABLE = 		GUI_PREFIX + 		"automixtable.png";

	public static final String MACHINE_PREFIX = PATH_PREFIX + "machines/";
	
	public static final String MACHINE_TEXTURE_PATH = 	MACHINE_PREFIX + 	"machines.png";
	public static final String RECIPES_REFINERY = 		MACHINE_PREFIX + 	"refinery.json";
	public static final String RECIPES_GRINDER = 		MACHINE_PREFIX + 	"grinder.json";
	
	public static final String MODEL_PREFIX = 			MACHINE_PREFIX + 	"models/";
	public static final String HD_PREFIX =				"hd/";
	
	public static String getModelPath(boolean hasHDModel) {
		if ( hasHDModel && FMLClientHandler.instance().getClient().gameSettings.fancyGraphics && FMLClientHandler.instance().getClient().gameSettings.advancedOpengl ) {
			return MODEL_PREFIX + HD_PREFIX;
		}
		return MODEL_PREFIX;
	}
	
}