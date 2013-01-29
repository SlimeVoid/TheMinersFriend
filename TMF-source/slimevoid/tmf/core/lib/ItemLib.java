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

public class ItemLib {

	private static final String ITEM_PREFIX = 										"tmf.";

	private static final String MINING_HELMET = 			ITEM_PREFIX + 			"miningHelmet.";

	public static final String MINING_HELMET_LAMP = 		MINING_HELMET + 		"lamp";
	public static final String MINING_HELMET_IRON = 		MINING_HELMET + 		"iron";
	public static final String MINING_HELMET_GOLD = 		MINING_HELMET + 		"gold";
	public static final String MINING_HELMET_DIAMOND = 		MINING_HELMET + 		"diamond";
	
	private static final String SENSOR_PREFIX = 			ITEM_PREFIX + 			"sensor.";
	
	public static final String MOTION_SENSOR = 				SENSOR_PREFIX + 		"motion";
	public static final String HEAT_SENSOR = 				SENSOR_PREFIX + 		"heat";

	private static final String DUST_PREFIX = 				ITEM_PREFIX + 			"dust.";
	
	public static final String DUST_AXCIUM = 				DUST_PREFIX + 			"axcium";
	public static final String DUST_BISOGEN =				DUST_PREFIX + 			"bisogen";
	public static final String DUST_CYDRINE = 				DUST_PREFIX + 			"cydrine";
	public static final String DUST_MIXED = 				DUST_PREFIX + 			"mixed";
	
	private static final String MINERAL_PREFIX = 			ITEM_PREFIX + 			"mineral.";
	
	public static final String MINERAL_AXCIUM = 			MINERAL_PREFIX + 		"axcium";
	public static final String MINERAL_BISOGEN = 			MINERAL_PREFIX + 		"bisogen";
	public static final String MINERAL_CYDRINE = 			MINERAL_PREFIX + 		"cydrine";

	public static final String MINING_TOOLBELT = 			ITEM_PREFIX + 			"miningToolBelt";

	public static final String MINING_MODE = 				ITEM_PREFIX +			"miningMode";
}
