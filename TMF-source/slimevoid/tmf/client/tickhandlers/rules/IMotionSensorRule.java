package slimevoid.tmf.client.tickhandlers.rules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IMotionSensorRule {

	public boolean doShowMotionSensor(EntityPlayer entityplayer, World world);
}
