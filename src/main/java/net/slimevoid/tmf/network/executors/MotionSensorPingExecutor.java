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
package net.slimevoid.tmf.network.executors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.library.network.executor.PacketExecutor;
import net.slimevoid.tmf.api.IMotionSensor;
import net.slimevoid.tmf.core.lib.SoundLib;
import net.slimevoid.tmf.network.packets.PacketMotionSensor;

public class MotionSensorPingExecutor extends PacketExecutor {

    @Override
    public PacketUpdate execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketMotionSensor) {
            PacketMotionSensor packetMS = (PacketMotionSensor) packet;
            Entity entity = packetMS.getEntity(world);
            if (entity != null) {
                world.playSoundAtEntity(entity,
                                        SoundLib.TRACKER_PING, /* 0.5F */
                                        ((IMotionSensor) packetMS).getDist2sq(),
                                        ((IMotionSensor) packetMS).getDist2sq());
            }
        }
        return null;
    }

}
