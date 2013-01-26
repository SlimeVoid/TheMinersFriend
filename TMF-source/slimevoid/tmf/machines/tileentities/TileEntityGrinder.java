package slimevoid.tmf.machines.tileentities;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.machines.GrinderRecipes;
import slimevoid.tmf.machines.blocks.BlockGrinder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGrinder extends TileEntityMachine {
	/**
	 * 0: Mineral
	 * 1: Fuel
	 * 2: Dust
	 */
	private ItemStack[] grinderItemStacks = new ItemStack[3];
	

	@Override
	public int getSizeInventory() {
		return grinderItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return grinderItemStacks[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		grinderItemStacks[index] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return BlockLib.CONTAINER_GRINDER;
	}

	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = ntbCompound.getTagList("Items");
		grinderItemStacks = new ItemStack[getSizeInventory()];
		
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound)items.tagAt(i);
			byte itemBytes = itemInSlot.getByte("Slot");
			
			if (itemBytes >= 0 && itemBytes < grinderItemStacks.length) {
				grinderItemStacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
			}
		}
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < grinderItemStacks.length; ++i) {
			if (grinderItemStacks[i] != null) {
				NBTTagCompound itemInSlot = new NBTTagCompound();
				itemInSlot.setByte("Slot", (byte)i);
				grinderItemStacks[i].writeToNBT(itemInSlot);
				items.appendTag(itemInSlot);
			}
		}
		
		ntbCompound.setTag("Items", items);
		
		super.writeToNBT(ntbCompound);
		
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.DOWN) return 1;
		if (side == ForgeDirection.UP) return 0;
		return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void smeltItem() {
		if ( canSmelt() ) {
			ItemStack mineralItem = grinderItemStacks[0];
			
			if ( GrinderRecipes.grinding().isMineralAllowed(mineralItem) ) {
				ItemStack smelted = GrinderRecipes.grinding().getRefiningResult(mineralItem.itemID);
				if ( smelted != null && smelted.stackSize > 0) {
					if (grinderItemStacks[2] == null ) {
						grinderItemStacks[2] = smelted.copy();
					} else if (grinderItemStacks[2].isItemEqual(smelted) ) {
						grinderItemStacks[2].stackSize += smelted.stackSize;
					}
					
					--grinderItemStacks[0].stackSize;
					if (grinderItemStacks[0].stackSize <= 0) {
						grinderItemStacks[0] = null;
					}
				}
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		if ( grinderItemStacks[0] == null )
			return false;
		
		ItemStack mineralItem = grinderItemStacks[0];
		

		if ( GrinderRecipes.grinding().isMineralAllowed(mineralItem) ) {
			ItemStack smelted = GrinderRecipes.grinding().getRefiningResult(mineralItem.itemID);
			if ( smelted != null  ) {
				if ( grinderItemStacks[2] == null )
					return true;
				if ( 
						grinderItemStacks[2] != null && 
						grinderItemStacks[2].stackSize + smelted.stackSize <= getInventoryStackLimit()
				)
					return true;
			}
		}

		return false;
	}

	@Override
	public int getCurrentFuelBurnTime() {
		return getItemBurnTime(grinderItemStacks[1]);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(grinderItemStacks[1]) * 2;
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return getItemBurnWidth(grinderItemStacks[1]);
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return grinderItemStacks[1];
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
		grinderItemStacks[1] = stack;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		((BlockGrinder)TMFCore.grinderIdle).updateMachineBlockState(isBurning, world, x, y, z);
	}

	@Override
	protected void onInventoryHasChanged(World world , int x, int y, int z) {
	}

}
