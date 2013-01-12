package slimevoid.tmf.network.handlers;

import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMotionSensor;

public class PacketMotionSensorHandler extends SubPacketHandler {

	@Override
	protected PacketMining createNewPacket() {
		return new PacketMotionSensor();
	}

}
