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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.slimevoid.library.IPacketExecutor;
import com.slimevoid.library.network.SlimevoidPayload;
import com.slimevoid.tmf.core.lib.MessageLib;
import com.slimevoid.tmf.network.packets.PacketMiningToolBelt;

public class ClientMiningModeDeactivatedExecutor implements IPacketExecutor {

    @Override
    public void execute(SlimevoidPayload packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketMiningToolBelt) {
            PacketMiningToolBelt packetMT = (PacketMiningToolBelt) packet;
            String message = StatCollector.translateToLocal(MessageLib.MINING_MODE_DEACTIVATED);
            entityplayer.addChatMessage(new ChatComponentText(message));
        }
    }

}
