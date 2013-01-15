package slimevoid.tmf.network.handlers;

import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class PacketMiningToolBeltHandler extends SubPacketHandler {

	@Override
	protected PacketMining createNewPacket() {
		return new PacketMiningToolBelt();
	}

}
