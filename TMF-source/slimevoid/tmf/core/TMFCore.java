package slimevoid.tmf.core;

import java.io.File;

import cpw.mods.fml.common.registry.GameRegistry;

import slimevoid.tmf.items.ItemMinersHat;

import eurysmods.api.ICommonProxy;

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
	public static String loggerLevel = "INFO";

	public static void initialize(ICommonProxy proxy) {
		TMFInit.initialize(proxy);
	}

	public static void addItems() {
		miningHelmetIron = new ItemMinersHat(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(0, 0);
		miningHelmetGold = new ItemMinersHat(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(0, 1);
		miningHelmetDiamond = new ItemMinersHat(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(0, 2);
	}

	public static void addNames() {
		GameRegistry.registerItem(miningHelmetIron, "Iron Mining Helmet");
		GameRegistry.registerItem(miningHelmetGold, "Gold Mining Helmet");
		GameRegistry.registerItem(miningHelmetDiamond, "Diamond Mining Helmet");
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
		configuration.save();
		LoggerTMF.getInstance("LittleBlocksConfig").setFilterLevel(loggerLevel);
		return 0;
	}
}
