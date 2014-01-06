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

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import slimevoidlib.data.Logger;
import slimevoidlib.util.xml.XMLLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {

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
		TMFCore.configuration.load();

		loadMotionSensorClient();

		TMFCore.configuration.save();
	}

	public static void CommonConfig(File configFile) {
		TMFCore.configuration = new Configuration(configFile);

		TMFCore.configuration.load();

		loadLogger();
		loadMotionSensorCommon();
		loadMiningHelmet();
		loadToolBelt();
		loadMinerals();
		loadDusts();
		loadOres();
		loadMachines();

		TMFCore.configuration.save();
	}

	private static void loadMotionSensorClient() {
		motionSensorDrawRight = TMFCore.configuration.get(	CATEGORY_MOTION_SENSOR,
															"drawonright",
															motionSensorDrawRight,
															COMMENT_MOTION_SENSOR_DRAW_RIGHT).getBoolean(motionSensorDrawRight);

		motionSensorMaxEntityDistance = 20;
		motionSensorMaxGameTicks = 40;
		motionSensorDrawRight = true;
		motionSensorPlaySounds = TMFCore.configuration.get(	CATEGORY_MOTION_SENSOR,
															"playsound",
															true).getBoolean(true);
	}

	private static void loadMotionSensorCommon() {
		TMFCore.motionSensorId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																			"motionSensor",
																			15003).getInt());
	}

	private static void loadMiningHelmet() {
		TMFCore.miningHelmetIronId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"ironMinersHelmet",
																				15000).getInt());
		TMFCore.miningHelmetGoldId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"goldMinersHelmet",
																				15001).getInt());
		TMFCore.miningHelmetDiamondId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																					"diamondMinersHelmet",
																					15002).getInt());
		TMFCore.miningHelmetLampId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"helmetLamp",
																				15004).getInt());
	}

	private static void loadToolBelt() {
		TMFCore.miningToolBeltId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"toolBelt",
																				15005).getInt());
	}

	private static void loadMinerals() {
		TMFCore.mineralAcxiumId = Integer.valueOf(TMFCore.configuration.get(Configuration.CATEGORY_ITEM,
																			"mineralAcxium",
																			15010).getInt());
		TMFCore.mineralBisogenId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"mineralBisogen",
																				15011).getInt());
		TMFCore.mineralCydrineId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"mineralCydrine",
																				15012).getInt());
	}

	private static void loadDusts() {
		TMFCore.dustAcxiumId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																			"dustAcxium",
																			15020).getInt());
		TMFCore.dustBisogenId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																			"dustBisogen",
																			15021).getInt());
		TMFCore.dustCydrineId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																			"dustCydrine",
																			15022).getInt());
		TMFCore.dustMixedId = Integer.valueOf(TMFCore.configuration.get(Configuration.CATEGORY_ITEM,
																		"dustMixed",
																		15023).getInt());
	}

	private static void loadOres() {
		TMFCore.arkiteOreId = Integer.valueOf(TMFCore.configuration.get(Configuration.CATEGORY_BLOCK,
																		"arkiteOre",
																		1025).getInt());
		TMFCore.bistiteOreId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																			"bistiteOre",
																			1026).getInt());
		TMFCore.crokereOreId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																			"crokereOre",
																			1027).getInt());
		TMFCore.derniteOreId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																			"derniteOre",
																			1028).getInt());
		TMFCore.egioclaseOreId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																			"egioclaseOre",
																			1029).getInt());
	}

	private static void loadMachines() {
		TMFCore.blockMachineBaseId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																				"blockMachine",
																				1100).getInt());

	}

	private static void loadLogger() {
		TMFCore.loggerLevel = String.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_GENERAL,
																		"loggerLevel",
																		TMFCore.loggerLevel).getString());

		LoggerTMF.getInstance(Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(TMFCore.loggerLevel);
	}

	public static void loadXMLVariables() {
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

		/* MINERALS */

		XMLLoader.addXmlVariable(	"$mineralAcxium",
									TMFCore.mineralAcxium.itemID);
		XMLLoader.addXmlVariable(	"$mineralBisogen",
									TMFCore.mineralBisogen.itemID);
		XMLLoader.addXmlVariable(	"$mineralCydrine",
									TMFCore.mineralCydrine.itemID);

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

		XMLLoader.addXmlVariable(	"$miningToolBelt",
									TMFCore.miningToolBelt.itemID);

		/* VANILLA PARTS */

		XMLLoader.addXmlVariable(	"$cobbleStone",
									Block.cobblestone.blockID);
		XMLLoader.addXmlVariable(	"$ingotIron",
									Item.ingotIron.itemID);
		XMLLoader.addXmlVariable(	"$redstoneDust",
									Item.redstone.itemID);
		XMLLoader.addXmlVariable(	"$leather",
									Item.leather.itemID);
		XMLLoader.addXmlVariable(	"$torchWood",
									Block.torchWood.blockID);
	}
}
