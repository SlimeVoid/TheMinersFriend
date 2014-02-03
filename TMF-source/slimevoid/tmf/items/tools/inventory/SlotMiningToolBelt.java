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

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import slimevoid.tmf.core.helpers.ItemHelper;

public class SlotMiningToolBelt extends Slot {

    public SlotMiningToolBelt(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
        super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return !ItemHelper.isItemBlock(itemstack)
               && !ItemHelper.isToolBelt(itemstack)
               && this.isItemValidForSlot(itemstack);
    }

    private boolean isItemValidForSlot(ItemStack itemstack) {
        return this.isValidSlotForPickaxe(itemstack)
               || this.isValidSlotForSpade(itemstack)
               || this.isValidSlotForItem(itemstack)
               || this.isValidSlotForMotionSensor(itemstack);
    }

    private boolean isValidSlotForPickaxe(ItemStack itemstack) {
        return this.getSlotIndex() == 0 && ItemHelper.isItemPickaxe(itemstack);
    }

    private boolean isValidSlotForSpade(ItemStack itemstack) {
        return this.getSlotIndex() == 1 && ItemHelper.isItemSpade(itemstack);
    }

    private boolean isValidSlotForItem(ItemStack itemstack) {
        return this.getSlotIndex() == 2
               && !ItemHelper.isItemMotionSensor(itemstack);
    }

    private boolean isValidSlotForMotionSensor(ItemStack itemstack) {
        return getSlotIndex() == 3 && ItemHelper.isItemMotionSensor(itemstack);
    }
}
