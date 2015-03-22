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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slimevoid.compatibility.TMFCompatibility;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.items.ItemBlockBase;
import net.slimevoid.library.util.FileReader;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.library.util.json.JSONLoader;
import net.slimevoid.library.util.xml.XMLRecipeLoader;
import net.slimevoid.tmf.blocks.BlockMiningLamp;
import net.slimevoid.tmf.blocks.machines.BlockMachineBase;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.blocks.machines.recipes.JSONGrinderRecipesLoader;
import net.slimevoid.tmf.blocks.machines.recipes.JSONRefineryRecipesLoader;
import net.slimevoid.tmf.blocks.ores.BlockTMFOre;
import net.slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import net.slimevoid.tmf.core.lib.*;
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
import net.slimevoid.tmf.items.tools.ItemMiningHelmet;
import net.slimevoid.tmf.items.tools.ItemMiningLamp;
import net.slimevoid.tmf.items.tools.ItemMiningToolBelt;
import net.slimevoid.tmf.items.tools.ItemMotionSensor;
import net.slimevoid.tmf.items.tools.recipes.ArmorRecipes;

public class TMFCore {

    // ======== ITEM REGISTRATION ========
    public static void registerItems() {
        registerTools();
        registerMinerals();
        registerIngots();
        registerParts();
        registerDusts();
    }

    public static void registerToolBelt() {
        ConfigurationLib.miningToolBelt = new ItemMiningToolBelt().setUnlocalizedName(ItemLib.MINING_TOOLBELT);
        ConfigurationLib.utilityBelt = new ItemMiningToolBelt().setUnlocalizedName(ItemLib.UTILITY_BELT);

        GameRegistry.registerItem(ConfigurationLib.miningToolBelt,
                ItemLib.MINING_TOOLBELT);
        GameRegistry.registerItem(ConfigurationLib.utilityBelt,
                                  ItemLib.UTILITY_BELT);
    }

    private static void registerTools() {
        registerMiningHelmets();
        registerMotionSensor();
    }

    private static void registerMiningHelmets() {
        ConfigurationLib.miningHelmetLamp = new ItemMiningLamp().setUnlocalizedName(ItemLib.MINING_HELMET_LAMP);
        ConfigurationLib.miningHelmetIron = new ItemMiningHelmet(ConfigurationLib.miningHelmetIronId, ArmorMaterial.IRON, 2, ItemLib.MINING_HELMET_IRON, ResourceLib.MINING_HELMET_IRON);
        ConfigurationLib.miningHelmetGold = new ItemMiningHelmet(ConfigurationLib.miningHelmetGoldId, ArmorMaterial.GOLD, 4, ItemLib.MINING_HELMET_GOLD, ResourceLib.MINING_HELMET_GOLD);
        ConfigurationLib.miningHelmetDiamond = new ItemMiningHelmet(ConfigurationLib.miningHelmetDiamondId, ArmorMaterial.DIAMOND, 3, ItemLib.MINING_HELMET_DIAMOND, ResourceLib.MINING_HELMET_DIAMOND);

        GameRegistry.registerItem(ConfigurationLib.miningHelmetLamp,
                                  ItemLib.MINING_HELMET_LAMP);
        GameRegistry.registerItem(ConfigurationLib.miningHelmetIron,
                                  ItemLib.MINING_HELMET_IRON);
        GameRegistry.registerItem(ConfigurationLib.miningHelmetGold,
                                  ItemLib.MINING_HELMET_GOLD);
        GameRegistry.registerItem(ConfigurationLib.miningHelmetDiamond,
                                  ItemLib.MINING_HELMET_DIAMOND);
    }

    private static void registerMotionSensor() {
        ConfigurationLib.motionSensor = new ItemMotionSensor().setUnlocalizedName(ItemLib.MOTION_SENSOR);

        GameRegistry.registerItem(ConfigurationLib.motionSensor,
                                  ItemLib.MOTION_SENSOR);
    }

    private static void registerIngots() {
        ConfigurationLib.nuggetAcxium = new ItemMineralNugget().setUnlocalizedName(ItemLib.NUGGET_ACXIUM);
        ConfigurationLib.nuggetBisogen = new ItemMineralNugget().setUnlocalizedName(ItemLib.NUGGET_BISOGEN);
        ConfigurationLib.nuggetCydrine = new ItemMineralNugget().setUnlocalizedName(ItemLib.NUGGET_CYDRINE);
        ConfigurationLib.ingotAcxium = new ItemMineralIngot().setUnlocalizedName(ItemLib.INGOT_ACXIUM);
        ConfigurationLib.ingotBisogen = new ItemMineralIngot().setUnlocalizedName(ItemLib.INGOT_BISOGEN);
        ConfigurationLib.ingotCydrine = new ItemMineralIngot().setUnlocalizedName(ItemLib.INGOT_CYDRINE);

        GameRegistry.registerItem(ConfigurationLib.nuggetAcxium,
                                  ItemLib.NUGGET_ACXIUM);
        GameRegistry.registerItem(ConfigurationLib.nuggetBisogen,
                                  ItemLib.NUGGET_BISOGEN);
        GameRegistry.registerItem(ConfigurationLib.nuggetCydrine,
                                  ItemLib.NUGGET_CYDRINE);

        GameRegistry.registerItem(ConfigurationLib.ingotAcxium,
                                  ItemLib.INGOT_ACXIUM);
        GameRegistry.registerItem(ConfigurationLib.ingotBisogen,
                                  ItemLib.INGOT_BISOGEN);
        GameRegistry.registerItem(ConfigurationLib.ingotCydrine,
                                  ItemLib.INGOT_CYDRINE);
    }

    private static void registerMinerals() {
        ConfigurationLib.mineralAcxium = new ItemMineral().setBurnTime(2400).setUnlocalizedName(ItemLib.MINERAL_ACXIUM);
        ConfigurationLib.mineralBisogen = new ItemMineral().setBurnSpeed(150).setUnlocalizedName(ItemLib.MINERAL_BISOGEN);
        ConfigurationLib.mineralCydrine = new ItemMineral().setBurnWidth(1).setUnlocalizedName(ItemLib.MINERAL_CYDRINE);

        GameRegistry.registerItem(ConfigurationLib.mineralAcxium,
                                  ItemLib.MINERAL_ACXIUM);
        GameRegistry.registerItem(ConfigurationLib.mineralBisogen,
                                  ItemLib.MINERAL_BISOGEN);
        GameRegistry.registerItem(ConfigurationLib.mineralCydrine,
                                  ItemLib.MINERAL_CYDRINE);
    }

    private static void registerDusts() {
        ConfigurationLib.dustAcxium = new ItemMineralDust().setBurnTime(3200).setUnlocalizedName(ItemLib.DUST_ACXIUM);
        ConfigurationLib.dustBisogen = new ItemMineralDust().setBurnSpeed(100).setUnlocalizedName(ItemLib.DUST_BISOGEN);
        ConfigurationLib.dustCydrine = new ItemMineralDust().setBurnWidth(1).setUnlocalizedName(ItemLib.DUST_CYDRINE);
        ConfigurationLib.dustMixed = new ItemMineralMixedDust().setUnlocalizedName(ItemLib.DUST_MIXED);

        GameRegistry.registerItem(ConfigurationLib.dustAcxium,
                                  ItemLib.DUST_ACXIUM);
        GameRegistry.registerItem(ConfigurationLib.dustBisogen,
                                  ItemLib.DUST_BISOGEN);
        GameRegistry.registerItem(ConfigurationLib.dustCydrine,
                                  ItemLib.DUST_CYDRINE);
        GameRegistry.registerItem(ConfigurationLib.dustMixed,
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
        ConfigurationLib.partAcxiumCore = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_ACXIUM_CORE);
        ConfigurationLib.partAcxogenScreen = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_ACXOGEN_SCREEN);
        ConfigurationLib.partAlloyCasing = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_ALLOY_CASING);
        ConfigurationLib.partBisogenGear = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_BISOGEN_GEAR);
        ConfigurationLib.partCydrineMotor = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_CYDRINE_MOTOR);
        ConfigurationLib.partCydriumSensor = new ItemMachinePart().setUnlocalizedName(ItemLib.PART_CYDRIUM_SENSOR);

        GameRegistry.registerItem(ConfigurationLib.partAcxiumCore,
                                  ItemLib.PART_ACXIUM_CORE);
        GameRegistry.registerItem(ConfigurationLib.partAcxogenScreen,
                                  ItemLib.PART_ACXOGEN_SCREEN);
        GameRegistry.registerItem(ConfigurationLib.partAlloyCasing,
                                  ItemLib.PART_ALLOY_CASING);
        GameRegistry.registerItem(ConfigurationLib.partBisogenGear,
                                  ItemLib.PART_BISOGEN_GEAR);
        GameRegistry.registerItem(ConfigurationLib.partCydrineMotor,
                                  ItemLib.PART_CYDRINE_MOTOR);
        GameRegistry.registerItem(ConfigurationLib.partCydriumSensor,
                                  ItemLib.PART_CYDRIUM_SENSOR);
    }

    // ======== FUEL REGISTRATION ========
    public static void registerFuels() {
        GameRegistry.registerFuelHandler(new MineralFuelHandler());
    }

    public static void registerMachines() {
        // Mining Lamp Light Block
        ConfigurationLib.blockMiningLamp = new BlockMiningLamp().setLightLevel(1.0F).setUnlocalizedName(BlockLib.BLOCK_MINING_LAMP);

        // MACHINE BASE
        ConfigurationLib.blockMachineBase = new BlockMachineBase();
        GameRegistry.registerBlock(ConfigurationLib.blockMachineBase,
                                   ItemBlockBase.class,
                                   BlockLib.BLOCK_MACHINE_BASE);

        BlockTypeMachine.registerMachines();
    }

    public static void registerOres() {

        BlockLib.init();

        ConfigurationLib.arkiteOre = new BlockTMFOre(128, 20, 5).setUnlocalizedName(BlockLib.ORE_ARKITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        ConfigurationLib.bistiteOre = new BlockTMFOre(64, 15, 5).setUnlocalizedName(BlockLib.ORE_BISTITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        ConfigurationLib.crokereOre = new BlockTMFOre(48, 15, 5).setUnlocalizedName(BlockLib.ORE_CROKERE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        ConfigurationLib.derniteOre = new BlockTMFOre(26, 10, 5).setUnlocalizedName(BlockLib.ORE_DERNITE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);
        ConfigurationLib.egioclaseOre = new BlockTMFOre(5, 5, 5).setUnlocalizedName(BlockLib.ORE_EGIOCLASE).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setCreativeTab(CreativeTabTMF.tabTMF);

        GameRegistry.registerBlock(ConfigurationLib.arkiteOre,
                                   BlockLib.ORE_ARKITE);
        GameRegistry.registerBlock(ConfigurationLib.bistiteOre,
                                   BlockLib.ORE_BISTITE);
        GameRegistry.registerBlock(ConfigurationLib.crokereOre,
                                   BlockLib.ORE_CROKERE);
        GameRegistry.registerBlock(ConfigurationLib.derniteOre,
                                   BlockLib.ORE_DERNITE);
        GameRegistry.registerBlock(ConfigurationLib.egioclaseOre,
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

            GameRegistry.addSmelting(ConfigurationLib.mineralAcxium,
                                     new ItemStack(ConfigurationLib.nuggetAcxium, 1),
                                     1);
            GameRegistry.addSmelting(ConfigurationLib.mineralBisogen,
                                     new ItemStack(ConfigurationLib.nuggetBisogen, 1),
                                     2);
            GameRegistry.addSmelting(ConfigurationLib.mineralCydrine,
                                     new ItemStack(ConfigurationLib.nuggetCydrine, 1),
                                     3);
        }

        // REFINERY
        JSONLoader.registerJSONLoader(new JSONRefineryRecipesLoader());

        // GRINDER
        JSONLoader.registerJSONLoader(new JSONGrinderRecipesLoader());

        // GEOLOGICAL EQUIPMENT

        // AUTOMATIC MIXING TABLE
    }

    private static void registerArmorRecipes() {
        ArmorRecipes armorRecipes = new ArmorRecipes(new ItemStack(ConfigurationLib.miningHelmetIron), new Object[] {
                new ItemStack(ConfigurationLib.miningHelmetLamp),
                new ItemStack(Items.iron_helmet) });

        GameRegistry.addRecipe(armorRecipes);

        armorRecipes = new ArmorRecipes(new ItemStack(ConfigurationLib.miningHelmetGold), new Object[] {
                new ItemStack(ConfigurationLib.miningHelmetLamp),
                new ItemStack(Items.golden_helmet) });

        GameRegistry.addRecipe(armorRecipes);

        armorRecipes = new ArmorRecipes(new ItemStack(ConfigurationLib.miningHelmetDiamond), new Object[] {
                new ItemStack(ConfigurationLib.miningHelmetLamp),
                new ItemStack(Items.diamond_helmet) });

        GameRegistry.addRecipe(armorRecipes);
    }
    private static boolean initialized = false;

    public static void preInitialize() {
        if (initialized) {
            return;
        }
        PacketHelper.registerHandler();
        TheMinersFriend.proxy.preInit();

        SlimevoidCore.console(CoreLib.MOD_ID,
                "Registering Miner's Tool Belt...");
        TMFCore.registerToolBelt();

        if (ConfigurationLib.loadItems) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                    "Registering items...");
            TMFCore.registerItems();
        }

        if (ConfigurationLib.loadOres) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                    "Registering ores...");
            TMFCore.registerOres();
        }

        if (ConfigurationLib.loadMachines) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                    "Registering machines...");
            TMFCore.registerMachines();
        }

        TheMinersFriend.proxy.registerRenderInformation();
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        TheMinersFriend.proxy.init();

        TheMinersFriend.proxy.registerTickHandlers();

        SlimevoidCore.console(CoreLib.MOD_ID,
                "Registering Compatibility Blocks and Items...");

        TMFCompatibility.registerBlockAndItemInformation();

        SlimevoidCore.console(CoreLib.MOD_ID,
                "Registering XML variables...");

        ConfigurationLib.loadXMLVariables();

        SlimevoidCore.console(CoreLib.MOD_ID,
                "Registering recipes...");
        TMFCore.registerRecipes();

        SlimevoidCore.console(CoreLib.MOD_ID,
                "Registering fuels...");
        TMFCore.registerFuels();
    }

    public static void postInitialize() {
        if (initialized) {
            return;
        }
        TheMinersFriend.proxy.postInit();

        TheMinersFriend.proxy.registerEventHandlers();
    }
}
