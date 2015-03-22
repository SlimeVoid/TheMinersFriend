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
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.core.TheMinersFriend;
import net.slimevoid.tmf.core.lib.BlockLib;
import net.slimevoid.tmf.core.lib.GuiLib;
import net.slimevoid.tmf.fuel.IFuelHandlerTMF;
import net.slimevoid.tmf.items.minerals.ItemMineral;

public class TileEntityGeologicalEquipment extends TileEntityMachine {
    /**
     * Fuel
     */
    private ItemStack                 fuelStack;

    /**
     * The width of the Geo scan in relation to this xCoord and zCoord
     */
    private int                       maxScanWidth;

    /**
     * How many levels that Geo Equipment can scan along yCoord
     */
    private int                       maxScanDepth;

    /**
     * The current level of scan
     */
    public int                        currentLevel;
    public int                        currentLevelIdx;

    /**
     * The scan speed based on fuel
     */
    private int                       scanSpeed;

    /**
     * Survey data
     * 
     * Idea is to store each level (Integer) with Array of blocks of scanSize
     */
    private HashMap<Integer, IBlockState[]> surveyData;

    private boolean                   hasOre;

    public TileEntityGeologicalEquipment() {
        super();
        this.surveyData = new HashMap<Integer, IBlockState[]>();
        this.hasOre = false;
        currentLevelIdx = 9;
    }

    @Override
    public boolean onBlockActivated(IBlockState blockState, EntityPlayer entityplayer, EnumFacing side, float xHit, float yHit, float zHit) {
        entityplayer.openGui(
                TheMinersFriend.instance,
                GuiLib.GUIID_GEOEQUIP,
                this.worldObj,
                this.getPos().getX(),
                this.getPos().getY(),
                this.getPos().getZ());

        return true;
    }

    private void gotoNextLevel() {
        currentLevelIdx = 0;
        if (currentLevel == 0) {
            currentLevel = this.getPos().getY() - 1;
        } else {
            currentLevel--;
        }
    }

    public void scan(int depth, int idx) {
        IBlockState block = getBlockAt(worldObj,
                new BlockPos(this.getPos().getX() + indexToRelativeX(idx),
                        depth,
                        this.getPos().getZ() + indexToRelativeZ(idx)));

        if (block != null) {
            addSurveyData(depth,
                          idx,
                          block);
        }
    }

    private int indexToRelativeX(int idx) {
        switch (idx) {
        case 3:
            return -1;
        case 4:
            return 1;
        case 5:
            return -1;
        case 6:
            return 1;
        case 7:
            return -1;
        case 8:
            return 1;
        default:
            return 0;
        }
    }

    private int indexToRelativeZ(int idx) {
        switch (idx) {
        case 1:
            return -1;
        case 2:
            return 1;
        case 5:
            return -1;
        case 6:
            return -1;
        case 7:
            return 1;
        case 8:
            return 1;
        default:
            return 0;
        }
    }

    public void addSurveyData(int depth, int idx, IBlockState block) {
        if ((worldObj != null && depth >= worldObj.getHeight())
            || (depth - this.maxScanDepth) <= 0) return;

        if (!surveyData.containsKey(depth)) {
            surveyData.put(depth,
                           new IBlockState[9]);
        }

        surveyData.get(depth)[idx] = block;
    }

    public IBlockState getBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    public IBlockState getSurveyResult(int depth, int cell) {
        IBlockState[] map = getSurveyResult(depth);
        if (map != null && map.length >= cell) return map[cell];
        return null;
    }

    public IBlockState[] getSurveyResult(int depth) {
        return surveyData.get(depth);
    }

    public boolean oreFound() {
        return hasOre;
    }

    public IBlockState[] getBlocksAt(int level) {
        if (surveyData.containsKey(level)) {
            return surveyData.get(level);
        }
        return null;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index == 0) return fuelStack;
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        fuelStack = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return BlockLib.BLOCK_GEOEQUIPMENT;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);

        NBTTagList items = nbtCompound.getTagList("Items",
                                                  10);
        if (items.tagCount() > 0) {
            NBTTagCompound itemInSlot = (NBTTagCompound) items.getCompoundTagAt(0);
            fuelStack = ItemStack.loadItemStackFromNBT(itemInSlot);
        }

        surveyData = new HashMap<Integer, IBlockState[]>();
        NBTTagList survey = nbtCompound.getTagList("Survey",
                                                   0);
        for (int i = 0; i < survey.tagCount(); ++i) {
            NBTBase depthTag = (NBTBase) survey.getCompoundTagAt(i);

            NBTTagCompound depthData = (NBTTagCompound) ((NBTTagList) depthTag).getCompoundTagAt(0);
            int depth = depthData.getInteger("Depth");

            for (int j = 1; j <= 9; j++) {
                NBTTagCompound block = (NBTTagCompound) ((NBTTagList) depthTag).getCompoundTagAt(j);
                Integer blockId = block.getInteger("Block");

                if (!(blockId == -1)) addSurveyData(
                        depth,
                        j - 1,
                        Block.getStateById(blockId)
                );
            }
        }

        currentLevel = nbtCompound.getInteger("CurrentLevel");
        currentLevelIdx = nbtCompound.getInteger("CurrentLevelIdx");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);

        NBTTagList items = new NBTTagList();
        if (fuelStack != null) {
            NBTTagCompound itemInSlot = new NBTTagCompound();
            fuelStack.writeToNBT(itemInSlot);
            items.appendTag(itemInSlot);
        }
        nbtCompound.setTag("Items",
                           items);

        NBTTagList survey = new NBTTagList();
        for (int depth : surveyData.keySet()) {
            if (surveyData.get(depth) != null) {
                NBTTagList depthTag = new NBTTagList();
                NBTTagCompound depthData = new NBTTagCompound();
                depthData.setInteger("Depth",
                                     depth);
                depthTag.appendTag(depthData);

                for (int idx = 0; idx < surveyData.get(depth).length; idx++) {
                    NBTTagCompound blockId = new NBTTagCompound();
                    if (surveyData.get(depth)[idx] == null) {
                        blockId.setInteger("Block",
                                           -1);
                    } else {
                        blockId.setInteger("Block",
                                          Block.getStateId(surveyData.get(depth)[idx]));
                    }
                    depthTag.appendTag(blockId);
                }
                survey.appendTag(depthTag);
            }
        }
        nbtCompound.setTag("Survey",
                           survey);

        nbtCompound.setInteger("CurrentLevel",
                               currentLevel);
        nbtCompound.setInteger("CurrentLevelIdx",
                               currentLevelIdx);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        // if (ForgeDirection.getOrientation(side) == ForgeDirection.DOWN)
        // return new int[] { 1 };
        // if (ForgeDirection.getOrientation(side) == ForgeDirection.UP) return
        // new int[] { 0 };
        return new int[] { 0 };
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
            for (int i = 0; i < this.currentItemWidth; i++) {
                if (currentLevelIdx > 8) {
                    gotoNextLevel();
                }
                scan(currentLevel,
                     currentLevelIdx);

                currentLevelIdx++;
            }
        }
    }

    @Override
    protected boolean canSmelt() {
        return true;
    }

    // Override to force TMF fuel.
    @Override
    public int getItemBurnTime(ItemStack stack) {
        if (stack == null) return 0;

        if (stack.getItem() instanceof IFuelHandlerTMF) return ((ItemMineral) stack.getItem()).getBurnTime(stack);

        return 0;
    }

    @Override
    public int getCurrentFuelBurnTime() {
        return getItemBurnTime(fuelStack);
    }

    @Override
    public int getCurrentFuelBurnSpeed() {
        return getItemBurnSpeed(fuelStack);
    }

    @Override
    public int getCurrentFuelBurnWidth() {
        return getItemBurnWidth(fuelStack);
    }

    @Override
    public ItemStack getCurrentFuelStack() {
        return fuelStack;
    }

    @Override
    public void setCurrentFuelStack(ItemStack stack) {
        fuelStack = stack;
    }

    @Override
    public void onInventoryHasChanged(World world, BlockPos pos) {
        /*
         * Sends block to client for update Automatically updates the associated
         * GUI should it be open
         */
        world.markBlockForUpdate(pos);
    }

    @Override
    public int getExtendedBlockID() {
        return BlockTypeMachine.GEOEQUIP.getId();
    }

    @Override
    protected void addHarvestContents(ArrayList<ItemStack> harvestList) {
        if (this.fuelStack != null) {
            harvestList.add(this.fuelStack);
        }
    }
}
