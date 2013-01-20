package slimevoid.tmf.blocks.machines.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import slimevoid.tmf.blocks.machines.blocks.BlockMachine;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public abstract class TileEntityMachine extends TileEntity implements IInventory, ISidedInventory {
	/** The number of ticks that the machine will keep burning */
	public int burnTime = 0;
	/** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
	public int currentItemBurnTime = 0;
	
	/** The number of ticks that the current item has been cooking for */
	public int cookTime = 0;
	/** The number of ticks that a fresh copy of the currently-burning item would take to cook */
	public int currentItemCookTime = 0;
		
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this 
				? false 
				: player.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		super.readFromNBT(ntbCompound);
		
		burnTime = ntbCompound.getShort("BurnTime");
        cookTime = ntbCompound.getShort("CookTime");
        currentItemBurnTime = getCurrentFuelBurnTime();
        currentItemCookTime = getCurrentFuelBurnSpeed();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		super.writeToNBT(ntbCompound);
		
		ntbCompound.setShort("BurnTime", (short)burnTime);
		ntbCompound.setShort("CookTime", (short)cookTime);
	}
	
	@Override
	public void updateEntity() {
		boolean wasBurning = isBurning();
		boolean inventoryChanged = false;
        
		if ( isBurning() )
			burnTime--;
		
		if ( !worldObj.isRemote ) {
			if ( !isBurning() && canSmelt() ) {
				currentItemBurnTime = burnTime = getCurrentFuelBurnTime();
				if ( burnTime > 0 ) {
					inventoryChanged = true;
					if ( getCurrentFuelStack() != null ) {
						getCurrentFuelStack().stackSize--;
						
						if ( getCurrentFuelStack().stackSize == 0 ) {
							setCurrentFuelStack(getCurrentFuelStack().getItem().getContainerItemStack(getCurrentFuelStack()));
						}
					}
				}
			}
			
			if ( isBurning() && canSmelt() ) {
				cookTime++;
				
				if ( cookTime == currentItemCookTime ) {
					cookTime = 0;
					smeltItem();
					inventoryChanged = true;
				}
			} else {
				cookTime = 0;
			}
			
			if ( wasBurning != isBurning() ) {
				inventoryChanged = true;
				updateMachineBlockState(isBurning(), worldObj, xCoord, yCoord, zCoord);
			}
		}
		
		if ( inventoryChanged ) {
			onInventoryChanged();
		}
	}

	public boolean isBurning() {
		return burnTime > 0;
	}
    
    
	public abstract void smeltItem();
	protected abstract boolean canSmelt();
	
	public abstract int getCurrentFuelBurnTime();
	public abstract int getCurrentFuelBurnSpeed();
	public abstract int getCurrentFuelBurnWidth();
	public abstract ItemStack getCurrentFuelStack();
	public abstract void setCurrentFuelStack(ItemStack stack);
	public abstract void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z);

	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		if ( currentItemCookTime <= 0 )
			return 0;
    	
    	return cookTime * par1 / currentItemCookTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}
	
		return burnTime * par1 / currentItemBurnTime;
	}
}
