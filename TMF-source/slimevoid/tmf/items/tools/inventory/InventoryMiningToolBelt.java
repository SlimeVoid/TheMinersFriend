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
package slimevoid.tmf.items.tools.inventory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.ItemLib;
import slimevoid.tmf.core.lib.NBTLib;
import slimevoid.tmf.core.lib.PacketLib;

public class InventoryMiningToolBelt implements IInventory {

	World				world;
	EntityLivingBase	entityliving;
	int					selectedTool	= 0;
	ItemStack[]			miningTools		= new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
	boolean				mode			= false;

	public InventoryMiningToolBelt(World world, EntityLivingBase entityliving, ItemStack itemstack) {
		this.world = world;
		this.entityliving = entityliving;
		if (itemstack != null && itemstack.hasTagCompound()) {
			this.readFromNBT(itemstack.stackTagCompound);
		}
	}

	public int getSelectedSlot() {
		return this.selectedTool;
	}

	public boolean getMiningMode() {
		return this.mode;
	}

	public ItemStack[] getTools() {
		return this.miningTools;
	}

	@Override
	public int getSizeInventory() {
		return DataLib.TOOL_BELT_MAX_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= 0 && slot < this.miningTools.length ? miningTools[slot] : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int stacksize) {
		if (this.miningTools[slot] != null) {
			ItemStack stackInSlot;

			if (this.miningTools[slot].stackSize <= stacksize) {
				stackInSlot = this.miningTools[slot];
				this.miningTools[slot] = null;
				return stackInSlot;
			} else {
				stackInSlot = this.miningTools[slot].splitStack(stacksize);

				if (this.miningTools[slot].stackSize <= 0) {
					this.miningTools[slot] = null;
				}
				return stackInSlot;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.miningTools[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (itemstack != null
			&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.miningTools[slot] = itemstack;
	}

	@Override
	public String getInvName() {
		return ItemLib.MINING_TOOLBELT;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		ItemStack heldItem = entityliving.getHeldItem();
		if (ItemHelper.isToolBelt(heldItem)) {
			heldItem.stackTagCompound = new NBTTagCompound();
			this.writeToNBT(heldItem.stackTagCompound);
		}

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	public void toggleMiningMode() {
		this.mode = !this.mode;
		PacketLib.sendMiningModeMessage(world,
										this.entityliving,
										this.mode);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return !ItemHelper.isItemBlock(itemstack)
				&& !ItemHelper.isToolBelt(itemstack);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		NBTTagList toolsTag = nbttagcompound.getTagList(NBTLib.TOOLS);
		this.miningTools = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < toolsTag.tagCount(); i++) {
			NBTTagCompound tagCompound = (NBTTagCompound) toolsTag.tagAt(i);
			byte slot = tagCompound.getByte(NBTLib.SLOT);
			if (slot >= 0 && slot < this.miningTools.length) {
				this.miningTools[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
		this.selectedTool = nbttagcompound.getInteger(NBTLib.SELECTED_TOOL);
		this.mode = nbttagcompound.getBoolean(NBTLib.MINING_MODE);
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		NBTTagList toolsTag = new NBTTagList();
		for (int i = 0; i < this.miningTools.length; i++) {
			if (miningTools[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte(NBTLib.SLOT,
									(byte) i);
				this.miningTools[i].writeToNBT(tagCompound);
				toolsTag.appendTag(tagCompound);
			}
		}
		nbttagcompound.setTag(	NBTLib.TOOLS,
								toolsTag);
		nbttagcompound.setInteger(	NBTLib.SELECTED_TOOL,
									this.selectedTool);
		nbttagcompound.setBoolean(	NBTLib.MINING_MODE,
									this.mode);
	}
}