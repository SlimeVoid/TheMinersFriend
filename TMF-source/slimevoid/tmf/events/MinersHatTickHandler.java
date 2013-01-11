package slimevoid.tmf.events;

import java.util.EnumSet;
import java.util.List;

import slimevoid.tmf.items.ItemMinersHat;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MinersHatTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (tickData.length > 0) {
			if (tickData[0] != null && tickData[0] instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) tickData[0];
				World world = entityplayer.worldObj;
				ItemStack armor = entityplayer.inventory.armorItemInSlot(3);
				if (armor != null && armor.getItem() != null && armor.getItem() instanceof ItemMinersHat) {
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
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER, TickType.CLIENT, TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "MinersHatHandler";
	}
	
}
