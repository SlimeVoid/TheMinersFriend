package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import slimevoid.tmf.core.data.MiningToolBelt;

public class MiningToolBeltBreakSpeed {

	
	@ForgeSubscribe
	public void onBreakSpeed(BreakSpeed event) {
		MiningToolBelt.doBreakSpeed(event);		
	}
}
