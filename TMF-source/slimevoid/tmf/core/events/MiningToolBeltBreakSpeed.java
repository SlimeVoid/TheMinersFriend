package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import slimevoid.tmf.tools.items.ItemMiningToolBelt;

public class MiningToolBeltBreakSpeed {
	
	@ForgeSubscribe
	public void onBreakSpeed(BreakSpeed event) {
		//if (!event.entityPlayer.worldObj.isRemote) {
			ItemMiningToolBelt.doBreakSpeed(event);
		//}
	}
}
