package slimevoid.tmf.core.events;

import slimevoid.tmf.core.data.MiningToolBelt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class MiningToolBeltBreakSpeed {

	
	@ForgeSubscribe
	public void onBreakSpeed(BreakSpeed event) {
		MiningToolBelt.doBreakSpeed(event);		
	}
}
