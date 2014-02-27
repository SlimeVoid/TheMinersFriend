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
package com.slimevoid.tmf.network.handlers;

import com.slimevoid.tmf.network.packets.PacketMining;
import com.slimevoid.tmf.network.packets.PacketMiningToolBelt;

import slimevoidlib.network.handlers.SubPacketHandler;

public class PacketMiningToolBeltHandler extends SubPacketHandler {

    @Override
    protected PacketMining createNewPacket() {
        return new PacketMiningToolBelt();
    }

}
