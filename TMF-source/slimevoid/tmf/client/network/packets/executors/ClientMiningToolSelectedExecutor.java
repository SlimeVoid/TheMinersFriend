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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.core.lib.MessageLib;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;
import slimevoid.tmf.items.tools.data.MiningToolBelt;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientMiningToolSelectedExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world, EntityPlayer entityplayer) {
		if (packet instanceof PacketMiningToolBelt) {
			PacketMiningToolBelt packetMT = (PacketMiningToolBelt) packet;
			MiningToolBelt data = null;
			if (data != null) {
				ItemStack selectedTool = ItemMiningToolBelt.getSelectedTool(entityplayer.getHeldItem());
				if (selectedTool != null) {
					String message = StatCollector.translateToLocal(MessageLib.TOOL_SELECTED);
					entityplayer.addChatMessage(selectedTool.getDisplayName()
												+ " " + message);
				} else {
					entityplayer.addChatMessage(StatCollector.translateToLocal(MessageLib.TOOL_SELECTED_NONE));
				}
			} else {
				FMLCommonHandler.instance().getFMLLogger().warning("Unknown toolbelt was updated, client out of sync!");
			}
		}
	}

}
