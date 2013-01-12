package slimevoid.tmf.client.network.handlers;

import slimevoid.tmf.network.handlers.SubPacketHandler;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMotionSensor;

public class ClientPacketMotionSensorHandler extends SubPacketHandler {

	@Override
	protected PacketMining createNewPacket() {
		return new PacketMotionSensor();
	}

}
