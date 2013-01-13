package slimevoid.tmf.client.tickhandlers.rules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.items.ItemMotionSensor;

public class MotionSensorRuleOnHotbar implements IMotionSensorRule {

	@Override
	public boolean doShowMotionSensor(EntityPlayer entityplayer, World world) {
		if ( entityplayer != null && entityplayer.inventory != null ) {
			for ( int i = 0; i < 9; i++ ) {
				ItemStack itemstack = entityplayer.inventory.mainInventory[i];
				if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemMotionSensor) {
					return true;
				}
			}
		}
		return false;
	}
}
