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

import java.io.File;

import net.minecraftforge.common.Configuration;
import slimevoid.tmf.blocks.machines.EnumMachine;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import slimevoidlib.data.Logger;
import slimevoidlib.util.xml.XMLLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {

	// CONFIG
	public static Configuration	configuration;

	@SideOnly(Side.CLIENT)
	public static int			motionSensorMaxEntityDistance;
	@SideOnly(Side.CLIENT)
	public static int			motionSensorMaxGameTicks;
	@SideOnly(Side.CLIENT)
	public static boolean		motionSensorDrawRight;
	public static int			renderMachineId						= RenderingRegistry.getNextAvailableRenderId();
	public static boolean		motionSensorPlaySounds;
	public static final String	CATEGORY_MOTION_SENSOR				= "motion sensor";
	public static final String	COMMENT_MOTION_SENSOR_DRAW_RIGHT	= "Set this to false to draw the motion sensor on the left.";

	@SideOnly(Side.CLIENT)
	public static void ClientConfig() {
		configuration.load();

		loadMotionSensorClient();

		configuration.save();
	}

	public static void CommonConfig(File configFile) {
		configuration = new Configuration(configFile);

		configuration.load();

		loadDefaults();

		loadLogger();

		loadToolBelt();

		if (loadItems) {
			loadMotionSensorCommon();
			loadMiningHelmet();
		}
		if (loadOres) {
			loadMinerals();
			loadIngots();
			loadParts();
			loadDusts();
			loadOres();
		}
		if (loadMachines) {
			loadMachines();
		}

		configuration.save();
	}

	private final static String	CATEGORY_LAUNCH_OPTIONS	= "launch options";

	public static boolean		loadItems				= false;
	public static boolean		loadOres				= false;
	public static boolean		loadMachines			= false;

	private static void loadDefaults() {
		loadItems = configuration.get(	CATEGORY_LAUNCH_OPTIONS,
										"shouldLoadItems",
										loadItems).getBoolean(loadItems);
		loadOres = configuration.get(	CATEGORY_LAUNCH_OPTIONS,
										"shouldLoadOres",
										loadOres).getBoolean(loadOres);
		loadMachines = configuration.get(	CATEGORY_LAUNCH_OPTIONS,
											"shouldLoadMachines",
											loadMachines).getBoolean(loadMachines);
	}

	private static void loadMotionSensorClient() {
		motionSensorDrawRight = configuration.get(	CATEGORY_MOTION_SENSOR,
													"drawonright",
													motionSensorDrawRight,
													COMMENT_MOTION_SENSOR_DRAW_RIGHT).getBoolean(motionSensorDrawRight);

		motionSensorMaxEntityDistance = 20;
		motionSensorMaxGameTicks = 40;
		motionSensorDrawRight = true;
		motionSensorPlaySounds = configuration.get(	CATEGORY_MOTION_SENSOR,
													"playsound",
													true).getBoolean(true);
	}

	private static void loadMotionSensorCommon() {
		TMFCore.motionSensorId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"motionSensor",
																	15003).getInt());
	}

	private static void loadMiningHelmet() {
		TMFCore.miningHelmetIronId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"ironMinersHelmet",
																		15000).getInt());
		TMFCore.miningHelmetGoldId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"goldMinersHelmet",
																		15001).getInt());
		TMFCore.miningHelmetDiamondId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																			"diamondMinersHelmet",
																			15002).getInt());
		TMFCore.miningHelmetLampId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"helmetLamp",
																		15004).getInt());
	}

	private static void loadToolBelt() {
		TMFCore.miningToolBeltId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"toolBelt",
																		15005).getInt());
	}

	private static void loadMinerals() {
		TMFCore.mineralAcxiumId = Integer.valueOf(configuration.get(Configuration.CATEGORY_ITEM,
																	"mineralAcxium",
																	15010).getInt());
		TMFCore.mineralBisogenId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"mineralBisogen",
																		15011).getInt());
		TMFCore.mineralCydrineId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"mineralCydrine",
																		15012).getInt());
	}

	private static void loadIngots() {
		TMFCore.ingotAcxiumId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"ingotAcxium",
																	15015).getInt());
		TMFCore.ingotBisogenId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"ingotBisogen",
																	15016).getInt());
		TMFCore.ingotCydrineId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"ingotCydrine",
																	15017).getInt());
	}

	private static void loadParts() {
		TMFCore.partAcxiumCoreId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"partAcxiumCore",
																		15025).getInt());
		TMFCore.partAcxogenScreenId = Integer.valueOf(configuration.get(Configuration.CATEGORY_ITEM,
																		"partAcxogenScreen",
																		15026).getInt());
		TMFCore.partAlloyCasingId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"partAlloyCasing",
																		15027).getInt());
		TMFCore.partBisogenGearId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"partBisogenGear",
																		15028).getInt());
		TMFCore.partCydrineMotorId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																		"partCydrineMotor",
																		15029).getInt());
		TMFCore.partCydriumSensorId = Integer.valueOf(configuration.get(Configuration.CATEGORY_ITEM,
																		"partCydriumSensor",
																		15030).getInt());
	}

	private static void loadDusts() {
		TMFCore.dustAcxiumId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"dustAcxium",
																	15020).getInt());
		TMFCore.dustBisogenId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"dustBisogen",
																	15021).getInt());
		TMFCore.dustCydrineId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_ITEM,
																	"dustCydrine",
																	15022).getInt());
		TMFCore.dustMixedId = Integer.valueOf(configuration.get(Configuration.CATEGORY_ITEM,
																"dustMixed",
																15023).getInt());
	}

	private static void loadOres() {
		TMFCore.arkiteOreId = Integer.valueOf(configuration.get(Configuration.CATEGORY_BLOCK,
																"arkiteOre",
																1025).getInt());
		TMFCore.bistiteOreId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_BLOCK,
																	"bistiteOre",
																	1026).getInt());
		TMFCore.crokereOreId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_BLOCK,
																	"crokereOre",
																	1027).getInt());
		TMFCore.derniteOreId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_BLOCK,
																	"derniteOre",
																	1028).getInt());
		TMFCore.egioclaseOreId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_BLOCK,
																	"egioclaseOre",
																	1029).getInt());
	}

	private static void loadMachines() {
		TMFCore.blockMachineBaseId = Integer.valueOf(configuration.get(	Configuration.CATEGORY_BLOCK,
																		"blockMachine",
																		1100).getInt());

	}

	private static void loadLogger() {
		TMFCore.loggerLevel = String.valueOf(configuration.get(	Configuration.CATEGORY_GENERAL,
																"loggerLevel",
																TMFCore.loggerLevel).getString());

		LoggerTMF.getInstance(Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(TMFCore.loggerLevel);
	}

	public static void loadXMLVariables() {

		XMLLoader.addXmlVariable(	"$miningToolBelt",
									TMFCore.miningToolBelt.itemID);

		if (loadMachines) {
			/* MACHINES */

			XMLLoader.addXmlVariable(	"$machine",
										TMFCore.blockMachineBase.blockID);
			XMLLoader.addXmlVariable(	"$refinery",
										EnumMachine.REFINERY.getId());
			XMLLoader.addXmlVariable(	"$grinder",
										EnumMachine.GRINDER.getId());
			XMLLoader.addXmlVariable(	"$geoEquip",
										EnumMachine.GEOEQUIP.getId());
			XMLLoader.addXmlVariable(	"$autoMixTable",
										EnumMachine.AUTOMIXTABLE.getId());
		}

		if (loadOres) {
			/* ORES */

			XMLLoader.addXmlVariable(	"$arkiteOre",
										TMFCore.arkiteOre.blockID);
			XMLLoader.addXmlVariable(	"$bistiteOre",
										TMFCore.bistiteOre.blockID);
			XMLLoader.addXmlVariable(	"$crokereOre",
										TMFCore.crokereOre.blockID);
			XMLLoader.addXmlVariable(	"$derniteOre",
										TMFCore.derniteOre.blockID);
			XMLLoader.addXmlVariable(	"$egioclaseOre",
										TMFCore.egioclaseOre.blockID);
		}

		if (loadItems) {
			/* MINERALS */

			XMLLoader.addXmlVariable(	"$mineralAcxium",
										TMFCore.mineralAcxium.itemID);
			XMLLoader.addXmlVariable(	"$mineralBisogen",
										TMFCore.mineralBisogen.itemID);
			XMLLoader.addXmlVariable(	"$mineralCydrine",
										TMFCore.mineralCydrine.itemID);

			/* INGOTS */

			XMLLoader.addXmlVariable(	"$ingotAcxium",
										TMFCore.ingotAcxium.itemID);
			XMLLoader.addXmlVariable(	"$ingotBisogen",
										TMFCore.ingotBisogen.itemID);
			XMLLoader.addXmlVariable(	"$ingotCydrine",
										TMFCore.ingotCydrine.itemID);

			/* PARTS */

			XMLLoader.addXmlVariable(	"$partAcxiumCore",
										TMFCore.partAcxiumCore.itemID);
			XMLLoader.addXmlVariable(	"$partAlloyCasing",
										TMFCore.partAlloyCasing.itemID);
			XMLLoader.addXmlVariable(	"$partAcxogenScreen",
										TMFCore.partAcxogenScreen.itemID);
			XMLLoader.addXmlVariable(	"$partBisogenGear",
										TMFCore.partBisogenGear.itemID);
			XMLLoader.addXmlVariable(	"$partCydrineMotor",
										TMFCore.partCydrineMotor.itemID);
			XMLLoader.addXmlVariable(	"$partCydriumSensor",
										TMFCore.partCydriumSensor.itemID);

			/* DUSTS */

			XMLLoader.addXmlVariable(	"$dustAcxium",
										TMFCore.dustAcxium.itemID);
			XMLLoader.addXmlVariable(	"$dustBisogen",
										TMFCore.dustBisogen.itemID);
			XMLLoader.addXmlVariable(	"$dustCydrine",
										TMFCore.dustCydrine.itemID);
			XMLLoader.addXmlVariable(	"$dustMixed",
										TMFCore.dustMixed.itemID);

			/* TOOLS */

			XMLLoader.addXmlVariable(	"$ironMinersHelmet",
										TMFCore.miningHelmetIron.itemID);
			XMLLoader.addXmlVariable(	"$goldMinersHelmet",
										TMFCore.miningHelmetGold.itemID);
			XMLLoader.addXmlVariable(	"$diamondMinersHelmet",
										TMFCore.miningHelmetDiamond.itemID);
			XMLLoader.addXmlVariable(	"$helmetLamp",
										TMFCore.miningHelmetLamp.itemID);
			XMLLoader.addXmlVariable(	"$helmetLamp",
										TMFCore.miningHelmetLamp.itemID);

			XMLLoader.addXmlVariable(	"$motionSensor",
										TMFCore.motionSensor.itemID);
		}

		/* VANILLA PARTS */
		/* Loaded via Slimevoid Library */
	}
}
