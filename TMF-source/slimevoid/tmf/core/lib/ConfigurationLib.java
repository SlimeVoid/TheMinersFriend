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
import slimevoidlib.data.Logger;
import slimevoidlib.util.xml.XMLLoader;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {

	public static final int	ITEMID_OFFSET	= 256;
	@SideOnly(Side.CLIENT)
	public static int		motionSensorMaxEntityDistance;
	@SideOnly(Side.CLIENT)
	public static int		motionSensorMaxGameTicks;
	@SideOnly(Side.CLIENT)
	public static boolean	motionSensorDrawRight;
	public static int		renderMachineId	= RenderingRegistry.getNextAvailableRenderId();	;

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
		motionSensorDrawRight = Boolean.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_GENERAL,
																			"motionSensorDrawRight",
																			motionSensorDrawRight).getBoolean(motionSensorDrawRight));

		motionSensorMaxEntityDistance = 20;
		motionSensorMaxGameTicks = 40;
		motionSensorDrawRight = true;
	}

	private static void loadMotionSensorCommon() {
		TMFCore.motionSensorId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																			"motionSensor",
																			15003).getInt());

		XMLLoader.addXmlVariable(	"$motionSensor",
									TMFCore.motionSensorId);
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

		XMLLoader.addXmlVariable(	"$ironMinersHelmet",
									TMFCore.miningHelmetIronId);
		XMLLoader.addXmlVariable(	"$goldMinersHelmet",
									TMFCore.miningHelmetGoldId);
		XMLLoader.addXmlVariable(	"$diamondMinersHelmet",
									TMFCore.miningHelmetDiamondId);
		XMLLoader.addXmlVariable(	"$helmetLamp",
									TMFCore.miningHelmetLampId);
	}

	private static void loadToolBelt() {
		TMFCore.miningToolBeltId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_ITEM,
																				"toolBelt",
																				15005).getInt());

		XMLLoader.addXmlVariable(	"$toolBelt",
									TMFCore.miningToolBeltId);
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

		XMLLoader.addXmlVariable(	"$mineralAcxium",
									TMFCore.mineralAcxiumId);
		XMLLoader.addXmlVariable(	"$mineralBisogen",
									TMFCore.mineralBisogenId);
		XMLLoader.addXmlVariable(	"$mineralCydrine",
									TMFCore.mineralCydrineId);
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

		XMLLoader.addXmlVariable(	"$dustAcxium",
									TMFCore.dustAcxiumId);
		XMLLoader.addXmlVariable(	"$dustBisogen",
									TMFCore.dustBisogenId);
		XMLLoader.addXmlVariable(	"$dustCydrine",
									TMFCore.dustCydrineId);
		XMLLoader.addXmlVariable(	"$dustMixed",
									TMFCore.dustMixedId);
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

		XMLLoader.addXmlVariable(	"$arkiteOre",
									TMFCore.arkiteOreId);
		XMLLoader.addXmlVariable(	"$bistiteOre",
									TMFCore.bistiteOreId);
		XMLLoader.addXmlVariable(	"$crokereOre",
									TMFCore.crokereOreId);
		XMLLoader.addXmlVariable(	"$derniteOre",
									TMFCore.derniteOreId);
		XMLLoader.addXmlVariable(	"$egioclaseOre",
									TMFCore.egioclaseOreId);
	}

	private static void loadMachines() {
		TMFCore.blockMachineBaseId = Integer.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_BLOCK,
																				"blockMachine",
																				1100).getInt());
		XMLLoader.addXmlVariable(	"$refinery",
									EnumMachine.REFINERY.getId());
		XMLLoader.addXmlVariable(	"$grinder",
									EnumMachine.GRINDER.getId());
		XMLLoader.addXmlVariable(	"$geoEquip",
									EnumMachine.GEOEQUIP.getId());
		XMLLoader.addXmlVariable(	"$autoMixTable",
									EnumMachine.AUTOMIXTABLE.getId());

	}

	private static void loadLogger() {
		TMFCore.loggerLevel = String.valueOf(TMFCore.configuration.get(	Configuration.CATEGORY_GENERAL,
																		"loggerLevel",
																		TMFCore.loggerLevel).getString());

		LoggerTMF.getInstance(Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(TMFCore.loggerLevel);
	}
}
