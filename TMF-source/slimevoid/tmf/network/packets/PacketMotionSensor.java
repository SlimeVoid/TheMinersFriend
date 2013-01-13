package slimevoid.tmf.network.packets;

import net.minecraft.entity.Entity;
import slimevoid.lib.network.PacketPayload;
import slimevoid.tmf.api.IMotionSensor;
import slimevoid.tmf.lib.PacketLib;

public class PacketMotionSensor extends PacketMiningEntity implements IMotionSensor {
	
	public PacketMotionSensor(String command, Entity entity, int x, int y, int z, float dist2sq) {
		this();
		this.payload = new PacketPayload(1, 1, 0, 0);
		this.setCommand(command);
		this.setEntityId(entity.entityId);
		this.setPosition(x, y, z, 0);
		this.setDist2sq(dist2sq);
	}

	public PacketMotionSensor() {
		super(PacketLib.MOTION_SENSOR);
	}

	@Override
	public void setDist2sq(float dist2sq) {
		this.payload.setFloatPayload(0, dist2sq);
	}
	
	@Override
	public float getDist2sq() {
		return this.payload.getFloatPayload(0);
	}

}
