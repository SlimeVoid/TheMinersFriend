/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.tmf.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.library.items.ItemBlockBase;
import net.slimevoid.library.util.FileReader;
import net.slimevoid.library.util.json.JSONLoader;
import net.slimevoid.library.util.xml.XMLRecipeLoader;
import net.slimevoid.tmf.blocks.BlockMiningLamp;
import net.slimevoid.tmf.blocks.machines.BlockMachineBase;
import net.slimevoid.tmf.blocks.machines.EnumMachine;
import net.slimevoid.tmf.blocks.machines.recipes.JSONGrinderRecipesLoader;
import net.slimevoid.tmf.blocks.machines.recipes.JSONRefineryRecipesLoader;
import net.slimevoid.tmf.blocks.ores.BlockTMFOre;
import net.slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import net.slimevoid.tmf.core.lib.BlockLib;
import net.slimevoid.tmf.core.lib.ConfigurationLib;
import net.slimevoid.tmf.core.lib.ItemLib;
import net.slimevoid.tmf.core.lib.ResourceLib;
import net.slimevoid.tmf.core.world.WorldGeneration;
import net.slimevoid.tmf.fuel.MineralFuelHandler;
import net.slimevoid.tmf.fuel.MixedDustNameRegistry;
import net.slimevoid.tmf.items.minerals.ItemMineral;
import net.slimevoid.tmf.items.minerals.ItemMineralDust;
import net.slimevoid.tmf.items.minerals.ItemMineralIngot;
import net.slimevoid.tmf.items.minerals.ItemMineralMixedDust;
import net.slimevoid.tmf.items.minerals.ItemMineralMixedDustRecipe;
import net.slimevoid.tmf.items.minerals.ItemMineralNugget;
import net.slimevoid.tmf.items.parts.ItemMachinePart;
import net.slimevoid.tmf.items.tools.ItemMiningArmor;
import net.slimevoid.tmf.items.tools.ItemMiningHelmet;
import net.slimevoid.tmf.items.tools.ItemMiningLamp;
import net.slimevoid.tmf.items.tools.ItemMiningToolBelt;
import net.slimevoid.tmf.items.tools.ItemMotionSensor;
import net.slimevoid.tmf.items.tools.recipes.ArmorRecipes;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMFCore {

    public static String          loggerLevel = "INFO";

    // TOOLS
    public static int             miningHelmetLampId, miningHelmetIronId,
            miningHelmetGoldId, miningHelmetDiamondId;
    public static ItemMiningArmor miningHelmetIron, miningHelmetGold,
            miningHelmetDiamond;

    public static Item            miningHelmetLamp;

    public static Item            motionSensor;
    public static int             motionSensorId;

    public static Item            miningToolBelt;
    public static int             miningToolBeltId;

    public static Item            utilityBelt;
    public static int             utilityBeltId;

    // MINERALS
    public static Item            mineralAcxium, mineralBisogen,
            mineralCydrine;
    public static int             mineralAcxiumId, mineralBisogenId,
            mineralCydrineId;

    // NUGGETS
    public static Item            nuggetAcxium, nuggetBisogen, nuggetCydrine;
    public static int             nuggetAcxiumId, nuggetBisogenId,
            nuggetCydrineId;

    // INGOTS
    public static Item            ingotAcxium, ingotBisogen, ingotCydrine;
    public static int             ingotAcxiumId, ingotBisogenId,
            ingotCydrineId;

    // MINERAL DUSTS
    public static Item            dustAcxium, dustBisogen, dustCydrine,
            dustMixed;
    public static int             dustAcxiumId, dustBisogenId, dustCydrineId,
            dustMixedId;

    // MACHINE PARTS
    public static Item            partAcxiumCore, partAlloyCasing,
            partAcxogenScreen, partBisogenGear, partCydrineMotor,
            partCydriumSensor;
    public static int             partAcxiumCoreId, partAlloyCasingId,
            partAcxogenScreenId, partBisogenGearId, partCydrineMotorId,
            partCydriumSensorId;

    // ======== ITEM REGISTRATION ========
    public static void registerItems() {
        registerTools();
        registerMinerals();
        registerIngots();
        registerParts();
        registerDusts();
    }

    public static void registerToolBelt() {
        miningToolBelt = new ItemMiningToolBelt(miningToolBeltId).setUnlocalizedName(ItemLib.MINING_TOOLBELT).setTextureName(ResourceLib.MINING_TOOLBELT);
        utilityBelt = new ItemMiningToolBelt(utilityBeltId).setUnlocalizedName(ItemLib.UTILITY_BELT).setTextureName(ResourceLib.UTILITY_BELT);

        GameRegistry.registerItem(miningToolBelt,
                                  ItemLib.MINING_TOOLBELT);
        GameRegistry.registerItem(utilityBelt,
                                  ItemLib.UTILITY_BELT);
    }

    private static void registerTools() {
        registerMiningHelmets();
        registerMotionSensor();
    }

    private static void registerMiningHelmets() {
        miningHelmetLamp = new ItemMiningLamp(miningHelmetLampId).setUnlocalizedName(ItemLib.MINING_HELMET_LAMP).setTextureName(ResourceLib.MINING_HELMET_LAMP);
        miningHelmetIron = new ItemMiningHelmet(miningHelmetIronId, ArmorMaterial.IRON, 2, ItemLib.MINING_HELMET_IRON, ResourceLib.MINING_HELMET_IRON);
        miningHelmetGold = new ItemMiningHelmet(miningHelmetGoldId, ArmorMaterial.GOLD, 4, ItemLib.MINING_HELMET_GOLD, ResourceLib.MINING_HELMET_GOLD);
        miningHelmetDiamond = new ItemMiningHelmet(miningHelmetDiamondId, ArmorMaterial.DIAMOND, 3, ItemLib.MINING_HELMET_DIAMOND, ResourceLib.MINING_HELMET_DIAMOND);

        GameRegistry.registerItem(miningHelmetLamp,
                                  ItemLib.MINING_HELMET_LAMP);
        GameRegistry.registerItem(miningHelmetIron,
                                  ItemLib.MINING_HELMET_IRON);
        GameRegistry.registerItem(miningHelmetGold,
                                  ItemLib.MINING_HELMET_GOLD);
        GameRegistry.registerItem(miningHelmetDiamond,
                                  ItemLib.MINING_HELMET_DIAMOND);
    }

    private static void registerMotionSensor() {
        motionSensor = new ItemMotionSensor(motionSensorId).setUnlocalizedName(ItemLib.MOTION_SENSOR).setTextureName(ResourceLib.MOTION_SENSOR);

        GameRegistry.registerItem(motionSensor,
                                  ItemLib.MOTION_SENSOR);
    }

    private static void registerIngots() {
        nuggetAcxium = new ItemMineralNugget(nuggetAcxiumId).setUnlocalizedName(ItemLib.NUGGET_ACXIUM).setTextureName(ResourceLib.NUGGET_ACXIUM);
        nuggetBisogen = new ItemMineralNugget(nuggetBisogenId).setUnlocalizedName(ItemLib.NUGGET_BISOGEN).setTextureName(ResourceLib.NUGGET_BISOGEN);
        nuggetCydrine = new ItemMineralNugget(nuggetCydrineId).setUnlocalizedName(ItemLib.NUGGET_CYDRINE).setTextureName(ResourceLib.NUGGET_CYDRINE);
        ingotAcxium = new ItemMineralIngot(ingotAcxiumId).setUnlocalizedName(ItemLib.INGOT_ACXIUM).setTextureName(ResourceLib.INGOT_ACXIUM);
        ingotBisogen = new ItemMineralIngot(ingotBisogenId).setUnlocalizedName(ItemLib.INGOT_BISOGEN).setTextureName(ResourceLib.INGOT_BISOGEN);
        ingotCydrine = new ItemMineralIngot(ingotCydrineId).setUnlocalizedName(ItemLib.INGOT_CYDRINE).setTextureName(ResourceLib.INGOT_CYDRINE);

        GameRegistry.registerItem(nuggetAcxium,
                                  ItemLib.NUGGET_ACXIUM);
        GameRegistry.registerItem(nuggetBisogen,
                                  ItemLib.NUGGET_BISOGEN);
        GameRegistry.registerItem(nuggetCydrine,
                                  ItemLib.NUGGET_CYDRINE);

        GameRegistry.registerItem(ingotAcxium,
                                  ItemLib.INGOT_ACXIUM);
        GameRegistry.registerItem(ingotBisogen,
                                  ItemLib.INGOT_BISOGEN);
        GameRegistry.registerItem(ingotCydrine,
                                  ItemLib.INGOT_CYDRINE);
    }

    private static void registerMinerals() {
        mineralAcxium = new ItemMineral(mineralAcxiumId).setBurnTime(2400).setUnlocalizedName(ItemLib.MINERAL_ACXIUM).setTextureName(ResourceLib.MINERAL_ACXIUM);
        mineralBisogen = new ItemMineral(mineralBisogenId).setBurnSpeed(150).setUnlocalizedName(ItemLib.MINERAL_BISOGEN).setTextureName(ResourceLib.MINERAL_BISOGEN);
        mineralCydrine = new ItemMineral(mineralCydrineId).setBurnWidth(1).setUnlocalizedName(ItemLib.MINERAL_CYDRINE).setTextureName(ResourceLib.MINERAL_CYDRINE);

        GameRegistry.registerItem(mineralAcxium,
                                  ItemLib.MINERAL_ACXIUM);
        GameRegistry.registerItem(mineralBisogen,
                                  ItemLib.MINERAL_BISOGEN);
        GameRegistry.registerItem(mineralCydrine,
                                  ItemLib.MINERAL_CYDRINE);
    }

    private static void registerDusts() {
        dustAcxium = new ItemMineralDust(dustAcxiumId).setBurnTime(3200).setUnlocalizedName(ItemLib.DUST_ACXIUM).setTextureName(ResourceLib.DUST_AXCIUM);
        dustBisogen = new ItemMineralDust(dustBisogenId).setBurnSpeed(100).setUnlocalizedName(ItemLib.DUST_BISOGEN).setTextureName(ResourceLib.DUST_BISOGEN);
        dustCydrine = new ItemMineralDust(dustCydrineId).setBurnWidth(1).setUnlocalizedName(ItemLib.DUST_CYDRINE).setTextureName(ResourceLib.DUST_CYDRINE);
        dustMixed = new ItemMineralMixedDust(dustMixedId).setUnlocalizedName(ItemLib.DUST_MIXED).setTextureName(ResourceLib.DUST_MIXED);

        GameRegistry.registerItem(dustAcxium,
                                  ItemLib.DUST_ACXIUM);
        GameRegistry.registerItem(dustBisogen,
                                  ItemLib.DUST_BISOGEN);
        GameRegistry.registerItem(dustCydrine,
                                  ItemLib.DUST_CYDRINE);
        GameRegistry.registerItem(dustMixed,
                                  ItemLib.DUST_MIXED);

        ItemMineralMixedDust.script = FileReader.readFile(ResourceLib.DUST_LIB_PATH);
        ItemMineralMixedDust.script_burnTime = "getBurnTime()";
        ItemMineralMixedDust.script_burnSpeed = "getBurnSpeed()";
        ItemMineralMixedDust.script_burnWidth = "getBurnWidth()";

        // TODO :: MixedDustNameRegistry : Move to or something
        MixedDustNameRegistry.addName(1,
                                      1,
                                      0,
                                      "Monoaxogen");
        MixedDustNameRegistry.addName(1,
                                      0,
                                      1,
                                      "Monoaxicyde");
        MixedDustNameRegistry.addName(0,
                                      1,
                                      1,
                                      "Monogencyde");
    }

    private static void registerParts() {
        partAcxiumCore = new ItemMachinePart(partAcxiumCoreId).setUnlocalizedName(ItemLib.PART_ACXIUM_CORE).setTextureName(ResourceLib.PART_ACXIUM_CORE);
        partAcxogenScreen = new ItemMachinePart(partAcxogenScreenId).setUnlocalizedName(ItemLib.PART_ACXOGEN_SCREEN).setTextureName(ResourceLib.PART_ACXOGEN_SCREEN);
        partAlloyCasing = new ItemMachinePart(partAlloyCasingId).setUnlocalizedName(ItemLib.PART_ALLOY_CASING).setTextureName(ResourceLib.PART_ALLOY_CASING);
        partBisogenGear = new ItemMachinePart(partBisogenGearId).setUnlocalizedName(ItemLib.PART_BISOGEN_GEAR).setTextureName(ResourceLib.PART_BISOGEN_GEAR);
        partCydrineMotor = new ItemMachinePart(partCydrineMotorId).setUnlocalizedName(ItemLib.PART_CYDRINE_MOTOR).setTextureName(ResourceLib.PART_CYDRINE_MOTOR);
        partCydriumSensor = new ItemMachinePart(partCydriumSensorId).setUnlocalizedName(ItemLib.PART_CYDRIUM_SENSOR).setTextureName(ResourceLib.PART_CYDRIUM_SENSOR);

        GameRegistry.registerItem(partAcxiumCore,
                                  ItemLib.PART_ACXIUM_CORE);
        GameRegistry.registerItem(partAcxogenScreen,
                                  ItemLib.PART_ACXOGEN_SCREEN);
        GameRegistry.registerItem(partAlloyCasing,
                                  ItemLib.PART_ALLOY_CASING);
        GameRegistry.registerItem(partBisogenGear,
                                  ItemLib.PART_BISOGEN_GEAR);
        GameRegistry.registerItem(partCydrineMotor,
                                  ItemLib.PART_CYDRINE_MOTOR);
        GameRegistry.registerItem(partCydriumSensor,
                                  ItemLib.PART_CYDRIUM_SENSOR);
    }

    // ======== FUEL REGISTRATION ========
    public static void registerFuels() {
        GameRegistry.registerFuelHandler(new MineralFuelHandler());
    }

    // ORES
    public static int       arkiteOreId, bistiteOreId, crokereOreId,
            derniteOreId, egioclaseOreId;
    public static Block     arkiteOre, bistiteOre, crokereOre, derniteOre,
            egioclaseOre;

    // MACHINES
    public static int       blockMachineBaseId;
    public static BlockBase blockMachineBase;

    public static int       blockMiningLampID = 3840;
    public static Block     blockMiningLamp;

    public static void registerMachines() {
        // Mining Lamp Light Block
        blockMiningLamp = new BlockMiningLamp(blockMiningLampID).setLightLevel(1.0F).setBlockName(BlockLib.BLOCK_MINING_LAMP);

        // MACHINE BASE
        blockMachineBase = new BlockMachineBase(blockMachineBaseId);
        GameRegistry.registerBlock(blockMachineBase,
                                   ItemBlockBase.class,
                                   BlockLib.BLOCK_MACHINE_BASE);

        EnumMachine.registerMachines();

        // REFINERY
        JSONLoader.registerJSONLoader(new JSONRefineryRecipesLoader());

        // GRINDER
        JSONLoader.registerJSONLoader(new JSONGrinderRecipesLoader());

        // GEOLOGICAL EQUIPMENT

        // AUTOMATIC MIXING TABLE
    }

    public static void registerOres() {

        BlockLib.init();

        arkiteOre = new BlockTMFOre(arkiteOreId, 128, 20, 5).setBlockName(BlockLib.ORE_ARKITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        bistiteOre = new BlockTMFOre(bistiteOreId, 64, 15, 5).setBlockName(BlockLib.ORE_BISTITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        crokereOre = new BlockTMFOre(crokereOreId, 48, 15, 5).setBlockName(BlockLib.ORE_CROKERE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        derniteOre = new BlockTMFOre(derniteOreId, 26, 10, 5).setBlockName(BlockLib.ORE_DERNITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        egioclaseOre = new BlockTMFOre(egioclaseOreId, 5, 5, 5).setBlockName(BlockLib.ORE_EGIOCLASE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);

        GameRegistry.registerBlock(arkiteOre,
                                   BlockLib.ORE_ARKITE);
        GameRegistry.registerBlock(bistiteOre,
                                   BlockLib.ORE_BISTITE);
        GameRegistry.registerBlock(crokereOre,
                                   BlockLib.ORE_CROKERE);
        GameRegistry.registerBlock(derniteOre,
                                   BlockLib.ORE_DERNITE);
        GameRegistry.registerBlock(egioclaseOre,
                                   BlockLib.ORE_EGIOCLASE);

        GameRegistry.registerWorldGenerator(new WorldGeneration(),
                                            100);
    }

    // ======= RECIPE REGISTRATION =======
    public static void registerRecipes() {
        XMLRecipeLoader.registerDefaultsFromLocation(TMFCore.class,
                                                     ResourceLib.RECIPE_PATH_XML);
        XMLRecipeLoader.loadFolder(ResourceLib.RECIPE_PATH_XML,
                                   new File(ResourceLib.RECIPE_STORE));

        registerArmorRecipes();

        if (ConfigurationLib.loadItems) {
            GameRegistry.addRecipe(new ItemMineralMixedDustRecipe());

            GameRegistry.addSmelting(mineralAcxium,
                                     new ItemStack(nuggetAcxium, 1),
                                     1);
            GameRegistry.addSmelting(mineralBisogen,
                                     new ItemStack(nuggetBisogen, 1),
                                     2);
            GameRegistry.addSmelting(mineralCydrine,
                                     new ItemStack(nuggetCydrine, 1),
                                     3);
        }
    }

    private static void registerArmorRecipes() {
        ArmorRecipes armorRecipes = new ArmorRecipes(new ItemStack(miningHelmetIron), new Object[] {
                new ItemStack(miningHelmetLamp),
                new ItemStack(Items.iron_helmet) });

        GameRegistry.addRecipe(armorRecipes);

        armorRecipes = new ArmorRecipes(new ItemStack(miningHelmetGold), new Object[] {
                new ItemStack(miningHelmetLamp),
                new ItemStack(Items.golden_helmet) });

        GameRegistry.addRecipe(armorRecipes);

        armorRecipes = new ArmorRecipes(new ItemStack(miningHelmetDiamond), new Object[] {
                new ItemStack(miningHelmetLamp),
                new ItemStack(Items.diamond_helmet) });

        GameRegistry.addRecipe(armorRecipes);
    }
}
