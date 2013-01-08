package slimevoid.tmf.handlers;

import java.util.EnumSet;
import java.util.List;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.items.ItemMotionSensor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MotionSensorTickHandler implements ITickHandler {
	private final Minecraft mc;
	private static boolean entityClose = true;
	private static int entityClosingIn = 21;
	private static int maxEntityDistance = 20;
	private static int motionTicks = 0;
	private static int maxTicks = 20;
	
	public MotionSensorTickHandler() {
		mc = FMLClientHandler.instance().getClient();
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if ( type.equals(EnumSet.of(TickType.CLIENT)) ) {
			GuiScreen guiScreen = this.mc.currentScreen;
			if ( guiScreen == null )
				onTickInGame();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "MotionSensing";
	}
	private void onTickInGame() {
		EntityPlayer entityplayer = mc.thePlayer;
		World world = mc.theWorld;
		if ( entityplayer != null && entityplayer.inventory != null ) {
			for ( int i = 0; i < 9; i++ ) {
				ItemStack itemstack = entityplayer.inventory.mainInventory[i];
				if (itemstack != null) {
					Item item = itemstack.getItem();
					if ( item != null && item instanceof ItemMotionSensor) {
						motionTicks++;
						if (motionTicks >= maxTicks) {
							AxisAlignedBB AABB = AxisAlignedBB.getBoundingBox(
									entityplayer.posX - maxEntityDistance,
									entityplayer.posY - maxEntityDistance,
									entityplayer.posZ - maxEntityDistance,
									entityplayer.posX + maxEntityDistance,
									entityplayer.posY + maxEntityDistance,
									entityplayer.posZ + maxEntityDistance
							);
							List<Entity> closestEntities = world.getEntitiesWithinAABBExcludingEntity(entityplayer, AABB);
							int closestEntityDistance = maxEntityDistance;
							if (closestEntities.size() > 0) {
								entityClose = true;
								for (Entity entity : closestEntities) {
									// TODO : Add Entity to motion Sensor
									double closingIn = entityplayer.getDistanceToEntity(entity);
									if ((int)closingIn < closestEntityDistance) {
										closestEntityDistance = (int)closingIn;
									}
									//System.out.println("CloseEntity: " + entity.getEntityName() + " | Distance: " + closingIn);
									if ((int)closingIn < entityClosingIn) {
										entityClosingIn = (int)closingIn;
									}
								}
								if (closestEntityDistance >= entityClosingIn) {
									entityClosingIn = closestEntityDistance;
								}
							} else {
								entityClose = false;
								entityClosingIn = 21;
							}
							if (entityClose) {
								System.out.println(entityClosingIn);
								// TODO : Pulse and Ping
							}
							System.out.println("Motion Sensing");
							world.playSoundAtEntity(entityplayer, "sounds.trackerping", 1, 1);
							motionTicks = 0;
						}
					}
				}
			}
		}
	}
}
