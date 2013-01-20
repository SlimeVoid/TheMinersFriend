package slimevoid.tmf.blocks.machines.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BlockMachine extends BlockContainer {

	public BlockMachine(int id, int texX, int texY, boolean isActive) {
		super(id, Material.rock);
        this.blockIndexInTexture = texX+(texY*16);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int n1 = world.getBlockId(x    , y, z - 1);
			int n2 = world.getBlockId(x    , y, z + 1);
			int n3 = world.getBlockId(x - 1, y, z    );
			int n4 = world.getBlockId(x + 1, y, z    );
			
			byte dir = 3;
			
			if (Block.opaqueCubeLookup[n1] && !Block.opaqueCubeLookup[n2]) {
				dir = 3;
			}
			
			if (Block.opaqueCubeLookup[n2] && !Block.opaqueCubeLookup[n1]) {
				dir = 2;
			}
			
			if (Block.opaqueCubeLookup[n3] && !Block.opaqueCubeLookup[n4]) {
				dir = 5;
			}
			
			if (Block.opaqueCubeLookup[n4] && !Block.opaqueCubeLookup[n3]) {
				dir = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, dir);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
		int var6 = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (var6 == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2);
		}
		
		if (var6 == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5);
		}
		
		if (var6 == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3);
		}
		
		if (var6 == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int l, int m) {
		super.breakBlock(world, x, y, z, l, m);
	}
}
