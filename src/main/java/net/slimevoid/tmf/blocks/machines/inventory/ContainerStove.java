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
package net.slimevoid.tmf.blocks.machines.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.slimevoid.tmf.blocks.machines.recipes.RefineryRecipes;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityMachine;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityStove;

public class ContainerStove extends ContainerMachine {

    public ContainerStove(InventoryPlayer playerInventory, TileEntityStove tileStove) {
        super(playerInventory, tileStove, tileStove.getWorldObj(), 0, 84);
    }

    @Override
    protected void bindLocalInventory() {
        TileEntityMachine stove = this.getMachineData();

        // Min level > 2 => only mixed dust.
        this.addSlotToContainer(new Slot(stove, 0, 44, 32)); // blueprint
        this.addSlotToContainer(new Slot(stove, 1, 116, 32)); // output

        // Min level = max level = 1 => only clean dust.
        this.addSlotToContainer(new Slot(stove, 2, 62, 68));
        this.addSlotToContainer(new Slot(stove, 3, 80, 68));
        this.addSlotToContainer(new Slot(stove, 4, 98, 68));
        this.addSlotToContainer(new Slot(stove, 5, 62, 86));
        this.addSlotToContainer(new Slot(stove, 6, 80, 86));
        this.addSlotToContainer(new Slot(stove, 7, 98, 86));
        this.addSlotToContainer(new Slot(stove, 8, 62, 10));
        this.addSlotToContainer(new Slot(stove, 9, 80, 104));
        this.addSlotToContainer(new Slot(stove, 10, 98, 104));
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
            if (slot >= 2 && slot < 11) {
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
                } else if (slot >= 11 && slot < 32) {
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
        return false;
    }
}
