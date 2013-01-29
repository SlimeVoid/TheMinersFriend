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
package slimevoid.tmf.machines.blocks;

import java.util.Random;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRefinery extends BlockMachine {
	
	public BlockRefinery(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRefinery();
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return TMFCore.refineryIdleId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float d) {
		player.openGui(
				TheMinersFriend.instance,
				GuiLib.REFINERY_GUIID,
				world,
				x,
				y,
				z
		);
		
		return true;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		keepInventory = true;
		if (isBurning) {
			world.setBlockWithNotify(x, y, z, TMFCore.refineryActive.blockID);
		} else {
			world.setBlockWithNotify(x, y, z, TMFCore.refineryIdle.blockID);
		}
		keepInventory = false;
		
		world.setBlockMetadataWithNotify(x, y, z, meta);
		if (tile != null) {
			tile.validate();
			world.setBlockTileEntity(x, y, z, tile);
		}
	}
}
