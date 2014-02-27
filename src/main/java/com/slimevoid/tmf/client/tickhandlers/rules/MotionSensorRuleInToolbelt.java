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
package com.slimevoid.tmf.client.tickhandlers.rules;

import com.slimevoid.tmf.api.IMotionSensorRule;
import com.slimevoid.tmf.core.helpers.ItemHelper;
import com.slimevoid.tmf.core.lib.DataLib;
import com.slimevoid.tmf.items.tools.ItemMiningToolBelt;
import com.slimevoid.tmf.items.tools.ItemMotionSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MotionSensorRuleInToolbelt implements IMotionSensorRule {

    @Override
    public boolean doShowMotionSensor(EntityPlayer entityplayer, World world) {
        if (entityplayer != null && entityplayer.inventory != null) {
            for (int i = 0; i < 9; i++) {
                ItemStack itemstack = entityplayer.inventory.mainInventory[i];
                if (hasMotionSensor(entityplayer,
                                    world,
                                    itemstack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasMotionSensor(EntityPlayer entityplayer, World world, ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() != null
            && itemstack.getItem() instanceof ItemMiningToolBelt) {
            if (itemstack.hasTagCompound()) {
                ItemStack[] tools = ItemHelper.getTools(itemstack);
                for (int j = 0; j < DataLib.TOOL_BELT_MAX_SIZE; j++) {
                    ItemStack itemstack2 = tools[j];
                    if (itemstack2 != null && itemstack2.getItem() != null
                        && itemstack2.getItem() instanceof ItemMotionSensor) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
