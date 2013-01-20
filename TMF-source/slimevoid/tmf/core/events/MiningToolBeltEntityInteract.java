package slimevoid.tmf.core.events;

import slimevoid.tmf.items.ItemMiningToolBelt;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class MiningToolBeltEntityInteract {
	
	@ForgeSubscribe
	public void onPlayerInteract(EntityInteractEvent event) {
		event.setCanceled(ItemMiningToolBelt.doEntityInteract(event));
	}

}
