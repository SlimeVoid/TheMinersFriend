package slimevoid.tmf.blocks.machines.blocks;

import java.util.Random;

import slimevoid.tmf.blocks.machines.tileentities.TileEntityMachine;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.SpriteLib;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMachine extends BlockContainer {
	private Random rand = new Random();
	public boolean isActive;
	public static boolean keepInventory = false;
	
	public BlockMachine(int id, int texX, int texY, boolean isActive) {
		super(id, Material.rock);
        setTextureFile(SpriteLib.MACHINE_TEXTURE_PATH);
        this.blockIndexInTexture = texX+(texY*16);
        this.isActive = isActive;
	}

	public abstract void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z);
	
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
		int var6 = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
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
		if (!keepInventory) {
			TileEntityMachine tile = (TileEntityMachine)world.getBlockTileEntity(x, y, z);
			
			if (tile != null) {
				for (int i = 0; i < tile.getSizeInventory(); ++i) {
					ItemStack stack = tile.getStackInSlot(i);
					
					if (stack != null) {
						float rndX = this.rand.nextFloat() * 0.8F + 0.1F;
						float rndY = this.rand.nextFloat() * 0.8F + 0.1F;
						float rndZ = this.rand.nextFloat() * 0.8F + 0.1F;
						
						while (stack.stackSize > 0) {
							int rndSize = this.rand.nextInt(21) + 10;
							
							if (rndSize > stack.stackSize) {
								rndSize = stack.stackSize;
							}
							
							stack.stackSize -= rndSize;
							EntityItem entity = new EntityItem(
									world, 
									(double)((float)x + rndX), 
									(double)((float)y + rndY), 
									(double)((float)z + rndZ), 
									new ItemStack(
											stack.itemID, 
											rndSize, 
											stack.getItemDamage()
									)
							);
							
							if (stack.hasTagCompound()) {
								entity.func_92014_d().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
							}
							
							float multi = 0.05F;
							entity.motionX = (double)((float)this.rand.nextGaussian() * multi);
							entity.motionY = (double)((float)this.rand.nextGaussian() * multi + 0.2F);
							entity.motionZ = (double)((float)this.rand.nextGaussian() * multi);
							world.spawnEntityInWorld(entity);
						}
					}
				}
			}
		}
		
		super.breakBlock(world, x, y, z, l, m);
	}
	
	@Override 
	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if ( side == 1 || side == 0 ) {
			// Top/bottom
			if ( isActive ) {
				return blockIndexInTexture+4;
			} else {
				return blockIndexInTexture+5;
			}
		} else {
			int meta = blockAccess.getBlockMetadata(x, y, z);
			if ( side == meta ) {
				// Front
				if ( isActive ) {
					return blockIndexInTexture;
				} else {
					return blockIndexInTexture+1;
				}
			} else {
				// Sides
				if ( isActive ) {
					return blockIndexInTexture+2;
				} else {
					return blockIndexInTexture+3;
				}
			}
		}
	}
}
