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

import com.slimevoid.tmf.fuel.IFuelHandlerTMF;
import com.slimevoid.tmf.items.minerals.ItemMineralDust;
import com.slimevoid.tmf.items.minerals.ItemMineralMixedDust;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTMFFuel extends Slot {
    private int     minFuelLevel;
    private int     maxFuelLevel;
    private boolean onlyDust;

    public SlotTMFFuel(IInventory inventory, int index, int minFuelLevel, int maxFuelLevel, int x, int y, boolean onlyDust) {
        super(inventory, index, x, y);
        this.minFuelLevel = minFuelLevel;
        this.maxFuelLevel = maxFuelLevel;
        this.onlyDust = onlyDust;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return false;
        if (!(stack.getItem() instanceof IFuelHandlerTMF)) return false;
        if (onlyDust && !(stack.getItem() instanceof ItemMineralDust)) return false;

        int totalLevel = 1;
        if (stack.getItem() instanceof ItemMineralMixedDust) totalLevel = ItemMineralMixedDust.getTotalLevel(stack.getItemDamage());

        return (totalLevel >= minFuelLevel && totalLevel <= maxFuelLevel);
    }
}
