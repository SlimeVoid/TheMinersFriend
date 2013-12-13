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

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoid.tmf.machines.tileentities.TileEntityMachine;

public abstract class BlockMachine extends BlockContainer {
	
	public Icon iconList[] = new Icon[4];
	private Random rand = new Random();
	public boolean isActive;
	public static boolean keepInventory = false;
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		iconList[0] = iconRegister.registerIcon(this.getTextureName() + "_front");
		iconList[1] = iconRegister.registerIcon(this.getTextureName() + "_side");
		iconList[2] = iconRegister.registerIcon(this.getTextureName() + "_top");
		iconList[3] = iconRegister.registerIcon(this.getTextureName() + "_bottom");
	}
	
	@Override
	public String getTextureName() {
		return CoreLib.MOD_RESOURCES + ":" + this.textureName;
	}
	
	public BlockMachine(int id, String name, boolean isActive) {
		super(id, Material.rock);
		this.setUnlocalizedName(name);
		this.textureName = name;
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
			
			world.setBlockMetadataWithNotify(x, y, z, dir, 0x1);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemstack) {
		int var6 = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (var6 == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 0x1);
		}
		
		if (var6 == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 0x1);
		}
		
		if (var6 == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 0x1);
		}
		
		if (var6 == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 0x1);
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
									(x + rndX), 
									(y + rndY), 
									(z + rndZ), 
									new ItemStack(
											stack.itemID, 
											rndSize, 
											stack.getItemDamage()
									)
							);
							
							if (stack.hasTagCompound()) {
								entity.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
							}
							
							float multi = 0.05F;
							entity.motionX = ((float)this.rand.nextGaussian() * multi);
							entity.motionY = ((float)this.rand.nextGaussian() * multi + 0.2F);
							entity.motionZ = ((float)this.rand.nextGaussian() * multi);
							world.spawnEntityInWorld(entity);
						}
					}
				}
			}
		}
		
		super.breakBlock(world, x, y, z, l, m);
	}
	
	@Override 
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		// Top/bottom
		if ( side == 1) {
			return this.iconList[2];
		} else if (side == 0) {
			return this.iconList[3];
		} else {
			int meta = 4;
			if ( blockAccess != null )
				meta = blockAccess.getBlockMetadata(x, y, z);
			if ( side == meta ) {
				// Front
				return this.iconList[0];
			} else {
				// Sides
				return this.iconList[1];
			}
		}
	}
}
