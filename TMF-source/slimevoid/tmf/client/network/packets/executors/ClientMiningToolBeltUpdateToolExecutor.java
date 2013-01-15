package slimevoid.tmf.client.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientMiningToolBeltUpdateToolExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketMiningToolBelt) {
			PacketMiningToolBelt packetTB = (PacketMiningToolBelt) packet;
			MiningToolBelt toolBelt = MiningToolBelt.getToolBeltDataFromId(entityplayer, world, packetTB.getToolBeltId());
			if (toolBelt != null) {
				ItemStack tool = toolBelt.selectTool(packetTB.getSelectedTool());
				if (tool != null) {
					toolBelt.onInventoryChanged();
				}
			}
		}
	}

}
