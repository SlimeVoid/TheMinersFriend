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
package net.slimevoid.tmf.client.tickhandlers.rules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.slimevoid.tmf.api.IMotionSensorRule;
import net.slimevoid.tmf.items.tools.ItemMotionSensor;

public class MotionSensorRuleOnHotbar implements IMotionSensorRule {

    @Override
    public boolean doShowMotionSensor(EntityPlayer entityplayer, World world) {
        if (entityplayer != null && entityplayer.inventory != null) {
            for (int i = 0; i < 9; i++) {
                ItemStack itemstack = entityplayer.inventory.mainInventory[i];
                if (itemstack != null && itemstack.getItem() != null
                    && itemstack.getItem() instanceof ItemMotionSensor) {
                    return true;
                }
            }
        }
        return false;
    }
}
