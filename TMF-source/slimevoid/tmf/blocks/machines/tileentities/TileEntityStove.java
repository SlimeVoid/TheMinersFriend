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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import slimevoid.tmf.blocks.machines.EnumMachine;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.GuiLib;

public class TileEntityStove extends TileEntityMachine {

	/*
	 * 0-5 Stacks to smelt 6-12 Stacks smelted
	 */
	private ItemStack[]	stoveItemStacks	= new ItemStack[12];
	private int			itemToSmelt		= 0;

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		player.openGui(	TheMinersFriend.instance,
						GuiLib.GUIID_STOVE,
						this.worldObj,
						this.xCoord,
						this.yCoord,
						this.zCoord);

		return true;
	}

	@Override
	public int getSizeInventory() {
		return stoveItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stoveItemStacks[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		stoveItemStacks[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return BlockLib.BLOCK_COOKER;
	}

	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		super.readFromNBT(ntbCompound);

		NBTTagList items = ntbCompound.getTagList("Items");
		stoveItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound) items.tagAt(i);
			byte itemBytes = itemInSlot.getByte("Slot");

			if (itemBytes >= 0 && itemBytes < stoveItemStacks.length) {
				stoveItemStacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
			}
		}
		this.itemToSmelt = ntbCompound.getInteger("SmeltIndex");
	}

	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		super.writeToNBT(ntbCompound);

		NBTTagList items = new NBTTagList();

		for (int i = 0; i < stoveItemStacks.length; ++i) {
			if (stoveItemStacks[i] != null) {
				NBTTagCompound itemInSlot = new NBTTagCompound();
				itemInSlot.setByte(	"Slot",
									(byte) i);
				stoveItemStacks[i].writeToNBT(itemInSlot);
				items.appendTag(itemInSlot);
			}
		}

		ntbCompound.setTag(	"Items",
							items);
		ntbCompound.setInteger(	"SmeltIndex",
								this.itemToSmelt);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return slot >= 0 && slot < 6;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] itemsToSmelt = new int[] { 0, 1, 2, 3, 4, 5 };
		int[] smeltedItems = new int[] { 6, 7, 8, 9, 10, 11 };
		if (ForgeDirection.getOrientation(side) == ForgeDirection.UP) return itemsToSmelt;
		if (ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) return smeltedItems;
		if (ForgeDirection.getOrientation(this.getRotatedSide(side)) == ForgeDirection.EAST) return smeltedItems;
		return itemsToSmelt;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return slot >= 0 && slot < 6;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return ForgeDirection.getOrientation(this.getRotatedSide(side)) == ForgeDirection.EAST
				&& slot >= 6 && slot < 12;
	}

	@Override
	public void smeltItem() {
		if (canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.stoveItemStacks[this.itemToSmelt]);
			for (int i = 6; i < 12; i++) {
				if (this.stoveItemStacks[i] == null) {
					this.stoveItemStacks[i] = itemstack.copy();
				} else if (this.stoveItemStacks[i].isItemEqual(itemstack)) {
					stoveItemStacks[i].stackSize += itemstack.stackSize;
				}

				--this.stoveItemStacks[this.itemToSmelt].stackSize;

				if (this.stoveItemStacks[this.itemToSmelt].stackSize <= 0) {
					this.stoveItemStacks[this.itemToSmelt] = null;
				}
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		if (this.stoveItemStacks[this.itemToSmelt] == null) return false;
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.stoveItemStacks[this.itemToSmelt]);
		if (itemstack == null) return false;
		for (int i = 6; i < 12; i++) {
			if (this.stoveItemStacks[i] == null) {
				return true;
			} else if (this.stoveItemStacks[i].isItemEqual(itemstack)) {
				int result = this.stoveItemStacks[i].stackSize
								+ itemstack.stackSize;
				return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
			}
		}
		return false;
	}

	@Override
	public int getExtendedBlockID() {
		return EnumMachine.STOVE.getId();
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
	public void updateMachine() {
		boolean wasBurning = isBurning();
		boolean inventoryChanged = false;

		if (isBurning()) --burnTime;

		if (!worldObj.isRemote) {
			if (!isBurning() && canSmelt()) {
				// TODO :: Cooking Time
			}

			if (isBurning() && canSmelt()) {
				++cookTime;
				if (cookTime == currentItemCookTime) {
					cookTime = 0;
					smeltItem();
					inventoryChanged = true;
				}
			} else {
				cookTime = 0;
			}

			if (wasBurning != isBurning()) {
				inventoryChanged = true;
				updateMachineBlockState(isBurning(),
										worldObj,
										xCoord,
										yCoord,
										zCoord);
			}
		}

		if (inventoryChanged) {
			this.onInventoryChanged();
		}
	}
}
