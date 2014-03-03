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
package com.slimevoid.tmf.blocks.machines.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.slimevoid.tmf.blocks.machines.recipes.RefineryRecipes;
import com.slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;

public class ContainerRefinery extends ContainerMachine {

    public ContainerRefinery(InventoryPlayer playerInventory, TileEntityRefinery refinery) {
        super(playerInventory, refinery, refinery.getWorldObj(), 0, 84);
    }

    @Override
    protected void bindLocalInventory() {
        this.addSlotToContainer(new Slot(this.getInventoryData(), 0, 56, 17)); // Ore
        this.addSlotToContainer(new Slot(this.getInventoryData(), 1, 56, 53)); // Fuel

        this.addSlotToContainer(new SlotMachineOutput(this.getInventoryData(), 2, 112, 35)); // Acxium
        this.addSlotToContainer(new SlotMachineOutput(this.getInventoryData(), 3, 130, 35)); // Bisogen
        this.addSlotToContainer(new SlotMachineOutput(this.getInventoryData(), 4, 148, 35)); // Cydrine
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // merges the item into player inventory since its in the inventory
            if (slot == 2 || slot == 3 || slot == 4) {
                if (!mergeItemStack(stackInSlot,
                                    1,
                                    inventorySlots.size(),
                                    true)) {
                    return null;
                }
                slotObject.onSlotChange(stackInSlot,
                                        stack);
                // places it into the inventory is possible since its in the
                // player inventory
            } else if (slot != 1 && slot != 0) {
                ItemStack[] results = RefineryRecipes.refining().getRefiningResults(stackInSlot);
                if (results != null && results.length > 0) {
                    if (!this.mergeItemStack(stackInSlot,
                                             0,
                                             1,
                                             false)) {
                        return null;
                    }
                } else if (this.getMachineData().isItemFuel(stackInSlot)) {
                    if (!this.mergeItemStack(stackInSlot,
                                             1,
                                             2,
                                             false)) {
                        return null;
                    }
                } else if (slot >= 5 && slot < 32) {
                    if (!this.mergeItemStack(stackInSlot,
                                             32,
                                             41,
                                             false)) {
                        return null;
                    }
                } else if (slot >= 32 && slot < 41
                           && !this.mergeItemStack(stackInSlot,
                                                   5,
                                                   32,
                                                   false)) {
                    return null;
                }
            } else if (!mergeItemStack(stackInSlot,
                                       5,
                                       41,
                                       false)) {
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

            slotObject.onPickupFromSlot(entityplayer,
                                        stack);
        }

        return stack;
    }

    @Override
    protected boolean hasProgressBar() {
        return true;
    }
}
