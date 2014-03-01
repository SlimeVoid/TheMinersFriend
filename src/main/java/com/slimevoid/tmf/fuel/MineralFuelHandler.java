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
package com.slimevoid.tmf.fuel;

import net.minecraft.item.ItemStack;

import com.slimevoid.tmf.items.minerals.ItemMineral;

public class MineralFuelHandler implements IFuelHandlerTMF {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (!(fuel.getItem() instanceof ItemMineral)) return 0;

        return ((ItemMineral) fuel.getItem()).getBurnTime(fuel);
    }

    @Override
    public int getBurnSpeed(ItemStack fuel) {
        if (!(fuel.getItem() instanceof ItemMineral)) return 0;

        return ((ItemMineral) fuel.getItem()).getBurnSpeed(fuel);
    }

    @Override
    public int getBurnWidth(ItemStack fuel) {
        if (!(fuel.getItem() instanceof ItemMineral)) return 0;

        return ((ItemMineral) fuel.getItem()).getBurnWidth(fuel);
    }

}
