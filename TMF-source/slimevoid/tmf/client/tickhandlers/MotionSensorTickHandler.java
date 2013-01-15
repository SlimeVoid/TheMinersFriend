package slimevoid.tmf.client.tickhandlers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.client.tickhandlers.rules.IMotionSensorRule;
import slimevoid.tmf.core.lib.CommandLib;
import slimevoid.tmf.network.packets.PacketMotionSensor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MotionSensorTickHandler implements ITickHandler {
	private final Minecraft mc;
	
	private int maxEntityDistance;
	
	private int motionTicks = 0;
	private int maxTicks;
	
	private Map<Entity,EntityPoint3f> closeEntities;
	private Map<Entity,EntityPoint3f> movedEntities;
	private EntityPoint3f lastPlayerPos;
	
	private boolean drawOnRight;
	
	private List<IMotionSensorRule> rules;
	
	public MotionSensorTickHandler(int maxEntityDistance, int maxTicks, boolean drawOnRight) {
		this.mc = FMLClientHandler.instance().getClient();
		this.maxEntityDistance = maxEntityDistance;
		this.maxTicks = maxTicks;
		this.drawOnRight = drawOnRight;
		closeEntities = new HashMap<Entity,EntityPoint3f>();
		movedEntities = new HashMap<Entity,EntityPoint3f>();
		lastPlayerPos = new EntityPoint3f();
		rules = new ArrayList<IMotionSensorRule>();
	}
	
	public void addRule(IMotionSensorRule rule) {
		rules.add(rule);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if ( 
				type.equals(EnumSet.of(TickType.CLIENT)) ||
				type.equals(EnumSet.of(TickType.RENDER))
				
		) {
			EntityPlayer entityplayer = mc.thePlayer;
			World world = mc.theWorld;
			
			boolean doTick = false;
			for ( IMotionSensorRule rule: rules ) {
				if ( rule.doShowMotionSensor(entityplayer, world) )
					doTick = true;
			}
			
			if ( doTick ) {
				if ( type.equals(EnumSet.of(TickType.CLIENT)) ) {
					GuiScreen guiScreen = this.mc.currentScreen;
					if ( guiScreen == null )
						onTickInGame(entityplayer, world);
				} else if ( type.equals(EnumSet.of(TickType.RENDER)) ) {
					GuiScreen guiScreen = this.mc.currentScreen;
					if ( guiScreen == null )
						onRenderTick(entityplayer);
				}
			}
		}
	}
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT,TickType.RENDER);
	}
	@Override
	public String getLabel() {
		return "MotionSensing";
	}

	private static double get2dDistSq(Entity a, double x, double z) {
		return get2dDistSq(a.posX,a.posZ,x,z);
	}
	private static double get2dDistSq(double x1, double z1, double x2, double z2) {
		double deltaX = x2-x1;
		double deltaZ = z2-z1;
		
		return (deltaX*deltaX)+(deltaZ*deltaZ);
	}
	private static double get2dDistSqFrom3dDistSq(double distSq3d, double yA, double yB) {
		double deltaY = yB-yA;
		
		return distSq3d-(deltaY*deltaY);
	}
	private static double getAngleRadians(Entity a, double x, double z) {
		return getAngleRadians(a.posX,a.posZ,x,z);
	}
	private static double getAngleRadians(double x1, double z1, double x2, double z2) {
		return Math.atan2(
				(x2-x1), 
				(z2-z1)
		);
	}
	private static double deg2rad(double deg) {
		return (deg*2d*Math.PI)/360d;
	}
	private static double rad2deg(double rad) {
		return (rad*360d)/(2d*Math.PI);
	}
	
	private void onTickInGame(EntityPlayer entityplayer, World world) {
		if (motionTicks == Math.abs(maxTicks / 2)) {
			doTickMotionSensor(
					entityplayer, 
					world
			);
		}
		motionTicks++;
		if (motionTicks >= maxTicks) {
			onMotionSensorSensing(entityplayer, world);
			motionTicks = 0;
		}
	}
	private void doTickMotionSensor(EntityPlayer entityplayer, World world) {
		lastPlayerPos.x = entityplayer.posX;
		lastPlayerPos.y = entityplayer.posY;
		lastPlayerPos.z = entityplayer.posZ;
		
		removeIrrelevantKnownEntities(entityplayer);
		
		checkEntities(entityplayer, world);
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

			double playerDeg = entityplayer.rotationYaw%360;
			if ( playerDeg < 0 )
				playerDeg = 360+playerDeg;
			
			double playerAngle = deg2rad(
					playerDeg
			);
			if ( playerAngle > Math.PI )
				playerAngle = (-2d*Math.PI)+playerAngle;
			
			for (Entity entity : closestEntities) {
				//Within circular distance
				double distSq = entityplayer.getDistanceSqToEntity(entity);
				if ( distSq <= maxEntityDistance*maxEntityDistance ) {
					if ( hasEntityMoved(entityplayer, entity) ) {
						double angle = getAngleRadians(
								entityplayer,
								closeEntities.get(entity).x,
								closeEntities.get(entity).z
						)*-1;
						
						angle = angle-playerAngle;
						
						if ( angle > Math.PI )
							angle = (-2d*Math.PI)+angle;
						
						if ( angle < -Math.PI )
							angle += 2d*Math.PI;
						if ( angle > Math.PI )
							angle -= 2d*Math.PI;
						
						
						if ( angle > (-Math.PI/2) && angle < (Math.PI/2) ) {
							movedEntities.put(entity, closeEntities.get(entity));
							
							double distSq2d = get2dDistSqFrom3dDistSq(distSq, entityplayer.posY, entity.posY);
							if ( closestEntity == null || distSq2d < closestDistSq2d ) {
								closestEntity = entity;
								closestDistSq2d = distSq2d;
							}
						}
					}
				}
			}
			
			if ( closestEntity != null ) {
				playSoundPing(entityplayer, world, closestDistSq2d);
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
	
	private void onMotionSensorSensing(EntityPlayer entityplayer, World world) {
		playSoundSweep(entityplayer, world);
	}
	
	private void onRenderTick(EntityPlayer entityplayer) {
		double motionTickProg = (double)motionTicks / (double)maxTicks;
		renderHUD(entityplayer);
		renderPings(
				entityplayer,
				motionTickProg
		);
		renderSweep(
				entityplayer,
				motionTickProg
		);
	}
	private void renderSprite(int x, int y, int u, int v, int width, int height, String texture, float alpha) {
		int tex = mc.renderEngine.getTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		mc.renderEngine.bindTexture(tex);
		float scalex = 0.00390625F*2;
		float scaley = 0.00390625F*2;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
			var9.addVertexWithUV(x + 0, y + height, 0, (u + 0) * scalex, (v + height) * scaley);
			var9.addVertexWithUV(x + width, y + height, 0, (u + width) * scalex, (v + height) * scaley);
			var9.addVertexWithUV(x + width, y + 0, 0, (u + width) * scalex, (v + 0) * scaley);
			var9.addVertexWithUV(x + 0, y + 0, 0, (u + 0) * scalex, (v + 0) * scaley);
		var9.draw();
	}
	private void renderHUD(EntityPlayer entityplayer) {
		double playerDeg = entityplayer.rotationYaw%360;
		if ( playerDeg < 0 )
			playerDeg = 360+playerDeg;
		playerDeg *= -1; 
				
		float opacity = 0.5f;
		
		GL11.glPushMatrix();
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GL11.glClear(256);
			
			GL11.glPushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				if ( drawOnRight ) {
					GL11.glTranslatef(
							sr.getScaledWidth()-60,
							sr.getScaledHeight(),
							0
					);
				} else {
					GL11.glTranslatef(
							60,
							sr.getScaledHeight(),
							0
					);
				}
				GL11.glRotatef(
						(float) playerDeg, 
						0,
						0,
						1.0f
				);
				
				renderSprite(
						-64,
						-64,
						0,
						0,
						128,
						128,
						"/TheMinersFriend/tracker/trackerBG.png",
						1
				);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
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
					lastPlayerPos.x,
					lastPlayerPos.z,
					closeEntities.get(entity).x,
					closeEntities.get(entity).z
			);
			double angle = getAngleRadians(
					lastPlayerPos.x,
					lastPlayerPos.z,
					closeEntities.get(entity).x,
					closeEntities.get(entity).z
			)*-1;
			
			angle = angle-playerAngle;
			if ( angle > Math.PI )
				angle = (-2d*Math.PI)+angle;

			if ( angle < -Math.PI )
				angle += 2d*Math.PI;
			if ( angle > Math.PI )
				angle -= 2d*Math.PI;
			
			renderPing(deltaTick, angle, distSq2d);
		}
	}	
	private void renderPing(double deltaTick, double angle, double distSq2d) {
		float opacity = 0.5f;
		
		GL11.glPushMatrix();
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GL11.glClear(256);
			
			GL11.glPushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				
				if ( drawOnRight ) {
					GL11.glTranslatef(
							sr.getScaledWidth()-60,
							sr.getScaledHeight(),
							0
					);
				} else {
					GL11.glTranslatef(
							60,
							sr.getScaledHeight(),
							0
					);
				}
				double angdeg = rad2deg(angle);
				angdeg = angdeg - 90;
				
				GL11.glRotatef((float) angdeg, 0, 0, 1);
				
				GL11.glTranslatef(
						(float) Math.sqrt(distSq2d)*3,
						0,
						0
				);
				GL11.glScalef(0.0625f, 0.0625f, 1);
				
				renderSprite(
						-64,
						-64,
						0,
						0,
						128,
						128,
						"/TheMinersFriend/tracker/contact.png",
						(float) (0.4d+Math.log(3.2d-deltaTick*3d)*0.6d)
				);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	private void renderSweep(EntityPlayer entityplayer, double deltaTick) {				
		float opacity = 0.5f;
		
		GL11.glPushMatrix();
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GL11.glClear(256);
			
			GL11.glPushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				if ( drawOnRight ) {
					GL11.glTranslatef(
							sr.getScaledWidth()-60,
							sr.getScaledHeight(),
							0
					);
				} else {
					GL11.glTranslatef(
							60,
							sr.getScaledHeight(),
							0
					);
				}
				GL11.glScalef(
						(float)deltaTick, 
						(float)deltaTick,
						1
				);
				
				renderSprite(
						-64,
						-64,
						0,
						0,
						128,
						128,
						"/TheMinersFriend/tracker/trackerSweep.png",
						(float) (1d-deltaTick/2d)
				);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	private void playSoundSweep(EntityPlayer entityplayer, World world) {
		PacketDispatcher.sendPacketToServer(
				(new PacketMotionSensor(
						CommandLib.PLAY_MOTION_SWEEP,
						entityplayer, 
						(int)entityplayer.posX,
						(int)entityplayer.posY,
						(int)entityplayer.posZ,
						1.0F
				)).getPacket()
		);
	}
	private void playSoundPing(EntityPlayer entityplayer, World world, double distSq2d) {
		PacketDispatcher.sendPacketToServer(
				(new PacketMotionSensor(
						CommandLib.PLAY_MOTION_PING,
						entityplayer, 
						(int)entityplayer.posX,
						(int)entityplayer.posY,
						(int)entityplayer.posZ,
						getPingPitch(distSq2d)
				)).getPacket()
		);
	}
	
	private float getPingPitch(double distSq2d) {
		System.out.println("Distance: " + distSq2d);
		int maxDistSq = maxEntityDistance*maxEntityDistance;
		System.out.println("MaxDistanceSq: " + maxDistSq);
		float pitch = (float)(distSq2d/(maxEntityDistance*maxEntityDistance));
		System.out.println("Pitch: " + pitch);
		float absolutePitch =  1F - pitch; 
		System.out.println("AbsPitch: " + absolutePitch);
		return absolutePitch;
	}

	private class EntityPoint3f {
		double x;
		double y;
		double z;
	}
}