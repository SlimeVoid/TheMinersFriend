package slimevoid.tmf.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.fuel.MineralFuelHandler;
import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMineralDust;
import slimevoid.tmf.items.ItemMineralMixedDust;
import slimevoid.tmf.items.ItemMiningHelmet;
import slimevoid.tmf.items.ItemMiningLamp;
import slimevoid.tmf.items.ItemMiningToolBelt;
import slimevoid.tmf.items.ItemMotionSensor;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TMFCore {
	public static final String packetChannel = "TMF";
	
	// CONFIG
	public static File configFile;
	public static Configuration configuration;
	
	// MINING EQUIPMENT
	public static Item miningHelmetLamp;
	public static Item miningHelmetIron;
	public static Item miningHelmetGold;
	public static Item miningHelmetDiamond;
	public static int miningHelmetLampId, miningHelmetIronId, miningHelmetGoldId, miningHelmetDiamondId;
	public static Item motionSensor;
	public static int motionSensorId;
	public static Item miningToolBelt;
	public static int miningToolBeltId;
	
	// MINERALS
	public static Item mineralAcxium;
	public static Item mineralBisogen;
	public static Item mineralCydrine;
	public static int mineralAcxiumId,mineralBisogenId,mineralCydrineId;
	// MINERAL DUSTS
	public static Item dustAcxium;
	public static Item dustBisogen;
	public static Item dustCydrine;
	public static Item dustMixed;
	public static int dustAcxiumId,dustBisogenId,dustCydrineId,dustMixedId;
	
	public static String loggerLevel = "INFO";

	// ======== ITEM REGISTRATION ========
	
	public static void addItems() {
		miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setItemName("miningHelmetLamp").setIconCoord(4, 0);
		miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(0, 0);
		miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(1, 0);
		miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(2, 0);
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName("motionSensor").setIconCoord(0, 1);
		miningToolBelt = new ItemMiningToolBelt(miningToolBeltId).setItemName("miningToolBelt").setIconCoord(0, 2);
		
		mineralAcxium = new ItemMineral(mineralAcxiumId).setBurnTime(3200).setItemName("mineralAcxium").setIconCoord(0, 1);
		mineralBisogen = new ItemMineral(mineralBisogenId).setBurnTime(1600).setItemName("mineralBisogen").setIconCoord(1, 1);
		mineralCydrine = new ItemMineral(mineralCydrineId).setBurnTime(1600).setItemName("mineralCydrine").setIconCoord(2, 1);
		
		dustAcxium = new ItemMineralDust(dustAcxiumId).setBurnTime(3200).setItemName("dustAcxium").setIconCoord(0, 2);
		dustBisogen = new ItemMineralDust(dustBisogenId).setBurnTime(1600).setItemName("dustBisogen").setIconCoord(1, 2);
		dustCydrine = new ItemMineralDust(dustCydrineId).setBurnTime(1600).setItemName("dustCydrine").setIconCoord(2, 2);
		dustMixed = new ItemMineralMixedDust(dustMixedId).setItemName("dustMixed").setIconCoord(3, 2);
	}
	
	public static void addFuels() {
		GameRegistry.registerFuelHandler(new MineralFuelHandler());
	}

	public static void addItemNames() {
		LanguageRegistry.addName(miningHelmetLamp, "Mining Helmet Lamp");
		LanguageRegistry.addName(miningHelmetIron, "Iron Mining Helmet");
		LanguageRegistry.addName(miningHelmetGold, "Gold Mining Helmet");
		LanguageRegistry.addName(miningHelmetDiamond, "Diamond Mining Helmet");
		
		LanguageRegistry.addName(motionSensor, "Motion Sensor");
		
		LanguageRegistry.addName(miningToolBelt, "Miner's ToolBelt");
		
		LanguageRegistry.addName(mineralAcxium, "Acxium");
		LanguageRegistry.addName(mineralBisogen, "Bisogen");
		LanguageRegistry.addName(mineralCydrine, "Cydrine");
		
		LanguageRegistry.addName(dustAcxium, "Acxium Dust");
		LanguageRegistry.addName(dustBisogen, "Bisogen Dust");
		LanguageRegistry.addName(dustCydrine, "Cydrine Dust");
		LanguageRegistry.addName(dustMixed, "Mixed Dust");
	}

	public static void addItemRecipes() {
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
	
	// ORES
	public static int tmfOreId;
	
	public static BlockTMFOre 
		ArkiteOre,
		BistiteOre,
		CrokereOre,
		DerniteOre,
		EgioclaseOre;
	
	// MACHINES
	
	
	// BLOCKS

	// ======== BLOCK REGISTRATION ========
	public static void registerBlocks() {
		
	}
}
