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
package slimevoid.tmf.machines.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import slimevoid.tmf.machines.RefineryRecipes;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRefinery extends ContainerTMFMachine {
	
	public ContainerRefinery(InventoryPlayer playerInventory, TileEntityRefinery refinery) {
		this.inventory = refinery;

        this.addSlotToContainer(new Slot(refinery, 0, 56, 17)); // Ore
        this.addSlotToContainer(new Slot(refinery, 1, 56, 53)); // Fuel
        
        this.addSlotToContainer(new SlotMachineOutput(refinery, 2, 112, 35)); // Acxium
        this.addSlotToContainer(new SlotMachineOutput(refinery, 3, 130, 35)); // Bisogen
        this.addSlotToContainer(new SlotMachineOutput(refinery, 4, 148, 35)); // Cydrine
     
        this.bindPlayerInventory(playerInventory, 0);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		//null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			//merges the item into player inventory since its in the inventory
			if (slot == 2 || slot == 3 || slot == 4) {
				if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true)) {
					return null;
				}
				slotObject.onSlotChange(stackInSlot,stack);
				//places it into the inventory is possible since its in the player inventory
			} else if (slot != 1 && slot != 0) {
				ItemStack[] results = RefineryRecipes.refining().getRefiningResults(stackInSlot.itemID);
				if ( results != null && results.length > 0 ) {
					if ( !this.mergeItemStack(stackInSlot, 0, 1, false) ) {
						return null;
					}
				} else if ( inventory.isItemFuel(stackInSlot) ) {
					if ( !this.mergeItemStack(stackInSlot, 1, 2, false) ) {
						return null;
					}
				} else if (slot >= 5 && slot < 32) {
					if ( !this.mergeItemStack(stackInSlot, 32, 41, false) ) {
						return null;
					}
				} else if (slot >= 32 && slot < 41 && !this.mergeItemStack(stackInSlot, 5, 32, false)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 5, 41, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			
			if (stack.stackSize == stack.stackSize) {
				return null;
			}

			slotObject.onPickupFromSlot(entityplayer, stack);
		}

		return stack;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.inventory.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.inventory.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.inventory.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 3, this.inventory.currentItemCookTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			
			if (this.lastCookTime != this.inventory.cookTime) {
				var2.sendProgressBarUpdate(this, 0, this.inventory.cookTime);
			}
			
			if (this.lastBurnTime != this.inventory.burnTime) {
				var2.sendProgressBarUpdate(this, 1, this.inventory.burnTime);
			}
			
			if (this.lastItemBurnTime != this.inventory.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.inventory.currentItemBurnTime);
			}
			
			if (this.lastItemCookTime != this.inventory.currentItemCookTime) {
				var2.sendProgressBarUpdate(this, 3, this.inventory.currentItemCookTime);
			}
		}
		
		this.lastCookTime = this.inventory.cookTime;
		this.lastBurnTime = this.inventory.burnTime;
		this.lastItemBurnTime = this.inventory.currentItemBurnTime;
		this.lastItemCookTime = this.inventory.currentItemCookTime;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.inventory.cookTime = par2;
		}
		
		if (par1 == 1) {
			this.inventory.burnTime = par2;
		}
		
		if (par1 == 2) {
			this.inventory.currentItemBurnTime = par2;
		}
		
		if (par1 == 3) {
			this.inventory.currentItemCookTime = par2;
		}
	}
}
