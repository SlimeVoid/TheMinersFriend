package slimevoid.tmf.client.tickhandlers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slimevoid.tmf.items.ItemMotionSensor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class MotionSensorTickHandler implements ITickHandler {
	private final Minecraft mc;
	
	private int maxEntityDistance = 20;
	
	private int motionTicks = 0;
	private int maxTicks = 20;
	
	private Map<Entity,EntityPoint3f> closeEntities;
	private Map<Entity,EntityPoint3f> movedEntities;
	
	public MotionSensorTickHandler(int maxEntityDistance, int maxTicks) {
		this.mc = FMLClientHandler.instance().getClient();
		this.maxEntityDistance = maxEntityDistance;
		this.maxTicks = maxTicks;
		closeEntities = new HashMap<Entity,EntityPoint3f>();
		movedEntities = new HashMap<Entity,EntityPoint3f>();
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

	private static double get2dDistSq(Entity a, double x, double z) {
		double deltaX = x-a.posX;
		double deltaZ = z-a.posZ;
		
		return (deltaX*deltaX)+(deltaZ*deltaZ);
	}
	private static double get2dDistSqFrom3dDistSq(double distSq3d, double yA, double yB) {
		double deltaY = yB-yA;
		
		return distSq3d-(deltaY*deltaY);
	}
	private static double getAngleRadians(Entity a, double x, double z) {
		return Math.atan2(
				(x-a.posX), 
				(z-a.posZ)
		);
	}
	private static double deg2rad(double deg) {
		return (deg*2d*Math.PI)/360d;
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
		double motionTickProg = (double)motionTicks / (double)maxTicks;

		renderHUD(entityplayer);
		renderPings(
				entityplayer,
				motionTickProg
		);
		renderPong(
				entityplayer,
				motionTickProg
		);
		
		if (motionTicks >= maxTicks) {
			motionTicks = 0;
			
			doTickMotionSensor(
					entityplayer, 
					world, 
					itemstack
			);
		}
	}
	private void doTickMotionSensor(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		removeIrrelevantKnownEntities(entityplayer);
		
		checkEntities(entityplayer, world);

		onMotionSensorSensing(entityplayer, world, itemstack);
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
		movedEntities.clear();
		
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
			Entity closestEntity = null;
			double closestDistSq2d = 0;
			
			for (Entity entity : closestEntities) {
				//Within circular distance
				double distSq = entityplayer.getDistanceSqToEntity(entity);
				if ( distSq <= maxEntityDistance*maxEntityDistance ) {
					if ( hasEntityMoved(entityplayer, entity) ) {
						movedEntities.put(entity, closeEntities.get(entity));
					
						double distSq2d = get2dDistSqFrom3dDistSq(distSq, entityplayer.posY, entity.posY);
						if ( closestEntity == null || distSq2d < closestDistSq2d ) {
							closestEntity = entity;
							closestDistSq2d = distSq2d;
						}
					}
				}
			}
			
			if ( closestEntity != null ) {
				playSoundPing(world, closestDistSq2d);
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
	
	private void onMotionSensorSensing(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		playSoundPong(world);
	}

	private void renderHUD(EntityPlayer entityplayer) {
		System.out.println("renderHUD");
		// TODO: Render hud
	}
	private void renderPings(EntityPlayer entityplayer, double deltaTick) {
		double playerDeg = entityplayer.rotationYaw%360;
		if ( playerDeg < 0 )
			playerDeg = 360+playerDeg;
		
		double playerAngle = deg2rad(
				playerDeg
		);
		if ( playerAngle > Math.PI )
			playerAngle = (-2d*Math.PI)+playerAngle;
				
		for ( Entity entity: movedEntities.keySet() ) {
			double distSq2d = get2dDistSq(
					entityplayer,
					closeEntities.get(entity).x,
					closeEntities.get(entity).z
			);
			double angle = getAngleRadians(
					entityplayer,
					closeEntities.get(entity).x,
					closeEntities.get(entity).z
			);

			angle = angle+playerAngle;
			if ( angle > Math.PI )
				angle = (-2d*Math.PI)+angle;
			
			renderPoint(entityplayer, entity, deltaTick, angle, distSq2d);
		}
	}	
	private void renderPoint(EntityPlayer entityplayer, Entity entity, double deltaTick, double angle, double distSq2d) {
		System.out.println("renderPing:"+deltaTick+":"+distSq2d+":"+angle+": "+entity);
		// TODO: Render point
	}
	private void renderPong(EntityPlayer entityplayer, double deltaTick) {
		System.out.println("renderPong:"+deltaTick);
		// TODO: Render sweep
	}
	private void playSoundPing(World world, double distSq2d) {
		System.out.println("playSoundPing:"+distSq2d);
		// TODO: play ping
	}
	private void playSoundPong(World world) {
		System.out.println("playSoundPong");
		// TODO: play pong
		//world.playSoundAtEntity(entityplayer, "sounds.trackerping", 1, 1);
	}
	
	private class EntityPoint3f {
		double x;
		double y;
		double z;
	}
}