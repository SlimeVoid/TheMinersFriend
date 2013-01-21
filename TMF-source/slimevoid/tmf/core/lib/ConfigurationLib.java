package slimevoid.tmf.core.lib;

import net.minecraftforge.common.Configuration;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {
	
	@SideOnly(Side.CLIENT)
	public static int motionSensorMaxEntityDistance;
	@SideOnly(Side.CLIENT)
	public static int motionSensorMaxGameTicks;
	@SideOnly(Side.CLIENT)
	public static boolean motionSensorDrawRight;

	@SideOnly(Side.CLIENT)
	public static void ClientConfig() {
		TMFCore.configuration.load();
		
		loadMotionSensorClient();
		
		TMFCore.configuration.save();
	}
	
	public static void CommonConfig() {
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
		motionSensorDrawRight = Boolean.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_GENERAL,
				"motionSensorDrawRight",
				motionSensorDrawRight).value);
		
		motionSensorMaxEntityDistance = 20;
		motionSensorMaxGameTicks = 40;
		motionSensorDrawRight = true;
	}
	private static void loadMotionSensorCommon() {
		TMFCore.motionSensorId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"motionSensor",
				15003).value);
		
		RecipeLib.addXmlVariable("$motionSensor", TMFCore.motionSensorId+256);
	}
	private static void loadMiningHelmet() {
		TMFCore.miningHelmetIronId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"ironMinersHat",
				15000).value);
		TMFCore.miningHelmetGoldId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"goldMinersHat",
				15001).value);
		TMFCore.miningHelmetDiamondId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"diamondMinersHat",
				15002).value);
		TMFCore.miningHelmetLampId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"helmetLamp",
				15004).value);
		
		RecipeLib.addXmlVariable("$ironMinersHat",		TMFCore.miningHelmetIronId+256);
		RecipeLib.addXmlVariable("$goldMinersHat",		TMFCore.miningHelmetGoldId+256);
		RecipeLib.addXmlVariable("$diamondMinersHat",	TMFCore.miningHelmetDiamondId+256);
		RecipeLib.addXmlVariable("$helmetLamp", 		TMFCore.miningHelmetLampId+256);
	}
	private static void loadToolBelt() {
		TMFCore.miningToolBeltId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"toolBelt",
				15005).value);
		
		RecipeLib.addXmlVariable("$toolBelt", TMFCore.miningToolBeltId);
	}
	private static void loadMinerals() {
		TMFCore.mineralAcxiumId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"mineralAcxium",
				15010).value);
		TMFCore.mineralBisogenId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"mineralBisogen",
				15011).value);
		TMFCore.mineralCydrineId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"mineralCydrine",
				15012).value);
		
		RecipeLib.addXmlVariable("$mineralAcxium", 		TMFCore.mineralAcxiumId+256);
		RecipeLib.addXmlVariable("$mineralBisogen", 	TMFCore.mineralBisogenId+256);
		RecipeLib.addXmlVariable("$mineralCydrine", 	TMFCore.mineralCydrineId+256);
	}
	private static void loadDusts() {
		TMFCore.dustAcxiumId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"dustAcxium",
				15020).value);
		TMFCore.dustBisogenId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"dustBisogen",
				15021).value);
		TMFCore.dustCydrineId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"dustCydrine",
				15022).value);
		TMFCore.dustMixedId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"dustMixed",
				15023).value);
		
		RecipeLib.addXmlVariable("$dustAcxium", 	TMFCore.dustAcxiumId+256);
		RecipeLib.addXmlVariable("$dustBisogen", 	TMFCore.dustBisogenId+256);
		RecipeLib.addXmlVariable("$dustCydrine", 	TMFCore.dustCydrineId+256);
		RecipeLib.addXmlVariable("$dustMixed", 		TMFCore.dustMixedId+256);
	}
	private static void loadOres() {
		TMFCore.arkiteOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"arkiteOre",
				1025).value);
		TMFCore.bistiteOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"bistiteOre",
				1026).value);
		TMFCore.crokereOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"crokereOre",
				1027).value);
		TMFCore.derniteOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"derniteOre",
				1028).value);
		TMFCore.egioclaseOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"egioclaseOre",
				1029).value);
		
		RecipeLib.addXmlVariable("$arkiteOre", 		TMFCore.arkiteOreId);
		RecipeLib.addXmlVariable("$bistiteOre", 	TMFCore.bistiteOreId);
		RecipeLib.addXmlVariable("$crokereOre", 	TMFCore.crokereOreId);
		RecipeLib.addXmlVariable("$derniteOre", 	TMFCore.derniteOreId);
		RecipeLib.addXmlVariable("$egioclaseOre", 	TMFCore.egioclaseOreId);
	}
	private static void loadMachines() {
		TMFCore.refineryIdleId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"refineryIdle",
				1100).value);
		TMFCore.refineryActiveId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"refineryActive",
				1101).value);
		
		RecipeLib.addXmlVariable("$refineryIdle", 		TMFCore.refineryIdleId);
		RecipeLib.addXmlVariable("$refineryActive", 	TMFCore.refineryActiveId);
	}
	private static void loadLogger() {
		TMFCore.loggerLevel = String.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_GENERAL,
				"loggerLevel",
				TMFCore.loggerLevel).value);
		
		LoggerTMF.getInstance(
				Logger.filterClassName(
						TMFCore.class.toString()
				)
		).setFilterLevel(
				TMFCore.loggerLevel
		);
	}
}
