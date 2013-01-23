package slimevoid.tmf.blocks.machines.blocks;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeologicalEquipment extends BlockMachine {

	public BlockGeologicalEquipment(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		// TODO :: GeoEquip idle ID
		//return TMFCore.grinderIdleId;
		return 0;
	}
	
	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		keepInventory = true;
		if (isBurning) {
			// TODO :: GeoEquip active ID
			//world.setBlockWithNotify(x, y, z, TMFCore.grinderActive.blockID);
		} else {
			// TODO :: GeoEquip inactive ID
			//world.setBlockWithNotify(x, y, z, TMFCore.grinderIdle.blockID);
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
		// TODO :: GEOEQUIP_GUIID
		
		/*player.openGui(
				TheMinersFriend.instance,
				GuiLib.GRINDER_GUIID,
				world,
				x,
				y,
				z
		);*/
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO :: GeoEquip tileentity
		return null;
	}

}
