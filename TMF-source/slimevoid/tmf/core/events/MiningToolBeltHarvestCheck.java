package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import slimevoid.tmf.tools.items.ItemMiningToolBelt;

public class MiningToolBeltHarvestCheck {
	
	@ForgeSubscribe
	public void onHarvestCheck(HarvestCheck event) {
		//if (!event.entityPlayer.worldObj.isRemote) {
			ItemMiningToolBelt.doHarvestCheck(event);
		//}
	}

}
