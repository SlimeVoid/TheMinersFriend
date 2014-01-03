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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import slimevoid.tmf.blocks.machines.recipes.RefineryRecipes;
import slimevoid.tmf.blocks.machines.recipes.RefineryRecipes.RefineryRecipe;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.EnumBlocks;
import slimevoid.tmf.core.lib.GuiLib;

public class TileEntityRefinery extends TileEntityMachine {
	/**
	 * 0: Ore 1: Fuel 2: Acxium Mineral 3: Bisogen Mineral 4: Cydrine Mineral
	 */
	private ItemStack[]	refineryItemStacks	= new ItemStack[5];

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		player.openGui(	TheMinersFriend.instance,
						GuiLib.REFINERY_GUIID,
						this.worldObj,
						this.xCoord,
						this.yCoord,
						this.zCoord);

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

		NBTTagList items = ntbCompound.getTagList("Items");
		refineryItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound) items.tagAt(i);
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
				itemInSlot.setByte(	"Slot",
									(byte) i);
				refineryItemStacks[i].writeToNBT(itemInSlot);
				items.appendTag(itemInSlot);
			}
		}

		ntbCompound.setTag(	"Items",
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
			ItemStack oreItem = refineryItemStacks[0];
			if (BlockLib.isOre(oreItem)) {
				int oreId = oreItem.getItemDamage();
				if (RefineryRecipes.refining().isOreAllowed(oreId)) {
					ItemStack[] smelted = RefineryRecipes.refining().getRefiningResults(oreId);

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
	}

	@Override
	protected boolean canSmelt() {
		if (refineryItemStacks[0] == null) return false;

		ItemStack oreItem = refineryItemStacks[0];
		if (BlockLib.isOre(oreItem)) {
			int oreId = oreItem.getItemDamage();
			if (RefineryRecipes.refining().isOreAllowed(oreId)) {
				RefineryRecipe[] recipes = RefineryRecipes.refining().getRefineryRecipes(oreId);

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
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		this.isActive = isBurning;
	}

	@Override
	public int getExtendedBlockID() {
		return EnumBlocks.MACHINE_REFINERY.getId();
	}
}
