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
package com.slimevoid.tmf.network.packets;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class PacketMiningEntity extends PacketMining {

    public PacketMiningEntity(int packetId) {
        super(packetId);
    }

    public void setEntityId(int entityId) {
        this.payload.setIntPayload(0,
                                   entityId);
    }

    public int getEntityId() {
        return this.payload.getIntPayload(0);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.getEntityId());
    }

    @Override
    public boolean targetExists(World world) {
        return false;
    }

}
