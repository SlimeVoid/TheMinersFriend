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
package slimevoid.tmf.items.tools.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.ItemLib;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;

public class MiningToolBelt implements IInventory {

	ItemStack	toolBelt;

	public MiningToolBelt(ItemStack itemstack) {
		this.toolBelt = itemstack;
	}

	@Override
	public int getSizeInventory() {
		return DataLib.TOOL_BELT_MAX_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return ItemMiningToolBelt.getToolInSlot(this.toolBelt,
												slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int stacksize) {
		ItemStack[] miningTools = ItemMiningToolBelt.getTools(this.toolBelt);
		if (miningTools != null && miningTools[slot] != null) {
			ItemStack stackInSlot;

			if (miningTools[slot].stackSize <= stacksize) {
				stackInSlot = miningTools[slot];
				miningTools[slot] = null;
				ItemMiningToolBelt.setToolInSlot(	this.toolBelt,
													miningTools[slot],
													slot);
				return stackInSlot;
			} else {
				stackInSlot = miningTools[slot].splitStack(stacksize);

				if (miningTools[slot].stackSize <= 0) {
					miningTools[slot] = null;
				}
				ItemMiningToolBelt.setToolInSlot(	this.toolBelt,
													miningTools[slot],
													slot);

				return stackInSlot;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return ItemMiningToolBelt.getToolInSlot(this.toolBelt,
												slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (itemstack != null
			&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		ItemMiningToolBelt.setToolInSlot(	this.toolBelt,
											itemstack,
											slot);

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return ItemLib.MINING_TOOLBELT;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Notify this Tool Belt that changes have happened
	 * 
	 * @param sendUpdate
	 *            whether or not we should send updates to connected Players
	 */
	public void onInventoryChanged(boolean sendUpdate) {
		this.onInventoryChanged();
		if (sendUpdate) this.sendUpdate();
	}

	/**
	 * Send the updated Tool Belt data to players connected Kind of Hammer to
	 * the fly for the moment
	 */
	private void sendUpdate() {
		// PacketDispatcher.sendPacketToAllPlayers(this.createPacket().getPacket());
	}

	@Override
	public void onInventoryChanged() {
		System.out.println(this.toolBelt.getTagCompound());
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

	public void toggleMiningMode(World world, EntityPlayer entityplayer) {
		MiningMode.toggleMiningModeForPlayer(	world,
												entityplayer,
												this);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	public ItemStack getToolBelt() {
		return this.toolBelt;
	}
}