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
package net.slimevoid.tmf.blocks.machines.tileentities;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.blocks.machines.recipes.RefineryRecipes;
import net.slimevoid.tmf.blocks.machines.recipes.RefineryRecipes.RefineryRecipe;
import net.slimevoid.tmf.core.TheMinersFriend;
import net.slimevoid.tmf.core.lib.BlockLib;
import net.slimevoid.tmf.core.lib.GuiLib;

public class TileEntityRefinery extends TileEntityMachine {
    /**
     * 0: Ore 1: Fuel 2: Acxium Mineral 3: Bisogen Mineral 4: Cydrine Mineral
     */
    private ItemStack[] refineryItemStacks = new ItemStack[5];

    @Override
    public boolean onBlockActivated(IBlockState blockState, EntityPlayer entityplayer, EnumFacing side, float xHit, float yHit, float zHit) {
        entityplayer.openGui(
                TheMinersFriend.instance,
                GuiLib.GUIID_REFINERY,
                this.getWorld(),
                this.getPos().getX(),
                this.getPos().getY(),
                this.getPos().getZ());

        return true;
    }

    @Override
    public int getSizeInventory() {
        return refineryItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return refineryItemStacks[index];
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        refineryItemStacks[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return BlockLib.BLOCK_REFINERY;
    }

    @Override
    public void readFromNBT(NBTTagCompound ntbCompound) {
        super.readFromNBT(ntbCompound);

        NBTTagList items = ntbCompound.getTagList("Items",
                                                  10);
        refineryItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemInSlot = (NBTTagCompound) items.getCompoundTagAt(i);
            byte itemBytes = itemInSlot.getByte("Slot");

            if (itemBytes >= 0 && itemBytes < refineryItemStacks.length) {
                refineryItemStacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound ntbCompound) {
        super.writeToNBT(ntbCompound);

        NBTTagList items = new NBTTagList();

        for (int i = 0; i < refineryItemStacks.length; ++i) {
            if (refineryItemStacks[i] != null) {
                NBTTagCompound itemInSlot = new NBTTagCompound();
                itemInSlot.setByte("Slot",
                                   (byte) i);
                refineryItemStacks[i].writeToNBT(itemInSlot);
                items.appendTag(itemInSlot);
            }
        }

        ntbCompound.setTag("Items",
                           items);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN) return new int[] { 1 };
        if (side == EnumFacing.UP) return new int[] { 0 };

        int maxSize = 0;
        int maxSizeSlot = 2;
        if (getStackInSlot(2) != null) {
            if (maxSize < getStackInSlot(2).stackSize) {
                maxSize = getStackInSlot(2).stackSize;
                maxSizeSlot = 2;
            }
        }
        if (getStackInSlot(3) != null) {
            if (maxSize < getStackInSlot(3).stackSize) {
                maxSize = getStackInSlot(3).stackSize;
                maxSizeSlot = 3;
            }
        }
        if (getStackInSlot(4) != null) {
            if (maxSize < getStackInSlot(4).stackSize) {
                maxSize = getStackInSlot(4).stackSize;
                maxSizeSlot = 4;
            }
        }

        return new int[] { maxSizeSlot };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side) {
        return true;
    }

    @Override
    public void smeltItem() {
        if (canSmelt()) {
            ItemStack oreItem = refineryItemStacks[0];
            if (RefineryRecipes.refining().isOreAllowed(oreItem)) {
                ItemStack[] smelted = RefineryRecipes.refining().getRefiningResults(oreItem);

                if (smelted != null && smelted.length == 3) {
                    if (smelted[0] != null && smelted[0].stackSize > 0) {
                        if (refineryItemStacks[2] == null) {
                            refineryItemStacks[2] = smelted[0].copy();
                        } else if (refineryItemStacks[2].isItemEqual(smelted[0])) {
                            refineryItemStacks[2].stackSize += smelted[0].stackSize;
                        }
                    }

                    if (smelted[1] != null && smelted[1].stackSize > 0) {
                        if (refineryItemStacks[3] == null) {
                            refineryItemStacks[3] = smelted[1].copy();
                        } else if (refineryItemStacks[3].isItemEqual(smelted[1])) {
                            refineryItemStacks[3].stackSize += smelted[1].stackSize;
                        }
                    }

                    if (smelted[2] != null && smelted[2].stackSize > 0) {
                        if (refineryItemStacks[4] == null) {
                            refineryItemStacks[4] = smelted[2].copy();
                        } else if (refineryItemStacks[4].isItemEqual(smelted[2])) {
                            refineryItemStacks[4].stackSize += smelted[2].stackSize;
                        }
                    }

                    --refineryItemStacks[0].stackSize;
                    if (refineryItemStacks[0].stackSize <= 0) {
                        refineryItemStacks[0] = null;
                    }
                }
            }
        }
    }

    @Override
    protected boolean canSmelt() {
        if (refineryItemStacks[0] == null) return false;

        ItemStack oreItem = refineryItemStacks[0];
        if (RefineryRecipes.refining().isOreAllowed(oreItem)) {
            RefineryRecipe[] recipes = RefineryRecipes.refining().getRefineryRecipes(oreItem);

            if (recipes != null && recipes.length == 3) {
                boolean ok = true;
                for (int i = 0; i < recipes.length; i++) {
                    if (refineryItemStacks[2 + recipes[i].slotId] != null) ok = false;
                }
                if (ok) return true;

                ok = true;
                for (int i = 0; i < recipes.length; i++) {
                    if (refineryItemStacks[2 + recipes[i].slotId] != null
                        && refineryItemStacks[2 + recipes[i].slotId].stackSize
                           + recipes[i].max > getInventoryStackLimit()) ok = false;
                }
                if (ok) return true;
            }
        }

        return false;
    }

    @Override
    public int getCurrentFuelBurnTime() {
        return getItemBurnTime(refineryItemStacks[1]);
    }

    @Override
    public int getCurrentFuelBurnSpeed() {
        return getItemBurnSpeed(refineryItemStacks[1]);
    }

    @Override
    public int getCurrentFuelBurnWidth() {
        return getItemBurnWidth(refineryItemStacks[1]);
    }

    @Override
    public ItemStack getCurrentFuelStack() {
        return refineryItemStacks[1];
    }

    @Override
    public void setCurrentFuelStack(ItemStack stack) {
        refineryItemStacks[1] = stack;
    }

    @Override
    public int getExtendedBlockID() {
        return BlockTypeMachine.REFINERY.getId();
    }

    @Override
    protected void addHarvestContents(ArrayList<ItemStack> harvestList) {
        for (ItemStack itemstack : this.refineryItemStacks) {
            if (itemstack != null) {
                harvestList.add(itemstack);
            }
        }
    }
}
