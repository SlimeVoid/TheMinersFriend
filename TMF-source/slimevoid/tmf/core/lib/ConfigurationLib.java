package slimevoid.tmf.core.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import net.minecraftforge.common.Configuration;

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
		motionSensorMaxGameTicks = 20;
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
