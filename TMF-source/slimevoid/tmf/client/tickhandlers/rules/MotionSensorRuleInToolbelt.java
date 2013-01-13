package slimevoid.tmf.client.tickhandlers.rules;

import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.items.ItemMiningToolbelt;
import slimevoid.tmf.items.ItemMotionSensor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MotionSensorRuleInToolbelt implements IMotionSensorRule {

	@Override
	public boolean doShowMotionSensor(EntityPlayer entityplayer, World world) {
		if ( entityplayer != null && entityplayer.inventory != null ) {
			for ( int i = 0; i < 9; i++ ) {
				ItemStack itemstack = entityplayer.inventory.mainInventory[i];
				if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemMiningToolbelt ) {
					MiningToolBeltData data = MiningToolBeltData.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
					if ( data != null ) {
						for ( int j = 0; j < data.getSizeInventory(); j++ ) {
							ItemStack itemstack2 = data.getStackInSlot(j);
							if (itemstack2 != null && itemstack2.getItem() != null && itemstack2.getItem() instanceof ItemMotionSensor ) {
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
