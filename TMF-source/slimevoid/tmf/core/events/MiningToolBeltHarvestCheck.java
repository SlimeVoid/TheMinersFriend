package slimevoid.tmf.core.events;

import slimevoid.tmf.core.data.MiningToolBelt;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;

public class MiningToolBeltHarvestCheck {
	
	@ForgeSubscribe
	public void onHarvestCheck(HarvestCheck event) {
		MiningToolBelt.doHarvestCheck(event);
	}

}
