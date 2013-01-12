package slimevoid.tmf.network.packets;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import slimevoid.lib.entity.EntityHelper;

public abstract class PacketMiningEntity extends PacketMining {

	public PacketMiningEntity(int packetId) {
		super(packetId);
	}

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(0, entityId);
	}
	
	public int getEntityId() {
		return this.payload.getIntPayload(0);
	}
	
	public Entity getEntity() {
		return EntityHelper.getEntityByID(this.getEntityId());
	}
	
	@Override
	public boolean targetExists(World world) {
		return false;
	}

}