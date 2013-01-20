package slimevoid.tmf.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMiningHelmet;
import slimevoid.tmf.items.ItemMiningLamp;
import slimevoid.tmf.items.ItemMiningToolBelt;
import slimevoid.tmf.items.ItemMotionSensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TMFCore {
	public static final String packetChannel = "TMF";
	
	public static File configFile;
	public static Configuration configuration;
	public static Item miningHelmetLamp;
	public static Item miningHelmetIron;
	public static Item miningHelmetGold;
	public static Item miningHelmetDiamond;
	public static int miningHelmetLampId, miningHelmetIronId, miningHelmetGoldId, miningHelmetDiamondId;

	public static Item mineralAcxium;
	public static Item mineralBisogen;
	public static Item mineralCydrine;
	public static int mineralAcxiumId,mineralBisogenId,mineralCydrineId;
	
	public static Item motionSensor;
	public static int motionSensorId;
	
	public static Item miningToolBelt;
	public static int miningToolBeltId;
	
	public static String loggerLevel = "INFO";

	public static void initialize(ICommonProxy proxy) {
		TMFInit.initialize(proxy);
	}

	public static void addItems() {
		miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setItemName("miningHelmetLamp").setIconCoord(4, 0);
		miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(0, 0);
		miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(1, 0);
		miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(2, 0);
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName("motionSensor").setIconCoord(0, 1);
		miningToolBelt = new ItemMiningToolBelt(miningToolBeltId).setItemName("miningToolBelt").setIconCoord(0, 2);
		
		mineralAcxium = new ItemMineral(mineralAcxiumId).setItemName("mineralAcxium").setIconCoord(0, 1);
		mineralBisogen = new ItemMineral(mineralBisogenId).setItemName("mineralBisogen").setIconCoord(1, 1);
		mineralCydrine = new ItemMineral(mineralCydrineId).setItemName("mineralCydrine").setIconCoord(2, 1);
	}

	public static void addNames() {
		LanguageRegistry.addName(miningHelmetLamp, "Mining Helmet Lamp");
		LanguageRegistry.addName(miningHelmetIron, "Iron Mining Helmet");
		LanguageRegistry.addName(miningHelmetGold, "Gold Mining Helmet");
		LanguageRegistry.addName(miningHelmetDiamond, "Diamond Mining Helmet");
		//GameRegistry.registerItem(motionSensor, "Motion Sensor");
		LanguageRegistry.addName(motionSensor, "Motion Sensor");
		LanguageRegistry.addName(miningToolBelt, "Miner's ToolBelt");
		
		LanguageRegistry.addName(mineralAcxium, "Acxium");
		LanguageRegistry.addName(mineralBisogen, "Bisogen");
		LanguageRegistry.addName(mineralCydrine, "Cydrine");
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
}
