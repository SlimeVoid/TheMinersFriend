package slimevoid.tmf.network.packets;

import net.minecraft.world.World;

public class PacketOpenGuiToolbelt extends PacketMining {

	public PacketOpenGuiToolbelt(int packetId) {
		super(packetId);
	}

	@Override
	public boolean targetExists(World world) {
		// TODO Auto-generated method stub
		return false;
	}

}
