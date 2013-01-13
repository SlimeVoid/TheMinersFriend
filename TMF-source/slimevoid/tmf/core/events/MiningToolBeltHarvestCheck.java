package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import slimevoid.tmf.items.ItemMiningToolbelt;

public class MiningToolBeltHarvestCheck {
	
	@ForgeSubscribe
	public void onHarvestCheck(HarvestCheck event) {
		ItemMiningToolbelt.doHarvestCheck(event);
	}

}
