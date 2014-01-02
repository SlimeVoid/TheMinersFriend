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
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.EnumMachine;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.items.minerals.ItemMineralDust;
import slimevoid.tmf.items.minerals.ItemMineralMixedDust;

public class TileEntityAutomaticMixingTable extends TileEntityMachine {

	/**
	 * 0 = blueprint 1 = output 2-10 = input
	 */
	private ItemStack[]	stacks	= new ItemStack[11];

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		player.openGui(	TheMinersFriend.instance,
						GuiLib.MIXINGTABLE_GUIID,
						this.worldObj,
						this.xCoord,
						this.yCoord,
						this.zCoord);
		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		smeltItem();
	}

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0 && stack != null
			&& !(stack.getItem() instanceof ItemMineralMixedDust)) return;
		if (index == 1 && stack != null) return;
		if (index >= 2
			&& stack != null
			&& (!(stack.getItem() instanceof ItemMineralDust) || stack.getItem() instanceof ItemMineralMixedDust)) return;

		stacks[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return BlockLib.BLOCK_AUTOMIXTABLE;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);

		NBTTagList items = nbtCompound.getTagList("Items");
		stacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound) items.tagAt(i);
			byte itemBytes = itemInSlot.getByte("Slot");

			if (itemBytes >= 0 && itemBytes < stacks.length) {
				stacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);

		NBTTagList items = new NBTTagList();

		for (int i = 0; i < stacks.length; ++i) {
			if (stacks[i] != null) {
				NBTTagCompound itemInSlot = new NBTTagCompound();
				itemInSlot.setByte(	"Slot",
									(byte) i);
				stacks[i].writeToNBT(itemInSlot);
				items.appendTag(itemInSlot);
			}
		}

		nbtCompound.setTag(	"Items",
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
		for (int i = 2; i < stacks.length; i++) {
			if (stacks[i] != null && stacks[i].stackSize > 0) {
				return new int[] { i };
			}
		}
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
			// Get blueprint's levels
			int meta = stacks[0].getItemDamage();
			int blueprintLevelA = ItemMineralMixedDust.getBurnTimeLevel(meta);
			int blueprintLevelB = ItemMineralMixedDust.getBurnSpeedLevel(meta);
			int blueprintLevelC = ItemMineralMixedDust.getBurnWidthLevel(meta);

			// Subtract form first from stacks
			int inputLevelA = blueprintLevelA;
			int inputLevelB = blueprintLevelB;
			int inputLevelC = blueprintLevelC;
			for (int i = 2; i < stacks.length; i++) {
				if (stacks[i] == null) continue;

				int inputMeta = ItemMineralMixedDust.getDustMeta(stacks[i]);
				if (!ItemMineralMixedDust.isMetaCleanDust(inputMeta)) continue;

				if (ItemMineralMixedDust.getBurnTimeLevel(inputMeta) > 0) {
					if (inputLevelA == 0) continue;
					if (stacks[i].stackSize <= inputLevelA) {
						inputLevelA -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelA;
						inputLevelA = 0;
					}
				} else if (ItemMineralMixedDust.getBurnSpeedLevel(inputMeta) > 0) {
					if (inputLevelB == 0) continue;
					if (stacks[i].stackSize <= inputLevelB) {
						inputLevelB -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelB;
						inputLevelB = 0;
					}
				} else if (ItemMineralMixedDust.getBurnWidthLevel(inputMeta) > 0) {
					if (inputLevelC == 0) continue;
					if (stacks[i].stackSize <= inputLevelC) {
						inputLevelC -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelC;
						inputLevelC = 0;
					}
				}
			}

			// Output
			if (stacks[1] == null) {
				stacks[1] = new ItemStack(stacks[0].itemID, 1, stacks[0].getItemDamage());
			} else {
				stacks[1].stackSize++;
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		if (stacks[0] == null) return false;

		if (stacks[1] != null
			&& stacks[1].stackSize >= getInventoryStackLimit()) return false;

		// Get blueprint's levels
		int meta = stacks[0].getItemDamage();
		int blueprintLevelA = ItemMineralMixedDust.getBurnTimeLevel(meta);
		int blueprintLevelB = ItemMineralMixedDust.getBurnSpeedLevel(meta);
		int blueprintLevelC = ItemMineralMixedDust.getBurnWidthLevel(meta);

		// Get total input's level (all the items combined)
		int inputLevelA = 0;
		int inputLevelB = 0;
		int inputLevelC = 0;
		for (int i = 2; i < stacks.length; i++) {
			if (stacks[i] == null) continue;

			int inputMeta = ItemMineralMixedDust.getDustMeta(stacks[i]);
			if (!ItemMineralMixedDust.isMetaCleanDust(inputMeta)) continue;

			if (ItemMineralMixedDust.getBurnTimeLevel(inputMeta) > 0) {
				inputLevelA += stacks[i].stackSize;
			} else if (ItemMineralMixedDust.getBurnSpeedLevel(inputMeta) > 0) {
				inputLevelB += stacks[i].stackSize;
			} else if (ItemMineralMixedDust.getBurnWidthLevel(inputMeta) > 0) {
				inputLevelC += stacks[i].stackSize;
			}
		}

		// True if there is enough input to create the blueprint
		return (inputLevelA >= blueprintLevelA
				&& inputLevelB >= blueprintLevelB && inputLevelC >= blueprintLevelC);
	}

	@Override
	public int getCurrentFuelBurnTime() {
		return 0;
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return 0;
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return 0;
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return null;
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
	}

	@Override
	public int getExtendedBlockID() {
		return EnumMachine.AUTOMIXTABLE.getId();
	}

}
