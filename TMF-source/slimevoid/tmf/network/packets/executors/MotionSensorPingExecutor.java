package slimevoid.tmf.network.packets.executors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoid.tmf.api.IMotionSensor;
import slimevoid.tmf.api.IPacketExecutor;
import slimevoid.tmf.core.lib.SoundLib;
import slimevoid.tmf.network.packets.PacketMining;
import slimevoid.tmf.network.packets.PacketMotionSensor;

public class MotionSensorPingExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketMining packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketMotionSensor) {
			PacketMotionSensor packetMS = (PacketMotionSensor) packet;
			Entity entity = packetMS.getEntity(world);
			if (entity != null) {
				world.playSoundAtEntity(entity, SoundLib.TRACKER_PING, 0.5F, ((IMotionSensor)packetMS).getDist2sq());
			}
		}
	}

}
