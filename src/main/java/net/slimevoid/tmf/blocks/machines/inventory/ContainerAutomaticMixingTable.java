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
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityMachine;

public class ContainerAutomaticMixingTable extends ContainerMachine {

    public ContainerAutomaticMixingTable(InventoryPlayer playerInventory, TileEntityAutomaticMixingTable autoMixTable) {
        super(playerInventory, autoMixTable, autoMixTable.getWorldObj(), 0, 84);
    }

    @Override
    protected void bindLocalInventory() {
        TileEntityMachine autoMixTable = this.getMachineData();

        // Min level > 2 => only mixed dust.
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 0, 2, 10, 44, 32, true)); // blueprint
        this.addSlotToContainer(new SlotMachineOutput(autoMixTable, 1, 116, 32)); // output

        // Min level = max level = 1 => only clean dust.
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 2, 1, 1, 62, 68, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 3, 1, 1, 80, 68, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 4, 1, 1, 98, 68, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 5, 1, 1, 62, 86, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 6, 1, 1, 80, 86, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 7, 1, 1, 98, 86, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 8, 1, 1, 62, 104, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 9, 1, 1, 80, 104, true));
        this.addSlotToContainer(new SlotTMFFuel(autoMixTable, 10, 1, 1, 98, 104, true));
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
