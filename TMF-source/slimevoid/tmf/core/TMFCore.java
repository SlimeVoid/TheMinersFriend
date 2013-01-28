package slimevoid.tmf.core;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import slimevoid.lib.util.FileReader;
import slimevoid.lib.util.xml.XMLLanguageLoader;
import slimevoid.lib.util.xml.XMLRecipeLoader;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.ItemLib;
import slimevoid.tmf.core.lib.LocalizationLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.core.world.WorldGeneration;
import slimevoid.tmf.fuel.MineralFuelHandler;
import slimevoid.tmf.fuel.MixedDustNameRegistry;
import slimevoid.tmf.machines.JSONGrinderRecipesLoader;
import slimevoid.tmf.machines.JSONRefineryRecipesLoader;
import slimevoid.tmf.machines.blocks.BlockAutomaticMixingTable;
import slimevoid.tmf.machines.blocks.BlockGeologicalEquipment;
import slimevoid.tmf.machines.blocks.BlockGrinder;
import slimevoid.tmf.machines.blocks.BlockRefinery;
import slimevoid.tmf.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.minerals.items.ItemMineral;
import slimevoid.tmf.minerals.items.ItemMineralDust;
import slimevoid.tmf.minerals.items.ItemMineralMixedDust;
import slimevoid.tmf.minerals.items.ItemMineralMixedDustRecipe;
import slimevoid.tmf.tools.items.ItemMiningHelmet;
import slimevoid.tmf.tools.items.ItemMiningLamp;
import slimevoid.tmf.tools.items.ItemMiningToolBelt;
import slimevoid.tmf.tools.items.ItemMotionSensor;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMFCore {
	
	// CONFIG
	public static File configFile;
	public static Configuration configuration;
	
	public static String loggerLevel = "INFO";
	
	// TOOLS
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
		registerTools();
		registerMinerals();
		registerDusts();
	}
	private static void registerTools() {
		registerMiningHelmets();
		registerMotionSensor();
		registerToolBelt();
	}
	private static void registerMiningHelmets() {
		miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setItemName(ItemLib.MINING_HELMET_LAMP).setIconCoord(4, 0);
		miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, EnumArmorMaterial.IRON, 2, 0).setItemName(ItemLib.MINING_HELMET_IRON).setIconCoord(2, 0);
		miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, EnumArmorMaterial.GOLD, 4, 0).setItemName(ItemLib.MINING_HELMET_GOLD).setIconCoord(1, 0);
		miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, EnumArmorMaterial.DIAMOND, 3, 0).setItemName(ItemLib.MINING_HELMET_DIAMOND).setIconCoord(0, 0);
		
		XMLLanguageLoader.addItemMapping(miningHelmetLamp);
		XMLLanguageLoader.addItemMapping(miningHelmetIron);
		XMLLanguageLoader.addItemMapping(miningHelmetGold);
		XMLLanguageLoader.addItemMapping(miningHelmetDiamond);
	}
	private static void registerMotionSensor() {
		motionSensor = new ItemMotionSensor(motionSensorId).setItemName(ItemLib.MOTION_SENSOR).setIconCoord(0, 1);
		XMLLanguageLoader.addItemMapping(motionSensor);
	}
	private static void registerToolBelt() {
		miningToolBelt = new ItemMiningToolBelt(miningToolBeltId).setItemName(ItemLib.MINING_TOOLBELT).setIconCoord(0, 2);
		XMLLanguageLoader.addItemMapping(miningToolBelt);
	}
	private static void registerMinerals() {
		mineralAcxium = new ItemMineral(mineralAcxiumId).setBurnTime(2400).setItemName(ItemLib.MINERAL_AXCIUM).setIconCoord(0, 1);
		mineralBisogen = new ItemMineral(mineralBisogenId).setBurnSpeed(150).setItemName(ItemLib.MINERAL_BISOGEN).setIconCoord(1, 1);
		mineralCydrine = new ItemMineral(mineralCydrineId).setBurnWidth(1).setItemName(ItemLib.MINERAL_CYDRINE).setIconCoord(2, 1);

		XMLLanguageLoader.addItemMapping(mineralAcxium);
		XMLLanguageLoader.addItemMapping(mineralBisogen);
		XMLLanguageLoader.addItemMapping(mineralCydrine);
	}
	private static void registerDusts() {
		dustAcxium = new ItemMineralDust(dustAcxiumId).setBurnTime(3200).setItemName(ItemLib.DUST_AXCIUM).setIconCoord(0, 2);
		dustBisogen = new ItemMineralDust(dustBisogenId).setBurnSpeed(100).setItemName(ItemLib.DUST_BISOGEN).setIconCoord(1, 2);
		dustCydrine = new ItemMineralDust(dustCydrineId).setBurnWidth(1).setItemName(ItemLib.DUST_CYDRINE).setIconCoord(2, 2);
		
		dustMixed = new ItemMineralMixedDust(dustMixedId).setItemName(ItemLib.DUST_MIXED).setIconCoord(3, 2);
		
		ItemMineralMixedDust.script = FileReader.readFile(new File(TMFCore.class.getResource("/TheMinersFriend/resources/mixedDust.js").getFile()));
		ItemMineralMixedDust.script_burnTime = "getBurnTime()";
		ItemMineralMixedDust.script_burnSpeed = "getBurnSpeed()";
		ItemMineralMixedDust.script_burnWidth = "getBurnWidth()";
		
		XMLLanguageLoader.addItemMapping(dustAcxium);
		XMLLanguageLoader.addItemMapping(dustBisogen);
		XMLLanguageLoader.addItemMapping(dustCydrine);
		XMLLanguageLoader.addItemMapping(dustMixed);
		
		// TODO :: MixedDustNameRegistry : Move to or something
		MixedDustNameRegistry.addName(1, 1, 0, "Monoaxogen");
		MixedDustNameRegistry.addName(1, 0, 1, "Monoaxicyde");
		MixedDustNameRegistry.addName(0, 1, 1, "Monogencyde");
	}

	// ======== FUEL REGISTRATION ========
	public static void registerFuels() {
		GameRegistry.registerFuelHandler(new MineralFuelHandler());
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
		refineryActiveId,
		grinderIdleId,
		grinderActiveId,
		geoEquipIdleId,
		geoEquipActiveId,
		autoMixTableId;
	public static Block
		refineryIdle,
		refineryActive,
		grinderIdle,
		grinderActive,
		geoEquipIdle,
		geoEquipActive,
		autoMixTable;

	// ======== BLOCK REGISTRATION ========
	public static void registerBlocks() {
		
		BlockLib.init();
		
		registerOres();
        
        GameRegistry.registerWorldGenerator(new WorldGeneration());
        
        registerMachines();
	}
	private static void registerOres() {
		// BlockTMFOre(int id, int texture, int spawnLevel, int spawnRate, int spawnSize, int veinSize, int lightLevel)
		arkiteOre = new BlockTMFOre(arkiteOreId, 0, 60, 100, 5, 0.2F).setBlockName(BlockLib.ORE_ARKITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		bistiteOre = new BlockTMFOre(bistiteOreId, 1, 36, 100, 5, 0.3F).setBlockName(BlockLib.ORE_BISTITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		crokereOre = new BlockTMFOre(crokereOreId, 2, 30, 100, 5, 0.4F).setBlockName(BlockLib.ORE_CROKERE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		derniteOre = new BlockTMFOre(derniteOreId, 3, 26, 100, 5, 0.5F).setBlockName(BlockLib.ORE_DERNITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		egioclaseOre = new BlockTMFOre(egioclaseOreId, 4, 20, 100, 5, 0.6F).setBlockName(BlockLib.ORE_EGIOCLASE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);

		GameRegistry.registerBlock(arkiteOre, BlockLib.ORE_ARKITE);
		GameRegistry.registerBlock(bistiteOre, BlockLib.ORE_BISTITE);
		GameRegistry.registerBlock(crokereOre, BlockLib.ORE_CROKERE);
		GameRegistry.registerBlock(derniteOre, BlockLib.ORE_DERNITE);
		GameRegistry.registerBlock(egioclaseOre, BlockLib.ORE_EGIOCLASE);

        MinecraftForge.setBlockHarvestLevel(arkiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(bistiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(crokereOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(derniteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(egioclaseOre,  "pickaxe", 2);
	}
	private static void registerMachines() {
		// REFINERY
		refineryIdle = new BlockRefinery(refineryIdleId,0,0,false).setBlockName(BlockLib.REFINERY_IDLE).setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		refineryActive = new BlockRefinery(refineryActiveId,0,0,true).setBlockName(BlockLib.REFINERY_ACTIVE).setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(refineryIdle,BlockLib.REFINERY_IDLE);
		GameRegistry.registerBlock(refineryActive,BlockLib.REFINERY_ACTIVE);
		GameRegistry.registerTileEntity(TileEntityRefinery.class, BlockLib.TILE_REFINERY);

		JSONRefineryRecipesLoader.loadFile(new File(TMFCore.class.getResource(ResourceLib.RECIPES_REFINERY).getFile()));

		// GRINDER
		grinderIdle = new BlockGrinder(grinderIdleId,0,1,false).setBlockName(BlockLib.GRINDER_IDLE).setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		grinderActive = new BlockGrinder(grinderActiveId,0,1,true).setBlockName(BlockLib.GRINDER_ACTIVE).setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(grinderIdle,BlockLib.GRINDER_IDLE);
		GameRegistry.registerBlock(grinderActive,BlockLib.GRINDER_ACTIVE);
		GameRegistry.registerTileEntity(TileEntityGrinder.class, BlockLib.TILE_GRINDER);
		
		JSONGrinderRecipesLoader.loadFile(new File(TMFCore.class.getResource(ResourceLib.RECIPES_GRINDER).getFile()));

		// GEOLOGICAL EQUIPMENT
		geoEquipIdle = new BlockGeologicalEquipment(geoEquipIdleId,0,2,false).setBlockName(BlockLib.GEOEQUIP_IDLE).setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		geoEquipActive = new BlockGeologicalEquipment(geoEquipActiveId,0,2,true).setBlockName(BlockLib.GEOEQUIP_ACTIVE).setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(geoEquipIdle,BlockLib.GEOEQUIP_IDLE);
		GameRegistry.registerBlock(geoEquipActive,BlockLib.GEOEQUIP_ACTIVE);
		GameRegistry.registerTileEntity(TileEntityGeologicalEquipment.class, BlockLib.TILE_GEOEQUIPMENT);

		// AUTOMATIC MIXING TABLE
		autoMixTable = new BlockAutomaticMixingTable(autoMixTableId,0,3,false).setBlockName(BlockLib.BLOCK_AUTOMIXTABLE).setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		
		GameRegistry.registerBlock(autoMixTable,BlockLib.BLOCK_AUTOMIXTABLE);
		GameRegistry.registerTileEntity(TileEntityAutomaticMixingTable.class, BlockLib.TILE_AUTOMIXTABLE);
	}

	// ======== NAME REGISTRATION ========
	public static void registerNames() {
		//XMLLanguageLoader.loadDefaults(new File(TMFCore.class.getResource("/TheMinersFriend/names").getFile()));
		//XMLLanguageLoader.loadFolder(new File(TMFInit.TMF.getProxy().getMinecraftDir()+"/config/TMFNames"));
		
		LocalizationLib.registerLanguages();//LanguageRegistry.instance().addStringLocalization(CreativeTabTMF.tabTMF.getTranslatedTabLabel(), NamingLib.TMFNAME);
	}
	
	// ======= RECIPE REGISTRATION =======
	public static void registerRecipes() {	
		XMLRecipeLoader.loadDefaults(new File(TMFCore.class.getResource("/TheMinersFriend/recipes").getFile()));
		XMLRecipeLoader.loadFolder(new File(TheMinersFriend.proxy.getMinecraftDir()+"/config/TMFRecipes"));	
		
		GameRegistry.addRecipe(new ItemMineralMixedDustRecipe());
	}
}
