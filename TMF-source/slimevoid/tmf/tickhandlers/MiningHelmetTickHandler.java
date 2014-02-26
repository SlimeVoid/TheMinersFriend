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
package slimevoid.tmf.tickhandlers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import slimevoid.tmf.core.lib.ArmorLib;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MiningHelmetTickHandler implements ITickHandler {

    int                             ticksWearingHelmet = 0;
    Set<ChunkPosition>              lastLitCoords      = new HashSet<ChunkPosition>();
    HashMap<ChunkPosition, Integer> lastLitLevels      = new HashMap<ChunkPosition, Integer>();

    private void doLightArea(EntityPlayer entityplayer, World world) {
        ItemStack miningHelm = ArmorLib.getHelm(entityplayer,
                                                world);
        int x = (int) entityplayer.posX;
        int y = MathHelper.ceiling_double_int(entityplayer.posY
                                              + entityplayer.getEyeHeight());
        int z = (int) entityplayer.posZ;
        ChunkPosition position = new ChunkPosition(x, y, z);
        if (!this.lastLitCoords.contains(position)) {
            this.lastLitCoords.add(position);
            this.lastLitLevels.put(position,
                                   world.getBlockLightValue(x,
                                                            y,
                                                            z));
        }
        world.updateAllLightTypes(x,
                                  y,
                                  z);
        if (miningHelm != null) {
            world.setLightValue(EnumSkyBlock.Block,
                                x,
                                y,
                                z,
                                15);
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        EntityPlayer entityplayer = (EntityPlayer) tickData[0];
        World world = entityplayer.worldObj;
        if (ticksWearingHelmet == 15) {
            ticksWearingHelmet = 0;
            refreshLighting(entityplayer,
                            world);
        }
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            doLightArea(entityplayer,
                        world);
            ++ticksWearingHelmet;
        }
    }

    private void refreshLighting(EntityPlayer entityplayer, World world) {
        int x = (int) entityplayer.posX;
        int y = MathHelper.ceiling_double_int(entityplayer.posY
                                              + entityplayer.getEyeHeight());
        int z = (int) entityplayer.posZ;
        ChunkPosition currentposition = new ChunkPosition(x, y, z);
        Set<ChunkPosition> processedLight = new HashSet<ChunkPosition>();
        for (ChunkPosition position : this.lastLitCoords) {
            int light = this.lastLitLevels.containsKey(position) ? this.lastLitLevels.remove(position) : 0;
            if (position != currentposition) {
                world.setLightValue(EnumSkyBlock.Block,
                                    position.x,
                                    position.y,
                                    position.z,
                                    light);
                processedLight.add(position);
                world.updateAllLightTypes(position.x,
                                          position.y,
                                          position.z);
            }
        }
        this.lastLitCoords.removeAll(processedLight);
    }

    private void checkForFallingBlocks(EntityPlayer entityplayer, World world) {
        ItemStack miningHelm = ArmorLib.getHelm(entityplayer,
                                                world);
        if (miningHelm != null) {
            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(entityplayer.posX,
                                                             entityplayer.posY,
                                                             entityplayer.posZ,
                                                             entityplayer.posX,
                                                             entityplayer.posY
                                                                     + entityplayer.getEyeHeight(),
                                                             entityplayer.posZ);
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entityplayer,
                                                                               box);
            int count = 0;
            if (entities.size() > 0) {
                for (Entity entity : entities) {
                    if (entity instanceof EntityFallingSand) {
                        EntityFallingSand entityfalling = (EntityFallingSand) entity;
                        if (!entityplayer.capabilities.isCreativeMode
                            && !world.isRemote) {
                            entityplayer.dropItem(entityfalling.blockID,
                                                  1);
                        }
                        count++;
                        entityfalling.setDead();
                    }
                }
            }
            double totalDamage = count * ArmorLib.getDamageToHelm(miningHelm);
            miningHelm.damageItem(MathHelper.ceiling_double_int(totalDamage),
                                  entityplayer);
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            EntityPlayer entityplayer = (EntityPlayer) tickData[0];
            World world = entityplayer.worldObj;
            checkForFallingBlocks(entityplayer,
                                  world);
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return "MinersHelmetHandler";
    }

}
