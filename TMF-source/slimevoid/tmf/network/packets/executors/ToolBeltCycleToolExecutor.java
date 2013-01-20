package slimevoid.tmf.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ToolBeltCycleToolExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketMiningToolBelt) {
			PacketMiningToolBelt packetTB = (PacketMiningToolBelt) packet;
			MiningToolBelt toolBelt = MiningToolBelt.getToolBeltDataFromId(entityplayer, world, packetTB.getToolBeltId());
			if (toolBelt != null) {
				toolBelt.cycleTool();
			}
		}
	}

}
