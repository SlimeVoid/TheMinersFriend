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
package com.slimevoid.tmf.client.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.slimevoid.library.IPacketExecutor;
import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientMiningToolSelectedExecutor implements IPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketMiningToolBelt) {
            // PacketMiningToolBelt packetMT = (PacketMiningToolBelt) packet;
            // InventoryMiningToolBelt data = null;
            // if (data != null) {
            // ItemStack selectedTool =
            // ItemMiningToolBelt.getSelectedTool(entityplayer.getHeldItem());
            // if (selectedTool != null) {
            // String message =
            // StatCollector.translateToLocal(MessageLib.TOOL_SELECTED);
            // entityplayer.addChatMessage(selectedTool.getDisplayName()
            // + " " + message);
            // } else {
            // entityplayer.addChatMessage(StatCollector.translateToLocal(MessageLib.TOOL_SELECTED_NONE));
            // }
            // } else {
            // FMLCommonHandler.instance().getFMLLogger().warning("Unknown toolbelt was updated, client out of sync!");
            // }
        }
    }

}
