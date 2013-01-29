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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import slimevoid.tmf.core.lib.ArmorLib;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MiningHelmetTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.PLAYER))) {
			EntityPlayer entityplayer = (EntityPlayer) tickData[0];
			World world = entityplayer.worldObj;
			checkForFallingBlocks(entityplayer, world);
		}
	}

	private void checkForFallingBlocks(EntityPlayer entityplayer, World world) {
		if (ArmorLib.getHelm(entityplayer, world) != null) {
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
					entityplayer.posX,
					entityplayer.posY,
					entityplayer.posZ,
					entityplayer.posX,
					entityplayer.posY + 1,
					entityplayer.posZ);
			List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entityplayer, box);
			if (entities.size() > 0) {
				for (Entity entity : entities) {
					if (entity instanceof EntityFallingSand) {
						EntityFallingSand entityfalling = (EntityFallingSand)entity;
						if (entityfalling.blockID == Block.sand.blockID || entityfalling.blockID == Block.gravel.blockID) {
							if (!entityplayer.capabilities.isCreativeMode && !world.isRemote) {
								entityplayer.dropItem(entityfalling.blockID, 1);
							}
							entityfalling.setDead();
						}
					}
				}
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
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
