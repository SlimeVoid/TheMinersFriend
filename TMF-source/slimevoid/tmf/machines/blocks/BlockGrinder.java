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

import slimevoid.tmf.client.renderers.SimpleBlockRenderingHandlerGrinder;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGrinder extends BlockMachine {

	public BlockGrinder(int id, String textureName, boolean isActive) {
		super(id, textureName, isActive);
		this.setBlockBounds(
				0.1875f, 0f, 0.1875f, 
				0.8125f, 0.9f, 0.8125f		
		);
	}

	@Override
	public int getRenderType() {
		return SimpleBlockRenderingHandlerGrinder.id;
	}
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return TMFCore.grinderIdleId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float d) {
		player.openGui(
				TheMinersFriend.instance,
				GuiLib.GRINDER_GUIID,
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
			world.setBlock(x, y, z, TMFCore.grinderActive.blockID, 0, 0x1);
		} else {
			world.setBlock(x, y, z, TMFCore.grinderIdle.blockID, 0, 0x1);
		}
		keepInventory = false;
		
		world.setBlockMetadataWithNotify(x, y, z, meta, 0x1);
		if (tile != null) {
			tile.validate();
			world.setBlockTileEntity(x, y, z, tile);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGrinder();
	}

}
