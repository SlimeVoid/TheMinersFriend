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

	private static final String			ITEM_PREFIX				= "tmf.";

	private static final String			MINING_HELMET			= ITEM_PREFIX
																	+ "armor.helm.";

	public static final String			MINING_HELMET_LAMP		= MINING_HELMET
																	+ "lamp";
	public static final String			MINING_HELMET_IRON		= MINING_HELMET
																	+ "iron";
	public static final String			MINING_HELMET_GOLD		= MINING_HELMET
																	+ "gold";
	public static final String			MINING_HELMET_DIAMOND	= MINING_HELMET
																	+ "diamond";

	private static final String			DUST_PREFIX				= ITEM_PREFIX
																	+ "dust.";

	public static final String			DUST_ACXIUM				= DUST_PREFIX
																	+ "acxium";
	public static final String			DUST_BISOGEN			= DUST_PREFIX
																	+ "bisogen";
	public static final String			DUST_CYDRINE			= DUST_PREFIX
																	+ "cydrine";
	public static final String			DUST_MIXED				= DUST_PREFIX
																	+ "mixed";

	private static final String			MINERAL_PREFIX			= ITEM_PREFIX
																	+ "mineral.";

	public static final String			MINERAL_ACXIUM			= MINERAL_PREFIX
																	+ "acxium";
	public static final String			MINERAL_BISOGEN			= MINERAL_PREFIX
																	+ "bisogen";
	public static final String			MINERAL_CYDRINE			= MINERAL_PREFIX
																	+ "cydrine";

	private static final String			INGOT_PREFIX			= ITEM_PREFIX
																	+ "ingot.";

	public static final String			INGOT_ACXIUM			= INGOT_PREFIX
																	+ "acxium";
	public static final String			INGOT_BISOGEN			= INGOT_PREFIX
																	+ "bisogen";
	public static final String			INGOT_CYDRINE			= INGOT_PREFIX
																	+ "cydrine";

	private static final String			TOOL_PREFIX				= ITEM_PREFIX
																	+ "tool.";

	private static final String			SENSOR_PREFIX			= TOOL_PREFIX
																	+ "sensor.";

	public static final String			MOTION_SENSOR			= SENSOR_PREFIX
																	+ "motion";
	public static final String			HEAT_SENSOR				= SENSOR_PREFIX
																	+ "heat";

	public static final String			MINING_TOOLBELT			= TOOL_PREFIX
																	+ "belt";

	public static final String			MINING_MODE				= ITEM_PREFIX
																	+ "miningMode";

	private static final String			PART_PREFIX				= ITEM_PREFIX
																	+ "part.";

	public static final String			PART_ALLOY_CASING		= PART_PREFIX
																	+ "alloy_casing";
	public static final String			PART_ACXIUM_CORE		= PART_PREFIX
																	+ "acxium_core";
	public static final String			PART_ACXOGEN_SCREEN		= PART_PREFIX
																	+ "acxogen_screen";
	public static final String			PART_BISOGEN_GEAR		= PART_PREFIX
																	+ "bisogen_gear";
	public static final String			PART_CYDRINE_MOTOR		= PART_PREFIX
																	+ "cydrine_motor";
	public static final String			PART_CYDRIUM_SENSOR		= PART_PREFIX
																	+ "cydrium_sensor";

	/**
	 * Infi Tools from Tinkers Construct
	 */
	public static final String			INFI_TOOL				= "item.InfiTool";

	public static final CharSequence	INFI_TOOL_PICKAXE		= "Pickaxe";
	public static final CharSequence	INFI_TOOL_HAMMER		= "Hammer";
	public static final CharSequence	INFI_TOOL_SHOVEL		= "Shovel";
	public static final CharSequence	INFI_TOOL_MATTOCK		= "Mattock";
	public static final CharSequence	INFI_TOOL_EXCAVATOR		= "Excavator";
}
