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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.slimevoid.tmf.core.lib.ArmorLib;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MiningHelmetTickHandler {

    private void checkForFallingBlocks(EntityPlayer entityplayer, World world) {
        ItemStack miningHelm = ArmorLib.getPlayerHelm(entityplayer,
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
                    if (entity instanceof EntityFallingBlock) {
                        EntityFallingBlock entityfalling = (EntityFallingBlock) entity;
                        if (!entityplayer.capabilities.isCreativeMode
                            && !world.isRemote) {
                            entityplayer.dropItem(Item.getItemFromBlock(entityfalling.func_145805_f()),
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

    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer entityplayer = event.player;
            World world = entityplayer.worldObj;
            checkForFallingBlocks(entityplayer,
                                  world);
        }
    }
}
