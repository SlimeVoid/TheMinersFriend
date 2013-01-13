package slimevoid.tmf.client.network.handlers;

import slimevoid.tmf.network.handlers.SubPacketHandler;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientPacketMiningToolBeltHandler extends SubPacketHandler {

	@Override
	protected PacketMining createNewPacket() {
		return new PacketMiningToolBelt();
	}

}
