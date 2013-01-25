package slimevoid.tmf.core;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import slimevoid.lib.util.XMLLanguageLoader;
import slimevoid.lib.util.XMLRecipeLoader;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.NamingLib;
import slimevoid.tmf.core.world.WorldGeneration;
import slimevoid.tmf.fuel.MineralFuelHandler;
import slimevoid.tmf.machines.JSONGrinderRecipesLoader;
import slimevoid.tmf.machines.JSONRefineryRecipesLoader;
import slimevoid.tmf.machines.blocks.BlockGeologicalEquipment;
import slimevoid.tmf.machines.blocks.BlockGrinder;
import slimevoid.tmf.machines.blocks.BlockRefinery;
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
		
		XMLLanguageLoader.addItemMapping(miningHelmetLamp);
		XMLLanguageLoader.addItemMapping(miningHelmetIron);
		XMLLanguageLoader.addItemMapping(miningHelmetGold);
		XMLLanguageLoader.addItemMapping(miningHelmetDiamond);
		XMLLanguageLoader.addItemMapping(motionSensor);
		XMLLanguageLoader.addItemMapping(miningToolBelt);
		
		mineralAcxium = new ItemMineral(mineralAcxiumId).setBurnTime(2400).setItemName("mineralAcxium").setIconCoord(0, 1);
		mineralBisogen = new ItemMineral(mineralBisogenId).setBurnSpeed(150).setItemName("mineralBisogen").setIconCoord(1, 1);
		mineralCydrine = new ItemMineral(mineralCydrineId).setBurnWidth(1).setItemName("mineralCydrine").setIconCoord(2, 1);

		XMLLanguageLoader.addItemMapping(mineralAcxium);
		XMLLanguageLoader.addItemMapping(mineralBisogen);
		XMLLanguageLoader.addItemMapping(mineralCydrine);
		
		dustAcxium = new ItemMineralDust(dustAcxiumId).setBurnTime(3200).setItemName("dustAcxium").setIconCoord(0, 2);
		dustBisogen = new ItemMineralDust(dustBisogenId).setBurnSpeed(100).setItemName("dustBisogen").setIconCoord(1, 2);
		dustCydrine = new ItemMineralDust(dustCydrineId).setBurnWidth(1).setItemName("dustCydrine").setIconCoord(2, 2);
		
		dustMixed = new ItemMineralMixedDust(dustMixedId).setItemName("dustMixed").setIconCoord(3, 2);
		
		XMLLanguageLoader.addItemMapping(dustAcxium);
		XMLLanguageLoader.addItemMapping(dustBisogen);
		XMLLanguageLoader.addItemMapping(dustCydrine);
		XMLLanguageLoader.addItemMapping(dustMixed);
	}
	
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
		geoEquipActiveId;
	public static Block
		refineryIdle,
		refineryActive,
		grinderIdle,
		grinderActive,
		geoEquipIdle,
		geoEquipActive;
	
	// BLOCKS

	// ======== BLOCK REGISTRATION ========
	public static void registerBlocks() {
		
		BlockLib.init();
		
		// BlockTMFOre(int id, int texture, int spawnLevel, int spawnRate, int spawnSize, int veinSize, int lightLevel)
		arkiteOre = new BlockTMFOre(arkiteOreId, 0, 60, 100, 5, 0.2F).setBlockName("arkiteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		bistiteOre = new BlockTMFOre(bistiteOreId, 1, 36, 100, 5, 0.3F).setBlockName("bistiteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		crokereOre = new BlockTMFOre(crokereOreId, 2, 30, 100, 5, 0.4F).setBlockName("crokereOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		derniteOre = new BlockTMFOre(derniteOreId, 3, 26, 100, 5, 0.5F).setBlockName("derniteOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);
		egioclaseOre = new BlockTMFOre(egioclaseOreId, 4, 20, 100, 5, 0.6F).setBlockName("egioclaseOre").setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabTMF.tabTMF);

		GameRegistry.registerBlock(arkiteOre, "arkiteOre");
		GameRegistry.registerBlock(bistiteOre, "bistiteOre");
		GameRegistry.registerBlock(crokereOre, "crokereOre");
		GameRegistry.registerBlock(derniteOre, "derniteOre");
		GameRegistry.registerBlock(egioclaseOre, "egioclaseOre");

        MinecraftForge.setBlockHarvestLevel(arkiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(bistiteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(crokereOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(derniteOre,  "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(egioclaseOre,  "pickaxe", 2);
        
        GameRegistry.registerWorldGenerator(new WorldGeneration());
        
        registerMachines();
	}
	
	public static void registerMachines() {
		// REFINERY
		refineryIdle = new BlockRefinery(refineryIdleId,0,0,false).setBlockName("refinery.idle").setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		refineryActive = new BlockRefinery(refineryActiveId,0,0,true).setBlockName("refinery.active").setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(refineryIdle,"refinery.idle");
		GameRegistry.registerBlock(refineryActive,"refinery.active");
		GameRegistry.registerTileEntity(TileEntityRefinery.class, "TMF Refinery");

		JSONRefineryRecipesLoader.loadFile(new File(TMFCore.class.getResource("/TheMinersFriend/machines/refinery.json").getFile()));

		// GRINDER
		grinderIdle = new BlockGrinder(grinderIdleId,0,1,false).setBlockName("grinder.idle").setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		grinderActive = new BlockGrinder(grinderActiveId,0,1,true).setBlockName("grinder.active").setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(grinderIdle,"grinder.idle");
		GameRegistry.registerBlock(grinderActive,"grinder.active");
		GameRegistry.registerTileEntity(TileEntityGrinder.class, "TMF Grinder");
		
		JSONGrinderRecipesLoader.loadFile(new File(TMFCore.class.getResource("/TheMinersFriend/machines/grinder.json").getFile()));

		// GEOLOGICAL EQUIPMENT
		geoEquipIdle = new BlockGeologicalEquipment(geoEquipIdleId,0,2,false).setBlockName("geoEquip.idle").setHardness(3.5F).setCreativeTab(CreativeTabTMF.tabTMF);
		geoEquipActive = new BlockGeologicalEquipment(geoEquipActiveId,0,2,true).setBlockName("geoEquip.active").setHardness(3.5F).setLightValue(0.875F);
		
		GameRegistry.registerBlock(geoEquipIdle,"geoEquip.idle");
		GameRegistry.registerBlock(geoEquipActive,"geoEquip.active");
		GameRegistry.registerTileEntity(TileEntityGeologicalEquipment.class, "TMF Geological Equipment");
	}

	public static void registerNames() {
		
	}
	
	// ======= RECIPE REGISTRATION =======
	public static void registerRecipes() {
		XMLLanguageLoader.loadDefaults(new File(TMFCore.class.getResource("/TheMinersFriend/names").getFile()));
		XMLLanguageLoader.loadFolder(new File(TMFInit.TMF.getProxy().getMinecraftDir()+"/config/TMFNames"));
		
		LanguageRegistry.instance().addStringLocalization(CreativeTabTMF.tabTMF.getTranslatedTabLabel(), NamingLib.TMFNAME);
	}
}
