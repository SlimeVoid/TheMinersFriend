package slimevoid.tmf.handlers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	
	private int maxEntityDistance = 20;
	
	private int motionTicks = 0;
	private int maxTicks = 20;
	
	private Map<Entity,EntityPoint3f> closeEntities;
	
	public MotionSensorTickHandler(int maxEntityDistance, int maxTicks) {
		this.mc = FMLClientHandler.instance().getClient();
		this.maxEntityDistance = maxEntityDistance;
		this.maxTicks = maxTicks;
		closeEntities = new HashMap<Entity,EntityPoint3f>();
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
				if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemMotionSensor) {
					onTickMotionSensor(entityplayer, world, itemstack);
				}
			}
		}
	}
	
	private void onTickMotionSensor(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		motionTicks++;
		if (motionTicks >= maxTicks) {
			doTickMotionSensor(entityplayer, world, itemstack);
			motionTicks = 0;
		}
	}
		
	private void doTickMotionSensor(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		removeIrrelevantKnownEntities(entityplayer);
		
		checkEntities(entityplayer, world);

		onMotionSensorSensing(entityplayer, world, itemstack);
		
		/*
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
			onEntityClose(entityplayer, world, itemstack);
		}*/
		
	}
	
	private void removeIrrelevantKnownEntities(EntityPlayer entityplayer) {
		List<Entity> noLongerRelevant = new ArrayList<Entity>();
		for ( Entity entity: closeEntities.keySet() ) {
			double knownNewDistSq = entityplayer.getDistanceSqToEntity(entity);
			if ( knownNewDistSq > maxEntityDistance*maxEntityDistance ) {
				noLongerRelevant.add(entity);
			}
		}
		for ( Entity entity: noLongerRelevant ) {
			closeEntities.remove(entity);
		}
	}
	
	private void checkEntities(EntityPlayer entityplayer, World world) {
		AxisAlignedBB AABB = AxisAlignedBB.getBoundingBox(
				entityplayer.posX - maxEntityDistance,
				entityplayer.posY - maxEntityDistance,
				entityplayer.posZ - maxEntityDistance,
				entityplayer.posX + maxEntityDistance,
				entityplayer.posY + maxEntityDistance,
				entityplayer.posZ + maxEntityDistance
		);
		List<Entity> closestEntities = world.getEntitiesWithinAABBExcludingEntity(entityplayer, AABB);
		
		if (closestEntities.size() > 0) {
			for (Entity entity : closestEntities) {
				//Within circular distance
				double distSq = entityplayer.getDistanceSqToEntity(entity);
				if ( distSq <= maxEntityDistance*maxEntityDistance ) {
					if ( hasEntityMoved(entityplayer, entity) )
						onEntityMoved(entityplayer, world, entity, distSq);
				}
			}
		}
	}
	private boolean hasEntityMoved(EntityPlayer entityplayer, Entity entity) {
		boolean entityKnown = false;
		boolean entityMoved = false;
		boolean entityNoLongerRelevant = false;
		if ( closeEntities.containsKey(entity) ) {
			entityKnown = true;
			
			double distMovedSq = entity.getDistanceSq(
					closeEntities.get(entity).x,
					closeEntities.get(entity).y,
					closeEntities.get(entity).z
			);
			if ( distMovedSq > 0 )
				entityMoved = true;
		}


		if ( entityKnown ) {
			if ( entityMoved ) {
				closeEntities.get(entity).x = entity.posX;
				closeEntities.get(entity).y = entity.posY;
				closeEntities.get(entity).z = entity.posZ;
			}
		} else {
			EntityPoint3f point = new EntityPoint3f();
			point.x = entity.posX;
			point.y = entity.posY;
			point.z = entity.posZ;
			closeEntities.put(entity, point);
		}
		
		return entityMoved || !entityKnown;
	}
	private void onEntityMoved(EntityPlayer entityplayer, World world, Entity entity, double distSq) {
		System.out.println(distSq+": "+entity);
	}
	
	private void onMotionSensorSensing(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		System.out.println("Motion Sensing");
		world.playSoundAtEntity(entityplayer, "sounds.trackerping", 1, 1);
	}
	
	private class EntityPoint3f {
		double x;
		double y;
		double z;
	}
}
