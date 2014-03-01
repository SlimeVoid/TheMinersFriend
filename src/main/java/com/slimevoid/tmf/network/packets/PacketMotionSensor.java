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

import com.slimevoid.library.network.PacketPayload;
import com.slimevoid.tmf.api.IMotionSensor;
import com.slimevoid.tmf.core.lib.PacketLib;

import net.minecraft.entity.Entity;

public class PacketMotionSensor extends PacketMiningEntity implements
        IMotionSensor {

    public PacketMotionSensor(String command, Entity entity, int x, int y, int z, float dist2sq) {
        this();
        this.payload = new PacketPayload(1, 1, 0, 0);
        this.setCommand(command);
        this.setEntityId(entity.entityId);
        this.setPosition(x,
                         y,
                         z,
                         0);
        this.setDist2sq(dist2sq);
    }

    public PacketMotionSensor() {
        super(PacketLib.MOTION_SENSOR);
    }

    @Override
    public void setDist2sq(float dist2sq) {
        this.payload.setFloatPayload(0,
                                     dist2sq);
    }

    @Override
    public float getDist2sq() {
        return this.payload.getFloatPayload(0);
    }

}
