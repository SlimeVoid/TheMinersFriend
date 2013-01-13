package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import slimevoid.tmf.core.data.MiningToolBelt;

public class MiningToolBeltHarvestCheck {
	
	@ForgeSubscribe
	public void onHarvestCheck(HarvestCheck event) {
		MiningToolBelt.doHarvestCheck(event);
	}

}
