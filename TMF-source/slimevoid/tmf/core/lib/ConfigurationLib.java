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
		motionSensorDrawRight = Boolean.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_GENERAL,
				"motionSensorDrawRight",
				motionSensorDrawRight).value);
		TMFCore.configuration.save();
		
		motionSensorMaxEntityDistance = 20;
		motionSensorMaxGameTicks = 40;
		motionSensorDrawRight = true;
	}
	
	public static void CommonConfig() {
		TMFCore.configuration.load();
		TMFCore.loggerLevel = String.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_GENERAL,
				"loggerLevel",
				TMFCore.loggerLevel).value);
		
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

		TMFCore.motionSensorId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"motionSensor",
				15003).value);

		TMFCore.miningHelmetLampId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"helmetLamp",
				15004).value);

		TMFCore.miningToolBeltId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_ITEM,
				"toolBelt",
				15005).value);
		
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
		
		TMFCore.tmfOreId = Integer.valueOf(TMFCore.configuration.get(
				Configuration.CATEGORY_BLOCK,
				"oreBase",
				1025).value);
		
		TMFCore.configuration.save();
		
		LoggerTMF.getInstance(
				Logger.filterClassName(
						TMFCore.class.toString()
				)
		).setFilterLevel(
				TMFCore.loggerLevel
		);
	}

}
