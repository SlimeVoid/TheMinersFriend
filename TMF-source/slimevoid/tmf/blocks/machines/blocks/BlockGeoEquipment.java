package slimevoid.tmf.blocks.machines.blocks;

import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeoEquipment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeoEquipment extends BlockMachine {

	public BlockGeoEquipment(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x,
			int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		keepInventory = true;
		if (isBurning) {
			// TODO :: geo equipment
			//world.setBlockWithNotify(x, y, z, TMFCore.geoEquipActive.blockID);
		} else {
			// TODO :: grinder
			//world.setBlockWithNotify(x, y, z, TMFCore.geoEquipIdle.blockID);
		}
		keepInventory = false;
		
		world.setBlockMetadataWithNotify(x, y, z, meta);
		if (tile != null) {
			tile.validate();
			world.setBlockTileEntity(x, y, z, tile);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGeoEquipment();
	}
}
