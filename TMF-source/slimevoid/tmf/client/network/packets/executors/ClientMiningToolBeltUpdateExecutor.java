/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.tmf.client.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientMiningToolBeltUpdateExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world, EntityPlayer entityplayer) {
		if (packet instanceof PacketMiningToolBelt) {
			PacketMiningToolBelt packetMT = (PacketMiningToolBelt) packet;
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromId(	entityplayer,
																		world,
																		packetMT.getToolBeltId());
			if (data == null) {
				String worldIndex = MiningToolBelt.getWorldIndexFromId(packetMT.getToolBeltId());
				data = new MiningToolBelt(worldIndex);
				if (data != null) {
					world.setItemData(	worldIndex,
										data);
					data.setToolBeltId(packetMT.getToolBeltId());
				}
			}
			for (int slot = 0; slot < DataLib.TOOL_BELT_MAX_SIZE; slot++) {
				data.setInventorySlotContents(	slot,
												packetMT.getToolInSlot(slot));
			}
			ItemStack tool = data.selectTool(	world,
												entityplayer,
												packetMT.getSelectedTool());
			data.onInventoryChanged();
		}
	}

}
