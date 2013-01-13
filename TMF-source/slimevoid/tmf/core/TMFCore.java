package slimevoid.tmf.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.items.ItemMiningHelmet;
import slimevoid.tmf.items.ItemMiningLamp;
import slimevoid.tmf.items.ItemMiningToolbelt;
import slimevoid.tmf.items.ItemMotionSensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TMFCore {
	public static final String packetChannel = "TMF";
	
	public static File configFile;
	public static Configuration configuration;
	public static Item miningHelmetLamp;
	public static Item miningHelmetIron;
	public static Item miningHelmetGold;
	public static Item miningHelmetDiamond;
	public static int miningHelmetLampId, miningHelmetIronId, miningHelmetGoldId, miningHelmetDiamondId;

	public static Item motionSensor;
	public static int motionSensorId;
	
	public static Item miningToolBelt;
	public static int miningToolBeltId;
	
	public static String loggerLevel = "INFO";
	
	@SideOnly(Side.CLIENT)
	public static int motionSensorMaxEntityDistance = 20;
	@SideOnly(Side.CLIENT)
	public static int motionSensorMaxGameTicks = 20;
	@SideOnly(Side.CLIENT)
	public static boolean motionSensorDrawRight = true;

	public static void initialize(ICommonProxy proxy) {
		TMFInit.initialize(proxy);
	}

	public static void addItems() {
		miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setItemName("miningHelmetLamp").setIconCoord(4, 0);
		miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(0, 0);
		miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(1, 0);
		miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(2, 0);
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName("motionSensor").setIconCoord(0, 1);
		miningToolBelt = new ItemMiningToolbelt(miningToolBeltId).setItemName("miningToolBelt").setIconCoord(0, 2);
	}

	public static void addNames() {
		LanguageRegistry.addName(miningHelmetLamp, "Mining Helmet Lamp");
		LanguageRegistry.addName(miningHelmetIron, "Iron Mining Helmet");
		LanguageRegistry.addName(miningHelmetGold, "Gold Mining Helmet");
		LanguageRegistry.addName(miningHelmetDiamond, "Diamond Mining Helmet");
		//GameRegistry.registerItem(motionSensor, "Motion Sensor");
		LanguageRegistry.addName(motionSensor, "Motion Sensor");
		LanguageRegistry.addName(miningToolBelt, "Miner's ToolBelt");
	}

	public static void addRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(miningHelmetIron),
				new Object[] {
					"X",
					"Y",
					Character.valueOf('X'),
					miningHelmetLamp,
					Character.valueOf('Y'),
					Item.helmetSteel
				}
		);
		GameRegistry.addRecipe(
				new ItemStack(miningHelmetGold),
				new Object[] {
					"X",
					"Y",
					Character.valueOf('X'),
					miningHelmetLamp,
					Character.valueOf('Y'),
					Item.helmetGold
				}
		);
		GameRegistry.addRecipe(
				new ItemStack(miningHelmetDiamond),
				new Object[] {
					"X",
					"Y",
					Character.valueOf('X'),
					miningHelmetLamp,
					Character.valueOf('Y'),
					Item.helmetDiamond
				}
		);
		GameRegistry.addRecipe(
				new ItemStack(miningToolBelt),
				new Object[] {
					"X",
					Character.valueOf('X'),
					Block.dirt
				}
		);
	}

	public static int configurationProperties() {
		configuration.load();
		
		loggerLevel = String.valueOf(configuration.get(
				Configuration.CATEGORY_GENERAL,
				"loggerLevel",
				loggerLevel).value);
		
		miningHelmetIronId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"ironMinersHat",
				15000).value);
		miningHelmetGoldId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"goldMinersHat",
				15001).value);
		miningHelmetDiamondId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"diamondMinersHat",
				15002).value);

		motionSensorId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"motionSensor",
				15003).value);

		miningHelmetLampId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"helmetLamp",
				15004).value);

		miningToolBeltId = Integer.valueOf(configuration.get(
				Configuration.CATEGORY_ITEM,
				"toolBelt",
				15005).value);
		
		motionSensorDrawRight = Boolean.valueOf(configuration.get(
				Configuration.CATEGORY_GENERAL,
				"motionSensorDrawRight",
				motionSensorDrawRight).value);
		
		configuration.save();
		LoggerTMF.getInstance(
				Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(loggerLevel);
		return 0;
	}
}
