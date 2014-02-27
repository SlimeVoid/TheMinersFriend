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
package com.slimevoid.tmf.tickhandlers;

import java.util.EnumSet;
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
import net.minecraft.world.World;
import slimevoidlib.core.lib.ConfigurationLib;

import com.slimevoid.tmf.core.lib.ArmorLib;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MiningHelmetTickHandler implements ITickHandler {

    private int                ticksInHelmet     = 0;
    private boolean            isFirstTick       = true;
    private Set<ChunkPosition> previousLocations = new HashSet<ChunkPosition>();

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            EntityPlayer entityplayer = (EntityPlayer) tickData[0];
            World world = entityplayer.worldObj;
            this.createLightAt(entityplayer,
                               world);
            ++this.ticksInHelmet;
            if (this.ticksInHelmet > 15) {
                this.refreshLighting(entityplayer,
                                     world);
                this.ticksInHelmet = 0;
            }
        }
    }

    private void refreshLighting(EntityPlayer entityplayer, World world) {
        int x = MathHelper.floor_double(entityplayer.posX);
        int y = MathHelper.floor_double(entityplayer.posY
                                        + entityplayer.getEyeHeight());
        int z = MathHelper.floor_double(entityplayer.posZ);
        ChunkPosition position = new ChunkPosition(x, y, z);
        ItemStack miningHelm = ArmorLib.getHelm(entityplayer,
                                                world);

        if (miningHelm != null) {
            removeLighting(world,
                           position,
                           false);
        } else {
            removeLighting(world,
                           position,
                           true);
        }
    }

    private void removeLighting(World world, ChunkPosition playerPos, boolean atPlayer) {
        Set<ChunkPosition> clearedLighting = new HashSet<ChunkPosition>();
        for (ChunkPosition position : this.previousLocations) {
            if (!position.equals(playerPos)) {
                world.scheduleBlockUpdate(position.x,
                                          position.y,
                                          position.z,
                                          ConfigurationLib.blockLight.blockID,
                                          5);
                clearedLighting.add(position);

            }
        }
        if (atPlayer) {
            world.scheduleBlockUpdate(playerPos.x,
                                      playerPos.y,
                                      playerPos.z,
                                      ConfigurationLib.blockLight.blockID,
                                      5);
            clearedLighting.add(playerPos);
        }
        this.previousLocations.removeAll(clearedLighting);
    }

    private void createLightAt(EntityPlayer entityplayer, World world) {
        ItemStack miningHelm = ArmorLib.getHelm(entityplayer,
                                                world);

        if (miningHelm != null) {
            int x = MathHelper.floor_double(entityplayer.posX);
            int y = MathHelper.floor_double(entityplayer.posY
                                            + entityplayer.getEyeHeight());
            int z = MathHelper.floor_double(entityplayer.posZ);
            this.previousLocations.add(new ChunkPosition(x, y, z));
            world.setBlock(x,
                           y,
                           z,
                           ConfigurationLib.blockLight.blockID);
            isFirstTick = false;
        }
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
