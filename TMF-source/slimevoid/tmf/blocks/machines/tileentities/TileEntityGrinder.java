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
package slimevoid.tmf.blocks.machines.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import slimevoid.tmf.blocks.machines.EnumMachine;
import slimevoid.tmf.blocks.machines.recipes.GrinderRecipes;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoidlib.blocks.BlockBase;

public class TileEntityGrinder extends TileEntityMachine {
    /**
     * 0: Mineral 1: Fuel 2: Dust
     */
    private ItemStack[] grinderItemStacks = new ItemStack[3];

    @Override
    public void setBlockBoundsBasedOnState(BlockBase block) {
        block.setBlockBounds(0.1875f,
                             0f,
                             0.1875f,
                             0.8125f,
                             0.9f,
                             0.8125f);
    }

    @Override
    public void setBlockBoundsForItemRender(BlockBase block) {
        block.setBlockBounds(0.1875f,
                             0f,
                             0.1875f,
                             0.8125f,
                             0.9f,
                             0.8125f);
    }

    @Override
    public boolean onBlockActivated(EntityPlayer player) {
        player.openGui(TheMinersFriend.instance,
                       GuiLib.GUIID_GRINDER,
                       this.worldObj,
                       this.xCoord,
                       this.yCoord,
                       this.zCoord);

        return true;
    }

    @Override
    public int getSizeInventory() {
        return grinderItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return grinderItemStacks[index];
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        grinderItemStacks[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return BlockLib.BLOCK_GRINDER;
    }

    @Override
    public void readFromNBT(NBTTagCompound ntbCompound) {
        super.readFromNBT(ntbCompound);
        NBTTagList items = ntbCompound.getTagList("Items");
        grinderItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemInSlot = (NBTTagCompound) items.tagAt(i);
            byte itemBytes = itemInSlot.getByte("Slot");

            if (itemBytes >= 0 && itemBytes < grinderItemStacks.length) {
                grinderItemStacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound ntbCompound) {
        super.writeToNBT(ntbCompound);
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < grinderItemStacks.length; ++i) {
            if (grinderItemStacks[i] != null) {
                NBTTagCompound itemInSlot = new NBTTagCompound();
                itemInSlot.setByte("Slot",
                                   (byte) i);
                grinderItemStacks[i].writeToNBT(itemInSlot);
                items.appendTag(itemInSlot);
            }
        }

        ntbCompound.setTag("Items",
                           items);
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) return new int[] { 1 };
        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP) return new int[] { 0 };
        return new int[] { 2 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        return true;
    }

    @Override
    public void smeltItem() {
        if (canSmelt()) {
            ItemStack mineralItem = grinderItemStacks[0];

            if (GrinderRecipes.grinding().isMineralAllowed(mineralItem)) {
                ItemStack smelted = GrinderRecipes.grinding().getRefiningResult(mineralItem.itemID);
                if (smelted != null && smelted.stackSize > 0) {
                    if (grinderItemStacks[2] == null) {
                        grinderItemStacks[2] = smelted.copy();
                    } else if (grinderItemStacks[2].isItemEqual(smelted)) {
                        grinderItemStacks[2].stackSize += smelted.stackSize;
                    }

                    --grinderItemStacks[0].stackSize;
                    if (grinderItemStacks[0].stackSize <= 0) {
                        grinderItemStacks[0] = null;
                    }
                }
            }
        }
    }

    @Override
    protected boolean canSmelt() {
        if (grinderItemStacks[0] == null) return false;

        ItemStack mineralItem = grinderItemStacks[0];

        if (GrinderRecipes.grinding().isMineralAllowed(mineralItem)) {
            ItemStack smelted = GrinderRecipes.grinding().getRefiningResult(mineralItem.itemID);
            if (smelted != null) {
                if (grinderItemStacks[2] == null) return true;
                if (grinderItemStacks[2] != null
                    && grinderItemStacks[2].stackSize + smelted.stackSize <= getInventoryStackLimit()) return true;
            }
        }

        return false;
    }

    @Override
    public int getCurrentFuelBurnTime() {
        return getItemBurnTime(grinderItemStacks[1]);
    }

    @Override
    public int getCurrentFuelBurnSpeed() {
        return getItemBurnSpeed(grinderItemStacks[1]) * 2;
    }

    @Override
    public int getCurrentFuelBurnWidth() {
        return getItemBurnWidth(grinderItemStacks[1]);
    }

    @Override
    public ItemStack getCurrentFuelStack() {
        return grinderItemStacks[1];
    }

    @Override
    public void setCurrentFuelStack(ItemStack stack) {
        grinderItemStacks[1] = stack;
    }

    @Override
    public int getExtendedBlockID() {
        return EnumMachine.GRINDER.getId();
    }

    @Override
    protected void addHarvestContents(ArrayList<ItemStack> harvestList) {
        for (ItemStack itemstack : this.grinderItemStacks) {
            if (itemstack != null) {
                harvestList.add(itemstack);
            }
        }
    }

}
