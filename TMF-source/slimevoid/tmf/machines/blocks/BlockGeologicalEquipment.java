package slimevoid.tmf.machines.blocks;

import java.util.Random;

import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeologicalEquipment extends BlockMachine {

	public BlockGeologicalEquipment(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return TMFCore.geoEquipIdleId;
	}
	
	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		keepInventory = true;
		if (isBurning) {
			world.setBlockWithNotify(x, y, z, TMFCore.geoEquipActive.blockID);
		} else {
			world.setBlockWithNotify(x, y, z, TMFCore.geoEquipIdle.blockID);
		}
		keepInventory = false;
		
		world.setBlockMetadataWithNotify(x, y, z, meta);
		if (tile != null) {
			tile.validate();
			world.setBlockTileEntity(x, y, z, tile);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float d) {
		player.openGui(
				TheMinersFriend.instance,
				GuiLib.GEOEQUIP_GUIID,
				world,
				x,
				y,
				z
		);
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGeologicalEquipment();
	}

}
