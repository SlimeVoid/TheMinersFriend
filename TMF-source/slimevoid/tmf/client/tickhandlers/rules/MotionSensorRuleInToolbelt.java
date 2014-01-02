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
package slimevoid.tmf.client.tickhandlers.rules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;
import slimevoid.tmf.items.tools.ItemMotionSensor;

public class MotionSensorRuleInToolbelt implements IMotionSensorRule {

	@Override
	public boolean doShowMotionSensor(EntityPlayer entityplayer, World world) {
		if (entityplayer != null && entityplayer.inventory != null) {
			for (int i = 0; i < 9; i++) {
				ItemStack itemstack = entityplayer.inventory.mainInventory[i];
				if (itemstack != null && itemstack.getItem() != null
					&& itemstack.getItem() instanceof ItemMiningToolBelt) {
					MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(	entityplayer,
																						world,
																						itemstack);
					if (data != null) {
						for (int j = 0; j < data.getSizeInventory(); j++) {
							ItemStack itemstack2 = data.getStackInSlot(j);
							if (itemstack2 != null
								&& itemstack2.getItem() != null
								&& itemstack2.getItem() instanceof ItemMotionSensor) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
