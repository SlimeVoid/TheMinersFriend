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
package com.slimevoid.tmf.core.lib;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import com.slimevoid.library.data.Logger;
import com.slimevoid.library.util.xml.XMLLoader;
import com.slimevoid.tmf.blocks.machines.EnumMachine;
import com.slimevoid.tmf.core.LoggerTMF;
import com.slimevoid.tmf.core.TMFCore;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    public static int           renderMachineId                  = RenderingRegistry.getNextAvailableRenderId();
    public static boolean       motionSensorPlaySounds;

    public static float         miningModeExhaustion             = 0.075F;
    public static final String  COMMENT_MOTION_SENSOR_DRAW_RIGHT = "Set this to false to draw the motion sensor on the left.";

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
        TMFCore.motionSensorId = Integer.valueOf(configuration.get(CATEGORY_MOTION_SENSOR,
                                                                   "motionSensor",
                                                                   15003).getInt());
    }

    private static void loadMiningHelmet() {
        TMFCore.blockMiningLampID = Integer.valueOf(configuration.get(CATEGORY_BLOCK,
                                                                      BlockLib.BLOCK_MINING_LAMP,
                                                                      TMFCore.blockMiningLampID).getInt());
        TMFCore.miningHelmetIronId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "ironMinersHelmet",
                                                                       15000).getInt());
        TMFCore.miningHelmetGoldId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "goldMinersHelmet",
                                                                       15001).getInt());
        TMFCore.miningHelmetDiamondId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                          "diamondMinersHelmet",
                                                                          15002).getInt());
        TMFCore.miningHelmetLampId = Integer.valueOf(configuration.get(CATEGORY_ARMOR,
                                                                       "helmetLamp",
                                                                       15004).getInt());
    }

    private static void loadToolBelt() {
        TMFCore.miningToolBeltId = Integer.valueOf(configuration.get(CATEGORY_ITEM,
                                                                     "toolBelt",
                                                                     15005).getInt());
        TMFCore.utilityBeltId = Integer.valueOf(configuration.get(CATEGORY_ITEM,
                                                                  "utilityBelt",
                                                                  15006).getInt());
        miningModeExhaustion = Float.parseFloat(configuration.get(CATEGORY_LAUNCH_OPTIONS,
                                                                  "miningModeExhaustion",
                                                                  miningModeExhaustion).getString());
    }

    private static void loadMinerals() {
        TMFCore.mineralAcxiumId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                    "mineralAcxium",
                                                                    15010).getInt());
        TMFCore.mineralBisogenId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                     "mineralBisogen",
                                                                     15011).getInt());
        TMFCore.mineralCydrineId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                     "mineralCydrine",
                                                                     15012).getInt());
    }

    private static void loadIngots() {
        TMFCore.nuggetAcxiumId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "nuggetAcxium",
                                                                   15013).getInt());
        TMFCore.nuggetBisogenId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                    "nuggetBisogen",
                                                                    15014).getInt());
        TMFCore.nuggetCydrineId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                    "nuggetCydrine",
                                                                    15015).getInt());

        TMFCore.ingotAcxiumId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                  "ingotAcxium",
                                                                  15016).getInt());
        TMFCore.ingotBisogenId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "ingotBisogen",
                                                                   15017).getInt());
        TMFCore.ingotCydrineId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "ingotCydrine",
                                                                   15018).getInt());
    }

    private static void loadParts() {
        TMFCore.partAcxiumCoreId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                     "partAcxiumCore",
                                                                     15025).getInt());
        TMFCore.partAcxogenScreenId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                        "partAcxogenScreen",
                                                                        15026).getInt());
        TMFCore.partAlloyCasingId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                      "partAlloyCasing",
                                                                      15027).getInt());
        TMFCore.partBisogenGearId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                      "partBisogenGear",
                                                                      15028).getInt());
        TMFCore.partCydrineMotorId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                       "partCydrineMotor",
                                                                       15029).getInt());
        TMFCore.partCydriumSensorId = Integer.valueOf(configuration.get(CATEGORY_PARTS,
                                                                        "partCydriumSensor",
                                                                        15030).getInt());
    }

    private static void loadDusts() {
        TMFCore.dustAcxiumId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                 "dustAcxium",
                                                                 15020).getInt());
        TMFCore.dustBisogenId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                  "dustBisogen",
                                                                  15021).getInt());
        TMFCore.dustCydrineId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                  "dustCydrine",
                                                                  15022).getInt());
        TMFCore.dustMixedId = Integer.valueOf(configuration.get(CATEGORY_FUEL,
                                                                "dustMixed",
                                                                15023).getInt());
    }

    private static void loadOres() {
        TMFCore.arkiteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                "arkiteOre",
                                                                3515).getInt());
        TMFCore.bistiteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "bistiteOre",
                                                                 3516).getInt());
        TMFCore.crokereOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "crokereOre",
                                                                 3517).getInt());
        TMFCore.derniteOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                 "derniteOre",
                                                                 3518).getInt());
        TMFCore.egioclaseOreId = Integer.valueOf(configuration.get(CATEGORY_ORES,
                                                                   "egioclaseOre",
                                                                   3519).getInt());
    }

    private static void loadMachines() {
        TMFCore.blockMachineBaseId = Integer.valueOf(configuration.get(CATEGORY_MACHINES,
                                                                       "blockMachine",
                                                                       1100).getInt());

    }

    private static void loadLogger() {
        TMFCore.loggerLevel = String.valueOf(configuration.get(Configuration.CATEGORY_GENERAL,
                                                               "loggerLevel",
                                                               TMFCore.loggerLevel).getString());

        LoggerTMF.getInstance(Logger.filterClassName(TMFCore.class.toString())).setFilterLevel(TMFCore.loggerLevel);
    }

    public static void loadXMLVariables() {

        XMLLoader.addXmlVariable("$miningToolBelt",
                                 Item.getIdFromItem(TMFCore.miningToolBelt));

        XMLLoader.addXmlVariable("$utilityBelt",
                                 Item.getIdFromItem(TMFCore.utilityBelt));

        if (loadMachines) {
            /* MACHINES */

            XMLLoader.addXmlVariable("$machine",
                                     Block.getIdFromBlock(TMFCore.blockMachineBase));
            XMLLoader.addXmlVariable("$refinery",
                                     EnumMachine.REFINERY.getId());
            XMLLoader.addXmlVariable("$grinder",
                                     EnumMachine.GRINDER.getId());
            XMLLoader.addXmlVariable("$geoEquip",
                                     EnumMachine.GEOEQUIP.getId());
            XMLLoader.addXmlVariable("$autoMixTable",
                                     EnumMachine.AUTOMIXTABLE.getId());
        }

        if (loadOres) {
            /* ORES */

            XMLLoader.addXmlVariable("$arkiteOre",
                                     Block.getIdFromBlock(TMFCore.arkiteOre));
            XMLLoader.addXmlVariable("$bistiteOre",
                                     Block.getIdFromBlock(TMFCore.bistiteOre));
            XMLLoader.addXmlVariable("$crokereOre",
                                     Block.getIdFromBlock(TMFCore.crokereOre));
            XMLLoader.addXmlVariable("$derniteOre",
                                     Block.getIdFromBlock(TMFCore.derniteOre));
            XMLLoader.addXmlVariable("$egioclaseOre",
                                     Block.getIdFromBlock(TMFCore.egioclaseOre));
        }

        if (loadItems) {
            /* MINERALS */

            XMLLoader.addXmlVariable("$mineralAcxium",
                                     Item.getIdFromItem(TMFCore.mineralAcxium));
            XMLLoader.addXmlVariable("$mineralBisogen",
                                     Item.getIdFromItem(TMFCore.mineralBisogen));
            XMLLoader.addXmlVariable("$mineralCydrine",
                                     Item.getIdFromItem(TMFCore.mineralCydrine));

            /* INGOTS */

            XMLLoader.addXmlVariable("$nuggetAcxium",
                                     Item.getIdFromItem(TMFCore.nuggetAcxium));
            XMLLoader.addXmlVariable("$nuggetBisogen",
                                     Item.getIdFromItem(TMFCore.nuggetBisogen));
            XMLLoader.addXmlVariable("$nuggetCydrine",
                                     Item.getIdFromItem(TMFCore.nuggetCydrine));

            XMLLoader.addXmlVariable("$ingotAcxium",
                                     Item.getIdFromItem(TMFCore.ingotAcxium));
            XMLLoader.addXmlVariable("$ingotBisogen",
                                     Item.getIdFromItem(TMFCore.ingotBisogen));
            XMLLoader.addXmlVariable("$ingotCydrine",
                                     Item.getIdFromItem(TMFCore.ingotCydrine));

            /* PARTS */

            XMLLoader.addXmlVariable("$partAcxiumCore",
                                     Item.getIdFromItem(TMFCore.partAcxiumCore));
            XMLLoader.addXmlVariable("$partAlloyCasing",
                                     Item.getIdFromItem(TMFCore.partAlloyCasing));
            XMLLoader.addXmlVariable("$partAcxogenScreen",
                                     Item.getIdFromItem(TMFCore.partAcxogenScreen));
            XMLLoader.addXmlVariable("$partBisogenGear",
                                     Item.getIdFromItem(TMFCore.partBisogenGear));
            XMLLoader.addXmlVariable("$partCydrineMotor",
                                     Item.getIdFromItem(TMFCore.partCydrineMotor));
            XMLLoader.addXmlVariable("$partCydriumSensor",
                                     Item.getIdFromItem(TMFCore.partCydriumSensor));

            /* DUSTS */

            XMLLoader.addXmlVariable("$dustAcxium",
                                     Item.getIdFromItem(TMFCore.dustAcxium));
            XMLLoader.addXmlVariable("$dustBisogen",
                                     Item.getIdFromItem(TMFCore.dustBisogen));
            XMLLoader.addXmlVariable("$dustCydrine",
                                     Item.getIdFromItem(TMFCore.dustCydrine));
            XMLLoader.addXmlVariable("$dustMixed",
                                     Item.getIdFromItem(TMFCore.dustMixed));

            /* TOOLS */

            XMLLoader.addXmlVariable("$ironMinersHelmet",
                                     Item.getIdFromItem(TMFCore.miningHelmetIron));
            XMLLoader.addXmlVariable("$goldMinersHelmet",
                                     Item.getIdFromItem(TMFCore.miningHelmetGold));
            XMLLoader.addXmlVariable("$diamondMinersHelmet",
                                     Item.getIdFromItem(TMFCore.miningHelmetDiamond));
            XMLLoader.addXmlVariable("$helmetLamp",
                                     Item.getIdFromItem(TMFCore.miningHelmetLamp));
            XMLLoader.addXmlVariable("$helmetLamp",
                                     Item.getIdFromItem(TMFCore.miningHelmetLamp));

            XMLLoader.addXmlVariable("$motionSensor",
                                     Item.getIdFromItem(TMFCore.motionSensor));
        }

        /* VANILLA PARTS */
        /* Loaded via Slimevoid Library */
    }
}
