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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.slimevoid.tmf.core.TheMinersFriend;

public class ResourceLib {

    private static final String ASSET_PREFIX = "assets/"
            + CoreLib.MOD_ID
            + "/";

    private static final String TEXTURE_PREFIX = CoreLib.MOD_ID
            + ":";
    private static final String PATH_PREFIX = "textures/";

    // public static final String BLOCK_TEXTURE_PATH = PATH_PREFIX +
    // "terrain.png";

    // public static final String RESOURCE_SPRITE_PATH = PATH_PREFIX +
    // "resources/resources.png";

    private static final String ARMOR_PREFIX = PATH_PREFIX
            + "armor/";

    public static final String IRON_MINING_HELMET = ARMOR_PREFIX
            + "tmfiron.png";
    public static final String GOLD_MINING_HELMET = ARMOR_PREFIX
            + "tmfgold.png";
    public static final String DIAMOND_MINING_HELMET = ARMOR_PREFIX
            + "tmfdiamond.png";

    public static final String MINING_HELMET_LAMP = TEXTURE_PREFIX
            + ItemLib.MINING_HELMET_LAMP;
    public static final String MINING_HELMET_IRON = TEXTURE_PREFIX
            + ItemLib.MINING_HELMET_IRON;
    public static final String MINING_HELMET_GOLD = TEXTURE_PREFIX
            + ItemLib.MINING_HELMET_GOLD;
    public static final String MINING_HELMET_DIAMOND = TEXTURE_PREFIX
            + ItemLib.MINING_HELMET_DIAMOND;

    private static final String GUI_PREFIX = PATH_PREFIX
            + "gui/";

    // public static final String ITEM_SPRITE_PATH = GUI_PREFIX + "items.png";
    public static final String GUI_TOOLBELT_PATH = GUI_PREFIX
            + "toolbeltGui.png";
    public static final ResourceLocation GUI_TOOLBELT = new ResourceLocation(CoreLib.MOD_ID, GUI_TOOLBELT_PATH);
    public static final String GUI_UTILITYBELT_PATH = GUI_PREFIX
            + "utilityBeltGui.png";
    public static final ResourceLocation GUI_UTILITYBELT = new ResourceLocation(CoreLib.MOD_ID, GUI_UTILITYBELT_PATH);
    public static final String GUI_GEOEQUIP_PATH = GUI_PREFIX
            + "geoequip.png";
    public static final ResourceLocation GUI_GEOEQUIP = new ResourceLocation(CoreLib.MOD_ID, GUI_GEOEQUIP_PATH);
    public static final String GUI_GRINDER_PATH = GUI_PREFIX
            + "grinder.png";
    public static final ResourceLocation GUI_GRINDER = new ResourceLocation(CoreLib.MOD_ID, GUI_GRINDER_PATH);
    public static final String GUI_REFINERY_PATH = GUI_PREFIX
            + "refinery.png";
    public static final ResourceLocation GUI_REFINERY = new ResourceLocation(CoreLib.MOD_ID, GUI_REFINERY_PATH);
    public static final String GUI_AUTOMIXTABLE_PATH = GUI_PREFIX
            + "automixtable.png";
    public static final ResourceLocation GUI_AUTOMIXTABLE = new ResourceLocation(CoreLib.MOD_ID, GUI_AUTOMIXTABLE_PATH);
    public static final String GUI_STOVE_PATH = GUI_PREFIX
            + "stove.png";
    public static final ResourceLocation GUI_STOVE = new ResourceLocation(CoreLib.MOD_ID, GUI_STOVE_PATH);
    ;

    private static final String MACHINE_PREFIX = ASSET_PREFIX
            + PATH_PREFIX
            + "machines/";

    private static final String MODEL_PREFIX = MACHINE_PREFIX
            + "models/";
    private static final String HD_PREFIX = "hd/";

    private static final String TRACKER_PREFIX = GUI_PREFIX
            + "tracker/";

    private static final String TRACKER_BG_PATH = TRACKER_PREFIX
            + "trackerBG.png";
    public static final ResourceLocation TRACKER_BG = new ResourceLocation(CoreLib.MOD_ID, TRACKER_BG_PATH);

    private static final String TRACKER_CONTACT_PATH = TRACKER_PREFIX
            + "contact.png";
    public static final ResourceLocation TRACKER_CONTACT = new ResourceLocation(CoreLib.MOD_ID, TRACKER_CONTACT_PATH);

    private static final String TRACKER_SWEEP_PATH = TRACKER_PREFIX
            + "trackerSweep.png";
    public static final ResourceLocation TRACKER_SWEEP = new ResourceLocation(CoreLib.MOD_ID, TRACKER_SWEEP_PATH);

    private static final String RESOURCE_PREFIX = ASSET_PREFIX
            + "resources/";

    public static final String DUST_LIB_PATH = RESOURCE_PREFIX
            + "mixedDust.js";

    private static final String RECIPE_PATH = ASSET_PREFIX
            + "recipes/";
    public static final String RECIPE_PATH_XML = RECIPE_PATH
            + "xml/";
    public static final String RECIPE_PATH_JSON = RECIPE_PATH
            + "json/";

    public static final String RECIPES_REFINERY = "refinery.json";
    public static final String RECIPES_GRINDER = "grinder.json";

    public static final String MINERAL_ACXIUM = TEXTURE_PREFIX
            + ItemLib.MINERAL_ACXIUM;
    public static final String MINERAL_BISOGEN = TEXTURE_PREFIX
            + ItemLib.MINERAL_BISOGEN;
    public static final String MINERAL_CYDRINE = TEXTURE_PREFIX
            + ItemLib.MINERAL_CYDRINE;

    public static final String NUGGET_ACXIUM = TEXTURE_PREFIX
            + ItemLib.NUGGET_ACXIUM;
    public static final String NUGGET_BISOGEN = TEXTURE_PREFIX
            + ItemLib.NUGGET_BISOGEN;
    public static final String NUGGET_CYDRINE = TEXTURE_PREFIX
            + ItemLib.NUGGET_CYDRINE;

    public static final String INGOT_ACXIUM = TEXTURE_PREFIX
            + ItemLib.INGOT_ACXIUM;
    public static final String INGOT_BISOGEN = TEXTURE_PREFIX
            + ItemLib.INGOT_BISOGEN;
    public static final String INGOT_CYDRINE = TEXTURE_PREFIX
            + ItemLib.INGOT_CYDRINE;

    public static final String DUST_AXCIUM = TEXTURE_PREFIX
            + ItemLib.DUST_ACXIUM;
    public static final String DUST_BISOGEN = TEXTURE_PREFIX
            + ItemLib.DUST_BISOGEN;
    public static final String DUST_CYDRINE = TEXTURE_PREFIX
            + ItemLib.DUST_CYDRINE;
    public static final String DUST_MIXED = TEXTURE_PREFIX
            + ItemLib.DUST_MIXED;

    public static final String MOTION_SENSOR = TEXTURE_PREFIX
            + ItemLib.MOTION_SENSOR;
    public static final String MINING_TOOLBELT = TEXTURE_PREFIX
            + ItemLib.MINING_TOOLBELT;
    public static final String UTILITY_BELT = TEXTURE_PREFIX
            + ItemLib.UTILITY_BELT;

    public static final String PART_ACXIUM_CORE = TEXTURE_PREFIX
            + ItemLib.PART_ACXIUM_CORE;
    public static final String PART_ACXOGEN_SCREEN = TEXTURE_PREFIX
            + ItemLib.PART_ACXOGEN_SCREEN;
    public static final String PART_ALLOY_CASING = TEXTURE_PREFIX
            + ItemLib.PART_ALLOY_CASING;
    public static final String PART_BISOGEN_GEAR = TEXTURE_PREFIX
            + ItemLib.PART_BISOGEN_GEAR;
    public static final String PART_CYDRINE_MOTOR = TEXTURE_PREFIX
            + ItemLib.PART_CYDRINE_MOTOR;
    public static final String PART_CYDRIUM_SENSOR = TEXTURE_PREFIX
            + ItemLib.PART_CYDRIUM_SENSOR;

    public static final String RECIPE_STORE = TheMinersFriend.proxy.getMinecraftDir()
            + "/config/TMFRecipes";

    public static String getModelPath(boolean hasHDModel) {
        if (hasHDModel
                && FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) {
            return MODEL_PREFIX + HD_PREFIX;
        }
        return MODEL_PREFIX;
    }

}