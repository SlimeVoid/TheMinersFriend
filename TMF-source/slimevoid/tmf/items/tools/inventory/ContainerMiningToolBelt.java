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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import slimevoidlib.util.helpers.ContainerHelper;

public class ContainerMiningToolBelt extends Container {

    // The toolbelt to which this Container Belongs
    private IInventory toolbelt;

    public ContainerMiningToolBelt(InventoryPlayer playerInventory, IInventory toolBelt) {
        this.toolbelt = toolBelt;
        this.bindToolBeltInventory(toolBelt);
        bindPlayerInventory(playerInventory);
    }

    protected void bindToolBeltInventory(IInventory toolBelt) {
        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 0, 69, 37)); // Pick

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 1, 69, 59)); // Spade

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 2, 92, 37)); // Etc

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 3, 92, 59)); // Sensor
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + 56 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142 + 56));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return toolbelt.isUseableByPlayer(entityplayer);
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
            if (slot <= 3) {
                if (!mergeItemStack(stackInSlot,
                                    1,
                                    inventorySlots.size(),
                                    true)) {
                    return null;
                }
                // places it into the inventory is possible since its in the
                // player inventory
            } else if (!ContainerHelper.mergeItemStack(this,
                                                       stackInSlot,
                                                       0,
                                                       4,
                                                       false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
            slotObject.onPickupFromSlot(entityplayer,
                                        stack);
        }

        return stack;
    }

    public InventoryMiningToolBelt getToolBelt() {
        return (InventoryMiningToolBelt) this.toolbelt;
    }
}
