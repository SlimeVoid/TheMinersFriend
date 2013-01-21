package slimevoid.tmf.core;

import java.io.File;
import java.io.IOException;

import argo.saj.InvalidSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import slimevoid.lib.util.XMLRecipeLoader;
import slimevoid.tmf.blocks.machines.JSONRefineryRecipesLoader;
import slimevoid.tmf.blocks.machines.RefineryRecipes;
import slimevoid.tmf.blocks.machines.blocks.BlockRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.NamingLib;
import slimevoid.tmf.core.world.WorldGeneration;
import slimevoid.tmf.fuel.MineralFuelHandler;
import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMineralDust;
import slimevoid.tmf.items.ItemMineralMixedDust;
import slimevoid.tmf.items.ItemMineralMixedDustRecipe;
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
	
	public static String loggerLevel = "INFO";
	
	// MINING EQUIPMENT
	public static int 
		miningHelmetLampId,
		miningHelmetIronId, 
		miningHelmetGoldId, 
		miningHelmetDiamondId;
	public static Item 
		miningHelmetLamp,
		miningHelmetIron,
		miningHelmetGold,
		miningHelmetDiamond;
	
	public static Item 
		motionSensor;
	public static int 
		motionSensorId;
	
	public static Item 
		miningToolBelt;
	public static int 
		miningToolBeltId;
	
	// MINERALS
	public static Item 
		mineralAcxium,
		mineralBisogen,
		mineralCydrine;
	public static int 
		mineralAcxiumId,
		mineralBisogenId,
		mineralCydrineId;
	
	// MINERAL DUSTS
	public static Item 
		dustAcxium,
		dustBisogen,
		dustCydrine,
		dustMixed;
	public static int 
		dustAcxiumId,
		dustBisogenId,
		dustCydrineId,
		dustMixedId;
	
	// ======== ITEM REGISTRATION ========
	
	public static void registerItems() {
		miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setItemName("miningHelmetLamp").setIconCoord(4, 0);
		miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName("ironMiningHelmet").setIconCoord(2, 0);
		miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName("goldMiningHelmet").setIconCoord(1, 0);
		miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName("diamondMiningHelmet").setIconCoord(0, 0);
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName("motionSensor").setIconCoord(0, 1);
		miningToolBelt = new ItemMiningToolBelt(miningToolBeltId).setItemName("miningToolBelt").setIconCoord(0, 2);
		
		mineralAcxium = new ItemMineral(mineralAcxiumId).setBurnTime(3200).setItemName("mineralAcxium").setIconCoord(0, 1);
		mineralBisogen = new ItemMineral(mineralBisogenId).setBurnSpeed(100).setItemName("mineralBisogen").setIconCoord(1, 1);
		mineralCydrine = new ItemMineral(mineralCydrineId).setBurnWidth(1).setItemName("mineralCydrine").setIconCoord(2, 1);
		
		dustAcxium = new ItemMineralDust(dustAcxiumId).setBurnTime(3200).setItemName("dustAcxium").setIconCoord(0, 2);
		dustBisogen = new ItemMineralDust(dustBisogenId).setBurnSpeed(100).setItemName("dustBisogen").setIconCoord(1, 2);
		dustCydrine = new ItemMineralDust(dustCydrineId).setBurnWidth(1).setItemName("dustCydrine").setIconCoord(2, 2);
		
		dustMixed = new ItemMineralMixedDust(dustMixedId).setItemName("dustMixed").setIconCoord(3, 2);
	}
	
	public static void registerFuels() {
		GameRegistry.registerFuelHandler(new MineralFuelHandler());
	}

	public static void registerItemNames() {
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
	
	// ORES
	public static int
		arkiteOreId,
		bistiteOreId,
		crokereOreId,
		derniteOreId,
		egioclaseOreId;
	
	public static Block
		arkiteOre,
		bistiteOre,
		crokereOre,
		derniteOre,
		egioclaseOre;
	
	// MACHINES
	public static int
		refineryIdleId,
		refineryActiveId;
	public static Block
		refineryIdle,
		refineryActive;
	
	// BLOCKS

	// ======== BLOCK REGISTRATION ========
	public static void registerBlocks() {
		
		BlockLib.init();
		
		// BlockTMFOre(int id, int texture, int spawnLevel, int spawnRate, int spawnSize, int veinSize, int lightLevel)
		arkiteOre = new BlockTMFOre(arkiteOreId, 0, 60, 100, 5, 0.2F).setBlockName("arkiteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock);
		bistiteOre = new BlockTMFOre(bistiteOreId, 1, 36, 100, 5, 0.3F).setBlockName("bistiteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock);;
		crokereOre = new BlockTMFOre(crokereOreId, 2, 30, 100, 5, 0.4F).setBlockName("crokereOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock);;
		derniteOre = new BlockTMFOre(derniteOreId, 3, 26, 100, 5, 0.5F).setBlockName("derniteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock);;
		egioclaseOre = new BlockTMFOre(egioclaseOreId, 4, 20, 100, 5, 0.6F).setBlockName("egioclaseOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock);;

		GameRegistry.registerBlock(arkiteOre, "arkiteOre");
		GameRegistry.registerBlock(bistiteOre, "bistiteOre");
		GameRegistry.registerBlock(crokereOre, "crokereOre");
		GameRegistry.registerBlock(derniteOre, "derniteOre");
		GameRegistry.registerBlock(egioclaseOre, "egioclaseOre");

		LanguageRegistry.addName(arkiteOre, NamingLib.ORE_ARKITE);
		LanguageRegistry.addName(bistiteOre, NamingLib.ORE_BISTITE);
		LanguageRegistry.addName(crokereOre, NamingLib.ORE_CROKERE);
		LanguageRegistry.addName(derniteOre, NamingLib.ORE_DERNITE);
		LanguageRegistry.addName(egioclaseOre, NamingLib.ORE_EGIOCLASE);

        MinecraftForge.setBlockHarvestLevel(arkiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(bistiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(crokereOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(derniteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(egioclaseOre,  "pickaxe", 2);
        
        GameRegistry.registerWorldGenerator(new WorldGeneration());
        
        registerMachines();
	}
	
	public static void registerMachines() {
		refineryIdle = new BlockRefinery(refineryIdleId,0,0,false).setBlockName("refinery.idle").setHardness(3.5F).setCreativeTab(CreativeTabs.tabBlock);
		refineryActive = new BlockRefinery(refineryActiveId,0,0,true).setBlockName("refinery.active").setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(refineryIdle,"refinery.idle");
		GameRegistry.registerBlock(refineryActive,"refinery.active");
		GameRegistry.registerTileEntity(TileEntityRefinery.class, "TMF Refinery");
		LanguageRegistry.addName(refineryIdle, NamingLib.REFINERY);
		LanguageRegistry.addName(refineryActive, NamingLib.REFINERY);
		
		JSONRefineryRecipesLoader.loadFile(new File(TMFCore.class.getResource("/TheMinersFriend/resources/refinery.json").getFile()));
	}

	
	
	// ======= RECIPE REGISTRATION =======
	public static void registerRecipes() {
		XMLRecipeLoader.loadDefaults(new File(TMFCore.class.getResource("/TheMinersFriend/recipes").getFile()));
		XMLRecipeLoader.loadFolder(new File(TMFInit.TMF.getProxy().getMinecraftDir()+"/config/TMFRecipes"));
		
		GameRegistry.addRecipe(new ItemMineralMixedDustRecipe());
	}
}
