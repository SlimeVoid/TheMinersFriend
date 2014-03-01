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
package com.slimevoid.tmf.core.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.slimevoid.tmf.core.lib.CommandLib;
import com.slimevoid.tmf.core.lib.DataLib;
import com.slimevoid.tmf.core.lib.ItemLib;
import com.slimevoid.tmf.core.lib.NBTLib;
import com.slimevoid.tmf.items.tools.ItemMiningToolBelt;
import com.slimevoid.tmf.items.tools.ItemMotionSensor;
import com.slimevoid.tmf.items.tools.inventory.ContainerMiningToolBelt;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ItemHelper {
    /**
     * Check if a player is holding or using a parsed tool class and return for
     * convenience
     * 
     * @param entitylivingbase
     *            the Player to check
     * @param world
     *            the World of the Player
     * @param itemClass
     *            the Class of Item to check for
     * @return the Held Item or null if the check was unsuccessfull
     */
    private static ItemStack playerIsHoldingOrUsingTool(EntityLivingBase entitylivingbase, World world, Class<? extends Item> itemClass) {
        if (entitylivingbase.getHeldItem() != null
            && entitylivingbase.getHeldItem().getItem() != null
            && itemClass.isInstance(entitylivingbase.getHeldItem().getItem())) {
            return entitylivingbase.getHeldItem();
        }
        return null;
    }

    /**
     * Check if a player is carrying a certain tool type and return the list for
     * convenience
     * 
     * @param entityplayer
     *            the Player to check
     * @param world
     *            the World of the Player
     * @param itemClass
     *            the Class of Item to check for
     * @return the List of tools
     */
    private static List<ItemStack> playerHasTools(EntityPlayer entityplayer, World world, Class<? extends Item> itemClass) {
        List<ItemStack> tools = new ArrayList<ItemStack>();
        IInventory playerInventory = entityplayer.inventory;
        for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
            ItemStack itemstack = playerInventory.getStackInSlot(slot);
            if (itemstack != null && itemstack.getItem() != null
                && itemClass.isInstance(itemstack.getItem())) {
                tools.add(itemstack);
            }
        }
        return tools;
    }

    /**
     * Check if a player has any Tool Belts in their inventory and return the
     * List for convenience
     * 
     * @param entityplayer
     *            the Player to check
     * @param world
     *            the World of the Player
     * @return the List of Tool Belts (if any)
     */
    public static List<ItemStack> getToolBelts(EntityPlayer entityplayer, World world) {
        return playerHasTools(entityplayer,
                              world,
                              ItemMiningToolBelt.class);
    }

    /**
     * Check if a Player has a Tool Belt and return for convenience
     * 
     * @param entityplayer
     *            the Player to check
     * @param world
     *            the World of the Player
     * @param isHeld
     *            whether we're checking for a held Tool Belt (Should be true)
     * @return
     */
    public static ItemStack getToolBelt(EntityLivingBase entitylivingbase, World world, boolean isHeld) {
        return isHeld ? playerIsHoldingOrUsingTool(entitylivingbase,
                                                   world,
                                                   ItemMiningToolBelt.class) : null;
    }

    public static boolean isItem(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() != null;
    }

    /**
     * Checks whether a given ItemStack is a Tool belt
     * 
     * @param itemstack
     *            the ItemStack to check
     * @return true or false
     */
    public static boolean isToolBelt(ItemStack itemstack) {
        return isItem(itemstack)
               && itemstack.getItem() instanceof ItemMiningToolBelt;
    }

    /**
     * Checks it the given ItemStack is an instance of ItemBlock
     * 
     * @param itemstack
     * @return ItemBlock or not
     */
    public static boolean isItemBlock(ItemStack itemstack) {
        return isItem(itemstack) && itemstack.getItem() instanceof ItemBlock;
    }

    /*
     * Infi Tool Check for Tinkers Construct compatibility
     */

    /**
     * Checks to see whether or not tools belong to the infi tool list
     * 
     * @param itemstack
     * @return if is INFI_TOOL
     */
    public static boolean isItemInfiTool(ItemStack itemstack) {
        return isItem(itemstack)
               && itemstack.getUnlocalizedName().startsWith(ItemLib.INFI_TOOL);
    }

    public static boolean isItemInfiPickaxe(ItemStack itemstack) {
        boolean flag = false;
        if (isItemInfiTool(itemstack)) {
            String stackName = itemstack.getUnlocalizedName();
            if (stackName.contains(ItemLib.INFI_TOOL_PICKAXE)
                || stackName.contains(ItemLib.INFI_TOOL_HAMMER)) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isItemInfiShovel(ItemStack itemstack) {
        boolean flag = false;
        if (isItemInfiTool(itemstack)) {
            String stackName = itemstack.getUnlocalizedName();
            if (stackName.contains(ItemLib.INFI_TOOL_SHOVEL)
                || stackName.contains(ItemLib.INFI_TOOL_MATTOCK)
                || stackName.contains(ItemLib.INFI_TOOL_EXCAVATOR)) {
                flag = true;
            }
        }
        return flag;
    }

    /*
     * End Infi Tool Check
     */

    /**
     * Checks it the given ItemStack is an instance of ItemPickaxe
     * 
     * @param itemstack
     * @return ItemPickaxe or not
     */
    public static boolean isItemPickaxe(ItemStack itemstack) {
        return isItem(itemstack)
               && (itemstack.getItem() instanceof ItemPickaxe || isItemInfiPickaxe(itemstack));
    }

    /**
     * Checks it the given ItemStack is an instance of ItemSpade
     * 
     * @param itemstack
     * @return ItemSpade or not
     */
    public static boolean isItemSpade(ItemStack itemstack) {
        return isItem(itemstack)
               && (itemstack.getItem() instanceof ItemSpade || isItemInfiShovel(itemstack));
    }

    /**
     * Checks it the given ItemStack is an instance of ItemMotionSensor
     * 
     * @param itemstack
     * @return ItemMotionSensor or not
     */
    public static boolean isItemMotionSensor(ItemStack itemstack) {
        return isItem(itemstack)
               && itemstack.getItem() instanceof ItemMotionSensor;
    }

    public static ItemStack getSelectedTool(ItemStack itemstack) {
        // Check that the current itemstack is a Tool Belt
        if (isToolBelt(itemstack)) {
            // Retrieve the selected tool
            ItemStack selectedTool = ((ItemMiningToolBelt) itemstack.getItem()).getSelectedTool(itemstack);
            // If there is a tool in the selected slot
            if (selectedTool != null) {
                // Perform the onBlockDestroyed using that Tool
                return selectedTool;
            }
        }
        return null;
    }

    public static ItemStack[] getTools(ItemStack itemstack) {
        ItemStack[] miningTools = new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
        if (itemstack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = itemstack.getTagCompound();
            if (nbttagcompound != null && nbttagcompound.hasKey(NBTLib.TOOLS)) {
                NBTTagList toolsTag = nbttagcompound.getTagList(NBTLib.TOOLS,
                                                                10);
                for (int i = 0; i < toolsTag.tagCount(); i++) {
                    NBTTagCompound tagCompound = (NBTTagCompound) toolsTag.getCompoundTagAt(i);
                    byte slot = tagCompound.getByte(NBTLib.SLOT);
                    if (slot >= 0 && slot < miningTools.length) {
                        miningTools[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
        }
        return miningTools;
    }

    public static ItemStack getNextSelectedTool(ItemStack itemstack) {
        if (isToolBelt(itemstack)) {
            return ((ItemMiningToolBelt) itemstack.getItem()).cycleTool(itemstack,
                                                                        CommandLib.CYCLE_TOOLBELT_UP);
        }
        return null;
    }

    public static ItemStack getPreviousSelectedTool(ItemStack itemstack) {
        if (isToolBelt(itemstack)) {
            return ((ItemMiningToolBelt) itemstack.getItem()).cycleTool(itemstack,
                                                                        CommandLib.CYCLE_TOOLBELT_DOWN);
        }
        return null;
    }

    public static void toggleMiningMode(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        if (isToolBelt(itemstack)) {
            ((ItemMiningToolBelt) itemstack.getItem()).toggleMiningMode(world,
                                                                        entityplayer,
                                                                        itemstack);
        }
    }

    public static int getItemInUseFieldId(World world, EntityPlayer entityplayer) {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT
               || world.isRemote ? 32 : 31;
    }

    public static int getItemUseCountFieldId(World world, EntityPlayer entityplayer) {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT
               || world.isRemote ? 33 : 32;
    }

    public static int getSelectedSlot(ItemStack itemstack) {
        if (isToolBelt(itemstack)) {
            return ((ItemMiningToolBelt) itemstack.getItem()).getSelectedSlot(itemstack);
        }
        return 0;
    }

    public static boolean getMiningMode(ItemStack itemstack) {
        if (isToolBelt(itemstack)) {
            return ((ItemMiningToolBelt) itemstack.getItem()).isMiningModeEnabled(itemstack);
        }
        return false;
    }

    public static void updateContainerInfo(World world, EntityPlayer entityplayer, ItemStack toolBelt) {
        if (entityplayer.openContainer != null
            && entityplayer.openContainer instanceof ContainerMiningToolBelt) {
            ContainerMiningToolBelt container = (ContainerMiningToolBelt) entityplayer.openContainer;
            container.getToolBelt().onExternalChange();
        }
    }
}
