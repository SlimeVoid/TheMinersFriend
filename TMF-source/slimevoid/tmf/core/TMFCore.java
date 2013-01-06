package slimevoid.tmf.core;

import java.io.File;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import slimevoid.tmf.handlers.MotionSensorTickHandler;
import slimevoid.tmf.items.ItemMinersHat;
import slimevoid.tmf.items.ItemMotionSensor;

import eurysmods.api.ICommonProxy;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class TMFCore {
	public static File configFile;
	public static Configuration configuration;
	public static Item miningHelmetIron;
	public static Item miningHelmetGold;
	public static Item miningHelmetDiamond;
	public static Item miningHelmetLamp;
	public static int miningHelmetIronId, miningHelmetGoldId, miningHelmetDiamondId;

	public static Item motionSensor;
	public static int motionSensorId;
	
	public static String loggerLevel = "INFO";

	public static void initialize(ICommonProxy proxy) {
		TMFInit.initialize(proxy);
	}

	public static void addItems() {
		miningHelmetIron = new ItemMinersHat(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(0, 0);
		miningHelmetGold = new ItemMinersHat(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(0, 1);
		miningHelmetDiamond = new ItemMinersHat(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(0, 2);
		
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName("motionSensor").setIconCoord(0, 3);
	}

	public static void addNames() {
		LanguageRegistry.addName(miningHelmetIron, "Iron Mining Helmet");
		LanguageRegistry.addName(miningHelmetGold, "Gold Mining Helmet");
		LanguageRegistry.addName(miningHelmetDiamond, "Diamond Mining Helmet");
		//GameRegistry.registerItem(motionSensor, "Motion Sensor");
		LanguageRegistry.addName(motionSensor, "Motion Sensor");
	}

	public static void addRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(miningHelmetIron),
				new Object[] {
					"X",
					"Y",
					Character.valueOf('X'),
					//TODO: Fix this.
					//miningHelmetLamp, 
					Block.dirt,
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
					//TODO: Fix this.
					//miningHelmetLamp,
					Block.dirt,
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
					//TODO: Fix this.
					//miningHelmetLamp,
					Block.dirt,
					Character.valueOf('Y'),
					Item.helmetDiamond
				}
		);
	}

	public static int configurationProperties() {
		configuration.load();
		loggerLevel = String.valueOf(configuration.get(
				Configuration.CATEGORY_GENERAL,
				"loggerLevel",
				"INFO").value);
		
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
		
		configuration.save();
		LoggerTMF.getInstance("LittleBlocksConfig").setFilterLevel(loggerLevel);
		return 0;
	}

	public static void registerHandlers() {
		TickRegistry.registerTickHandler(new MotionSensorTickHandler(), Side.CLIENT);
	}
}
