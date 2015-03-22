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
package net.slimevoid.tmf.core.lib;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.library.data.Logger;
import net.slimevoid.library.util.xml.XMLLoader;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.core.LoggerTMF;
import net.slimevoid.tmf.core.TMFCore;
import net.slimevoid.tmf.items.tools.ItemMiningArmor;

public class ConfigurationLib {

    // CONFIG
    public static Configuration configuration;

    private static final String CATEGORY_BLOCK                   = "block";
    private static final String CATEGORY_ITEM                    = "item";
    private final static String CATEGORY_LAUNCH_OPTIONS          = "launch options";
    private static final String CATEGORY_MOTION_SENSOR           = "motion sensor";
    private static final String CATEGORY_MACHINES                = "machines";
    private static final String CATEGORY_ORES                    = "ores";
    private static final String CATEGORY_FUEL                    = "fuel";
    private static final String CATEGORY_ARMOR                   = "armor";
    private static final String CATEGORY_PARTS                   = "parts";

    public static boolean       loadItems                        = true;
    public static boolean       loadOres                         = true;
    public static boolean       loadMachines                     = true;

    @SideOnly(Side.CLIENT)
    public static int           motionSensorMaxEntityDistance;
    @SideOnly(Side.CLIENT)
    public static int           motionSensorMaxGameTicks;
    @SideOnly(Side.CLIENT)
    public static boolean       motionSensorDrawRight;
    public static int           renderMachineId                  = 0;
    public static boolean       motionSensorPlaySounds;

    public static float         miningModeExhaustion             = 0.075F;

    public static final String  COMMENT_MOTION_SENSOR_DRAW_RIGHT = "Set this to false to draw the motion sensor on the left.";

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

    @SideOnly(Side.CLIENT)
    public static void ClientConfig() {
        configuration.load();

        loadMotionSensorClient();

        configuration.save();
    }

    public static void CommonConfig(File configFile) {
        configuration = new Configuration(configFile);

        configuration.load();

        loadDefaults();

        loadLogger();

        loadToolBelt();

        if (loadItems) {
            loadMotionSensorCommon();
            loadMiningHelmet();
        }
        if (loadOres) {
            loadMinerals();
            loadIngots();
            loadParts();
            loadDusts();
            loadOres();
        }
        if (loadMachines) {
            loadMachines();
        }

        configuration.save();
    }

    private static void loadDefaults() {
        loadItems = configuration.get(CATEGORY_LAUNCH_OPTIONS,
                                      "shouldLoadItems",
                                      loadItems).getBoolean(loadItems);
        loadOres = configuration.get(CATEGORY_LAUNCH_OPTIONS,
                                     "shouldLoadOres",
                                     loadOres).getBoolean(loadOres);
        loadMachines = configuration.get(CATEGORY_LAUNCH_OPTIONS,
                                         "shouldLoadMachines",
                                         loadMachines).getBoolean(loadMachines);
    }

    private static void loadMotionSensorClient() {
        motionSensorDrawRight = configuration.get(CATEGORY_MOTION_SENSOR,
                                                  "drawonright",
                                                  motionSensorDrawRight,
                                                  COMMENT_MOTION_SENSOR_DRAW_RIGHT).getBoolean(motionSensorDrawRight);

        motionSensorMaxEntityDistance = 20;
        motionSensorMaxGameTicks = 40;
        motionSensorDrawRight = true;
        motionSensorPlaySounds = configuration.get(CATEGORY_MOTION_SENSOR,
                                                   "playsound",
                                                   true).getBoolean(true);
    }

    private static void loadMotionSensorCommon() {
        motionSensorId = Integer.valueOf(configuration.get(CATEGORY_MOTION_SENSOR,
                                                                   "motionSensor",
                                                                   15003).getInt());
    }

    private static void loadMiningHelmet() {
        blockMiningLampID = Integer.valueOf(configuration.get(CATEGORY_BLOCK,
                                                                      BlockLib.BLOCK_MINING_LAMP,
                                                                      blockMiningLampID).getInt());
        miningHelmetIronId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "ironMinersHelmet",
                                                                       15000).getInt());
        miningHelmetGoldId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "goldMinersHelmet",
                                                                       15001).getInt());
        miningHelmetDiamondId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                          "diamondMinersHelmet",
                                                                          15002).getInt());
        miningHelmetLampId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "helmetLamp",
                                                                       15004).getInt());
    }

    private static void loadToolBelt() {
        miningToolBeltId = Integer.valueOf(configuration.get(CATEGORY_ITEM,
                                                                     "toolBelt",
                                                                     15005).getInt());
        utilityBeltId = Integer.valueOf(configuration.get(CATEGORY_ITEM,
                                                                  "utilityBelt",
                                                                  15006).getInt());
        miningModeExhaustion = Float.parseFloat(configuration.get(CATEGORY_LAUNCH_OPTIONS,
                                                                  "miningModeExhaustion",
                                                                  miningModeExhaustion).getString());
    }

    private static void loadMinerals() {
        mineralAcxiumId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                    "mineralAcxium",
                                                                    15010).getInt());
        mineralBisogenId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                     "mineralBisogen",
                                                                     15011).getInt());
        mineralCydrineId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                     "mineralCydrine",
                                                                     15012).getInt());
    }

    private static void loadIngots() {
        nuggetAcxiumId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "nuggetAcxium",
                                                                   15013).getInt());
        nuggetBisogenId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                    "nuggetBisogen",
                                                                    15014).getInt());
        nuggetCydrineId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                    "nuggetCydrine",
                                                                    15015).getInt());

        ingotAcxiumId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                  "ingotAcxium",
                                                                  15016).getInt());
        ingotBisogenId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "ingotBisogen",
                                                                   15017).getInt());
        ingotCydrineId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "ingotCydrine",
                                                                   15018).getInt());
    }

    private static void loadParts() {
        partAcxiumCoreId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                     "partAcxiumCore",
                                                                     15025).getInt());
        partAcxogenScreenId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                        "partAcxogenScreen",
                                                                        15026).getInt());
        partAlloyCasingId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                      "partAlloyCasing",
                                                                      15027).getInt());
        partBisogenGearId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                      "partBisogenGear",
                                                                      15028).getInt());
        partCydrineMotorId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                       "partCydrineMotor",
                                                                       15029).getInt());
        partCydriumSensorId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                        "partCydriumSensor",
                                                                        15030).getInt());
    }

    private static void loadDusts() {
        dustAcxiumId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                 "dustAcxium",
                                                                 15020).getInt());
        dustBisogenId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                  "dustBisogen",
                                                                  15021).getInt());
        dustCydrineId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                  "dustCydrine",
                                                                  15022).getInt());
        dustMixedId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                "dustMixed",
                                                                15023).getInt());
    }

    private static void loadOres() {
        arkiteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                "arkiteOre",
                                                                3515).getInt());
        bistiteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "bistiteOre",
                                                                 3516).getInt());
        crokereOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "crokereOre",
                                                                 3517).getInt());
        derniteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "derniteOre",
                                                                 3518).getInt());
        egioclaseOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "egioclaseOre",
                                                                   3519).getInt());
    }

    private static void loadMachines() {
        blockMachineBaseId = Integer.valueOf(configuration.get(CATEGORY_MACHINES,
                                                                       "blockMachine",
                                                                       1100).getInt());

    }

    private static void loadLogger() {
        loggerLevel = String.valueOf(configuration.get(Configuration.CATEGORY_GENERAL,
                                                               "loggerLevel",
                                                               loggerLevel).getString());

        LoggerTMF.getInstance(Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(loggerLevel);
    }

    public static void loadXMLVariables() {

        XMLLoader.addXmlVariable("$miningToolBelt",
                                 Item.getIdFromItem(miningToolBelt));

        XMLLoader.addXmlVariable("$utilityBelt",
                                 Item.getIdFromItem(utilityBelt));

        if (loadMachines) {
            /* MACHINES */

            XMLLoader.addXmlVariable("$machine",
                                     Block.getIdFromBlock(blockMachineBase));
            XMLLoader.addXmlVariable("$refinery",
                                     BlockTypeMachine.REFINERY.getId());
            XMLLoader.addXmlVariable("$grinder",
                                     BlockTypeMachine.GRINDER.getId());
            XMLLoader.addXmlVariable("$geoEquip",
                                     BlockTypeMachine.GEOEQUIP.getId());
            XMLLoader.addXmlVariable("$autoMixTable",
                                     BlockTypeMachine.AUTOMIXTABLE.getId());
        }

        if (loadOres) {
            /* ORES */

            XMLLoader.addXmlVariable("$arkiteOre",
                                     Block.getIdFromBlock(arkiteOre));
            XMLLoader.addXmlVariable("$bistiteOre",
                                     Block.getIdFromBlock(bistiteOre));
            XMLLoader.addXmlVariable("$crokereOre",
                                     Block.getIdFromBlock(crokereOre));
            XMLLoader.addXmlVariable("$derniteOre",
                                     Block.getIdFromBlock(derniteOre));
            XMLLoader.addXmlVariable("$egioclaseOre",
                                     Block.getIdFromBlock(egioclaseOre));
        }

        if (loadItems) {
            /* MINERALS */

            XMLLoader.addXmlVariable("$mineralAcxium",
                                     Item.getIdFromItem(mineralAcxium));
            XMLLoader.addXmlVariable("$mineralBisogen",
                                     Item.getIdFromItem(mineralBisogen));
            XMLLoader.addXmlVariable("$mineralCydrine",
                                     Item.getIdFromItem(mineralCydrine));

            /* INGOTS */

            XMLLoader.addXmlVariable("$nuggetAcxium",
                                     Item.getIdFromItem(nuggetAcxium));
            XMLLoader.addXmlVariable("$nuggetBisogen",
                                     Item.getIdFromItem(nuggetBisogen));
            XMLLoader.addXmlVariable("$nuggetCydrine",
                                     Item.getIdFromItem(nuggetCydrine));

            XMLLoader.addXmlVariable("$ingotAcxium",
                                     Item.getIdFromItem(ingotAcxium));
            XMLLoader.addXmlVariable("$ingotBisogen",
                                     Item.getIdFromItem(ingotBisogen));
            XMLLoader.addXmlVariable("$ingotCydrine",
                                     Item.getIdFromItem(ingotCydrine));

            /* PARTS */

            XMLLoader.addXmlVariable("$partAcxiumCore",
                                     Item.getIdFromItem(partAcxiumCore));
            XMLLoader.addXmlVariable("$partAlloyCasing",
                                     Item.getIdFromItem(partAlloyCasing));
            XMLLoader.addXmlVariable("$partAcxogenScreen",
                                     Item.getIdFromItem(partAcxogenScreen));
            XMLLoader.addXmlVariable("$partBisogenGear",
                                     Item.getIdFromItem(partBisogenGear));
            XMLLoader.addXmlVariable("$partCydrineMotor",
                                     Item.getIdFromItem(partCydrineMotor));
            XMLLoader.addXmlVariable("$partCydriumSensor",
                                     Item.getIdFromItem(partCydriumSensor));

            /* DUSTS */

            XMLLoader.addXmlVariable("$dustAcxium",
                                     Item.getIdFromItem(dustAcxium));
            XMLLoader.addXmlVariable("$dustBisogen",
                                     Item.getIdFromItem(dustBisogen));
            XMLLoader.addXmlVariable("$dustCydrine",
                                     Item.getIdFromItem(dustCydrine));
            XMLLoader.addXmlVariable("$dustMixed",
                                     Item.getIdFromItem(dustMixed));

            /* TOOLS */

            XMLLoader.addXmlVariable("$ironMinersHelmet",
                                     Item.getIdFromItem(miningHelmetIron));
            XMLLoader.addXmlVariable("$goldMinersHelmet",
                                     Item.getIdFromItem(miningHelmetGold));
            XMLLoader.addXmlVariable("$diamondMinersHelmet",
                                     Item.getIdFromItem(miningHelmetDiamond));
            XMLLoader.addXmlVariable("$helmetLamp",
                                     Item.getIdFromItem(miningHelmetLamp));
            XMLLoader.addXmlVariable("$helmetLamp",
                                     Item.getIdFromItem(miningHelmetLamp));

            XMLLoader.addXmlVariable("$motionSensor",
                                     Item.getIdFromItem(motionSensor));
        }

        /* VANILLA PARTS */
        /* Loaded via Slimevoid Library */
    }
}
