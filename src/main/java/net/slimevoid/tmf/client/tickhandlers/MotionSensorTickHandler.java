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
package net.slimevoid.tmf.client.tickhandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.tmf.core.lib.PacketLib;
import org.lwjgl.opengl.GL11;

import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.tmf.api.IMotionSensorRule;
import net.slimevoid.tmf.core.lib.CommandLib;
import net.slimevoid.tmf.core.lib.ResourceLib;
import net.slimevoid.tmf.network.packets.PacketMotionSensor;

@SideOnly(Side.CLIENT)
public class MotionSensorTickHandler {
    private final Minecraft            mc;

    private int                        maxEntityDistance;

    private int                        motionTicks = 0;
    private int                        maxTicks;

    private Map<Entity, EntityPoint3f> closeEntities;
    private Map<Entity, EntityPoint3f> movedEntities;
    private EntityPoint3f              lastPlayerPos;

    private boolean                    drawOnRight;

    private List<IMotionSensorRule>    rules;

    public MotionSensorTickHandler(int maxEntityDistance, int maxTicks, boolean drawOnRight) {
        this.mc = FMLClientHandler.instance().getClient();
        this.maxEntityDistance = maxEntityDistance;
        this.maxTicks = maxTicks;
        this.drawOnRight = drawOnRight;
        closeEntities = new HashMap<Entity, EntityPoint3f>();
        movedEntities = new HashMap<Entity, EntityPoint3f>();
        lastPlayerPos = new EntityPoint3f();
        rules = new ArrayList<IMotionSensorRule>();
    }

    public void addRule(IMotionSensorRule rule) {
        rules.add(rule);
    }

    @SubscribeEvent
    public void onSensorClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayer entityplayer = mc.thePlayer;
        World world = mc.theWorld;

        if (this.shouldTick(entityplayer,
                            world)) {
            GuiScreen guiScreen = this.mc.currentScreen;
            if (guiScreen == null) onTickInGame(entityplayer,
                                                world);
        }
    }

    private boolean shouldTick(EntityPlayer entityplayer, World world) {
        boolean doTick = false;
        for (IMotionSensorRule rule : rules) {
            if (rule.doShowMotionSensor(entityplayer,
                                        world)) doTick = true;
        }
        return doTick;
    }

    @SubscribeEvent
    public void onSensorRenderTick(TickEvent.RenderTickEvent event) {
        EntityPlayer entityplayer = mc.thePlayer;
        World world = mc.theWorld;

        GuiScreen guiScreen = this.mc.currentScreen;
        if (guiScreen == null) onRenderTick(entityplayer);
    }

    private static double get2dDistSq(Entity a, double x, double z) {
        return get2dDistSq(a.posX,
                           a.posZ,
                           x,
                           z);
    }

    private static double get2dDistSq(double x1, double z1, double x2, double z2) {
        double deltaX = x2 - x1;
        double deltaZ = z2 - z1;

        return (deltaX * deltaX) + (deltaZ * deltaZ);
    }

    private static double get2dDistSqFrom3dDistSq(double distSq3d, double yA, double yB) {
        double deltaY = yB - yA;

        return distSq3d - (deltaY * deltaY);
    }

    private static double getAngleRadians(Entity a, double x, double z) {
        return getAngleRadians(a.posX,
                               a.posZ,
                               x,
                               z);
    }

    private static double getAngleRadians(double x1, double z1, double x2, double z2) {
        return Math.atan2((x2 - x1),
                          (z2 - z1));
    }

    private static double deg2rad(double deg) {
        return (deg * 2d * Math.PI) / 360d;
    }

    private static double rad2deg(double rad) {
        return (rad * 360d) / (2d * Math.PI);
    }

    private void onTickInGame(EntityPlayer entityplayer, World world) {
        if (motionTicks == Math.abs(maxTicks / 2)) {
            doTickMotionSensor(entityplayer,
                               world);
        }
        motionTicks++;
        if (motionTicks >= maxTicks) {
            onMotionSensorSensing(entityplayer,
                                  world);
            motionTicks = 0;
        }
    }

    private void doTickMotionSensor(EntityPlayer entityplayer, World world) {
        lastPlayerPos.x = entityplayer.posX;
        lastPlayerPos.y = entityplayer.posY;
        lastPlayerPos.z = entityplayer.posZ;

        removeIrrelevantKnownEntities(entityplayer);

        checkEntities(entityplayer,
                      world);
    }

    private void removeIrrelevantKnownEntities(EntityPlayer entityplayer) {
        List<Entity> noLongerRelevant = new ArrayList<Entity>();
        for (Entity entity : closeEntities.keySet()) {
            double knownNewDistSq = entityplayer.getDistanceSqToEntity(entity);
            if (knownNewDistSq > maxEntityDistance * maxEntityDistance) {
                noLongerRelevant.add(entity);
            }
        }
        for (Entity entity : noLongerRelevant) {
            closeEntities.remove(entity);
        }
    }

    private void checkEntities(EntityPlayer entityplayer, World world) {
        movedEntities.clear();

        AxisAlignedBB AABB = AxisAlignedBB.fromBounds(entityplayer.posX
                        - maxEntityDistance,
                entityplayer.posY
                        - maxEntityDistance,
                entityplayer.posZ
                        - maxEntityDistance,
                entityplayer.posX
                        + maxEntityDistance,
                entityplayer.posY
                        + maxEntityDistance,
                entityplayer.posZ
                        + maxEntityDistance);
        List<Entity> closestEntities = world.getEntitiesWithinAABBExcludingEntity(entityplayer,
                                                                                  AABB);

        if (closestEntities.size() > 0) {
            Entity closestEntity = null;
            double closestDistSq2d = 0;

            double playerDeg = entityplayer.rotationYaw % 360;
            if (playerDeg < 0) playerDeg = 360 + playerDeg;

            double playerAngle = deg2rad(playerDeg);
            if (playerAngle > Math.PI) playerAngle = (-2d * Math.PI)
                                                     + playerAngle;

            for (Entity entity : closestEntities) {
                // Within circular distance
                double distSq = entityplayer.getDistanceSqToEntity(entity);
                if (distSq <= maxEntityDistance * maxEntityDistance) {
                    if (hasEntityMoved(entityplayer,
                                       entity)) {
                        double angle = getAngleRadians(entityplayer,
                                                       closeEntities.get(entity).x,
                                                       closeEntities.get(entity).z)
                                       * -1;

                        angle = angle - playerAngle;

                        if (angle > Math.PI) angle = (-2d * Math.PI) + angle;

                        if (angle < -Math.PI) angle += 2d * Math.PI;
                        if (angle > Math.PI) angle -= 2d * Math.PI;

                        if (angle > (-Math.PI / 2) && angle < (Math.PI / 2)) {
                            movedEntities.put(entity,
                                              closeEntities.get(entity));

                            double distSq2d = get2dDistSqFrom3dDistSq(distSq,
                                                                      entityplayer.posY,
                                                                      entity.posY);
                            if (closestEntity == null
                                || distSq2d < closestDistSq2d) {
                                closestEntity = entity;
                                closestDistSq2d = distSq2d;
                            }
                        }
                    }
                }
            }

            if (closestEntity != null) {
                PacketLib.playSoundPing(
                        entityplayer,
                        world,
                        entityplayer.getPosition(),
                        getPingPitch(closestDistSq2d));
            }
        }
    }

    private boolean hasEntityMoved(EntityPlayer entityplayer, Entity entity) {
        boolean entityKnown = false;
        boolean entityMoved = false;
        boolean entityNoLongerRelevant = false;
        if (closeEntities.containsKey(entity)) {
            entityKnown = true;

            double distMovedSq = entity.getDistanceSq(closeEntities.get(entity).x,
                                                      closeEntities.get(entity).y,
                                                      closeEntities.get(entity).z);
            if (distMovedSq > 0) entityMoved = true;
        }

        if (entityKnown) {
            if (entityMoved) {
                closeEntities.get(entity).x = entity.posX;
                closeEntities.get(entity).y = entity.posY;
                closeEntities.get(entity).z = entity.posZ;
            }
        } else {
            EntityPoint3f point = new EntityPoint3f();
            point.x = entity.posX;
            point.y = entity.posY;
            point.z = entity.posZ;
            closeEntities.put(entity,
                              point);
        }

        return entityMoved || !entityKnown;
    }

    private void onMotionSensorSensing(EntityPlayer entityplayer, World world) {
        PacketLib.playSoundSweep(
                entityplayer,
                world,
                entityplayer.getPosition());
    }

    private void onRenderTick(EntityPlayer entityplayer) {
        double motionTickProg = (double) motionTicks / (double) maxTicks;
        renderHUD(entityplayer);
        renderPings(entityplayer,
                    motionTickProg);
        renderSweep(entityplayer,
                    motionTickProg);
    }

    private void renderSprite(int x, int y, int u, int v, int width, int height, ResourceLocation texture, float alpha) {
        GL11.glColor4f(1.0F,
                       1.0F,
                       1.0F,
                       alpha);
        mc.renderEngine.bindTexture(texture);
        float scalex = 0.00390625F * 2;
        float scaley = 0.00390625F * 2;
        Tessellator var9 = Tessellator.getInstance();
        var9.getWorldRenderer().startDrawingQuads();
        var9.getWorldRenderer().addVertexWithUV(
                x + 0,
                y + height,
                0,
                (u + 0) * scalex,
                (v + height) * scaley);
        var9.getWorldRenderer().addVertexWithUV(
                x + width,
                y + height,
                0,
                (u + width) * scalex,
                (v + height) * scaley);
        var9.getWorldRenderer().addVertexWithUV(
                x + width,
                y + 0,
                0,
                (u + width) * scalex,
                (v + 0) * scaley);
        var9.getWorldRenderer().addVertexWithUV(
                x + 0,
                y + 0,
                0,
                (u + 0) * scalex,
                (v + 0) * scaley);
        var9.getWorldRenderer().finishDrawing();
    }

    private void renderHUD(EntityPlayer entityplayer) {
        double playerDeg = entityplayer.rotationYaw % 360;
        if (playerDeg < 0) playerDeg = 360 + playerDeg;
        playerDeg *= -1;

        float opacity = 0.5f;

        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);

        GL11.glPushMatrix();
        // RenderHelper.enableGUIStandardItemLighting();
        // GL11.glDisable(GL11.GL_LIGHTING);
        // GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (drawOnRight) {
            GL11.glTranslatef(sr.getScaledWidth() - 60,
                              sr.getScaledHeight(),
                              0);
        } else {
            GL11.glTranslatef(60,
                              sr.getScaledHeight(),
                              0);
        }
        GL11.glRotatef((float) playerDeg,
                0,
                0,
                1.0f);

        renderSprite(-64,
                     -64,
                     0,
                     0,
                     128,
                     128,
                     ResourceLib.TRACKER_BG,
                     1);
        // GL11.glEnable(GL11.GL_LIGHTING);
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void renderPings(EntityPlayer entityplayer, double deltaTick) {
        double playerDeg = entityplayer.rotationYaw % 360;
        if (playerDeg < 0) playerDeg = 360 + playerDeg;

        double playerAngle = deg2rad(playerDeg);
        if (playerAngle > Math.PI) playerAngle = (-2d * Math.PI) + playerAngle;

        for (Entity entity : movedEntities.keySet()) {
            double distSq2d = get2dDistSq(lastPlayerPos.x,
                                          lastPlayerPos.z,
                                          closeEntities.get(entity).x,
                                          closeEntities.get(entity).z);
            double angle = getAngleRadians(lastPlayerPos.x,
                                           lastPlayerPos.z,
                                           closeEntities.get(entity).x,
                                           closeEntities.get(entity).z) * -1;

            angle = angle - playerAngle;
            if (angle > Math.PI) angle = (-2d * Math.PI) + angle;

            if (angle < -Math.PI) angle += 2d * Math.PI;
            if (angle > Math.PI) angle -= 2d * Math.PI;

            renderPing(deltaTick,
                       angle,
                       distSq2d);
        }
    }

    private void renderPing(double deltaTick, double angle, double distSq2d) {
        float opacity = 0.5f;

        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);

        GL11.glPushMatrix();
        // RenderHelper.enableGUIStandardItemLighting();
        // GL11.glDisable(GL11.GL_LIGHTING);
        // GL11.glDisable(GL11.GL_DEPTH_TEST);

        if (drawOnRight) {
            GL11.glTranslatef(sr.getScaledWidth() - 60,
                              sr.getScaledHeight(),
                              0);
        } else {
            GL11.glTranslatef(60,
                              sr.getScaledHeight(),
                              0);
        }
        double angdeg = rad2deg(angle);
        angdeg = angdeg - 90;

        GL11.glRotatef((float) angdeg,
                       0,
                       0,
                       1);

        GL11.glTranslatef((float) Math.sqrt(distSq2d) * 3,
                          0,
                          0);
        GL11.glScalef(0.0625f,
                      0.0625f,
                      1);

        renderSprite(-64,
                     -64,
                     0,
                     0,
                     128,
                     128,
                     ResourceLib.TRACKER_CONTACT,
                     (float) (0.4d + Math.log(3.2d - deltaTick * 3d) * 0.6d));
        // GL11.glEnable(GL11.GL_LIGHTING);
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void renderSweep(EntityPlayer entityplayer, double deltaTick) {
        float opacity = 0.5f;

        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);

        GL11.glPushMatrix();
        // RenderHelper.enableGUIStandardItemLighting();
        // GL11.glDisable(GL11.GL_LIGHTING);
        // GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (drawOnRight) {
            GL11.glTranslatef(sr.getScaledWidth() - 60,
                              sr.getScaledHeight(),
                              0);
        } else {
            GL11.glTranslatef(60,
                              sr.getScaledHeight(),
                              0);
        }
        GL11.glScalef((float) deltaTick,
                      (float) deltaTick,
                      1);

        renderSprite(-64,
                     -64,
                     0,
                     0,
                     128,
                     128,
                     ResourceLib.TRACKER_SWEEP,
                     (float) (1d - deltaTick / 2d));
        // GL11.glEnable(GL11.GL_LIGHTING);
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private float getPingPitch(double distSq2d) {
        int maxDistSq = maxEntityDistance * maxEntityDistance;
        float pitch = (float) (distSq2d / maxDistSq);
        float absolutePitch = 1F - pitch;
        return absolutePitch;
    }

    private class EntityPoint3f {
        double x;
        double y;
        double z;
    }
}