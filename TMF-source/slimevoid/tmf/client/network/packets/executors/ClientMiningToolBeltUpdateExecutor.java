package slimevoid.tmf.client.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.lib.DataLib;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientMiningToolBeltUpdateExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketMiningToolBelt) {
			PacketMiningToolBelt packetMT = (PacketMiningToolBelt) packet;
			MiningToolBeltData data = MiningToolBeltData.getToolBeltDataFromId(entityplayer, world, packetMT.getToolBeltId());
			if (data == null) {
				String worldIndex = MiningToolBeltData.getWorldIndexFromId(packetMT.getToolBeltId());
				data = new MiningToolBeltData(worldIndex);
				if (data != null) {
					world.setItemData(worldIndex, data);
				}
			}
			for (int slot = 0; slot < DataLib.TOOL_BELT_MAX_SIZE; slot++) {
				data.setInventorySlotContents(slot, packetMT.getToolInSlot(slot));
			}
			data.onInventoryChanged();
		}
	}

}
