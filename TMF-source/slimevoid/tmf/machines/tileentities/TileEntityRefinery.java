package slimevoid.tmf.machines.tileentities;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.machines.RefineryRecipes;
import slimevoid.tmf.machines.RefineryRecipes.RefineryRecipe;
import slimevoid.tmf.machines.blocks.BlockMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityRefinery extends TileEntityMachine {
	/**
	 * 0: Ore
	 * 1: Fuel
	 * 2: Acxium Mineral
	 * 3: Bisogen Mineral
	 * 4: Cydrine Mineral
	 */
	private ItemStack[] refineryItemStacks = new ItemStack[5];
	
	
	
	@Override
	public int getSizeInventory() {
		return refineryItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return refineryItemStacks[index];
	}


	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		refineryItemStacks[index] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.tmf.refinery";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = ntbCompound.getTagList("Items");
		refineryItemStacks = new ItemStack[getSizeInventory()];
		
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound)items.tagAt(i);
			byte itemBytes = itemInSlot.getByte("Slot");
			
			if (itemBytes >= 0 && itemBytes < refineryItemStacks.length) {
				refineryItemStacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
			}
		}
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < refineryItemStacks.length; ++i) {
			if (refineryItemStacks[i] != null) {
				NBTTagCompound itemInSlot = new NBTTagCompound();
				itemInSlot.setByte("Slot", (byte)i);
				refineryItemStacks[i].writeToNBT(itemInSlot);
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
			ItemStack oreItem = refineryItemStacks[0];
			if ( RefineryRecipes.refining().isOreAllowed(oreItem) ) {
				ItemStack[] smelted = RefineryRecipes.refining().getRefiningResults(oreItem.itemID);

				if ( smelted != null && smelted.length == 3 ) {
					if ( smelted[0] != null && smelted[0].stackSize > 0 ) {
						if (refineryItemStacks[2] == null ) {
							refineryItemStacks[2] = smelted[0].copy();
						} else if (refineryItemStacks[2].isItemEqual(smelted[0]) ) {
							refineryItemStacks[2].stackSize += smelted[0].stackSize;
						}
					}
					
					if ( smelted[1] != null && smelted[1].stackSize > 0 ) {
						if (refineryItemStacks[3] == null  ) {
							refineryItemStacks[3] = smelted[1].copy();
						} else if (refineryItemStacks[3].isItemEqual(smelted[1]) ) {
							refineryItemStacks[3].stackSize += smelted[1].stackSize;
						}
					}

					if ( smelted[2] != null && smelted[2].stackSize > 0 ) {
						if (refineryItemStacks[4] == null ) {
							refineryItemStacks[4] = smelted[2].copy();
						} else if (refineryItemStacks[4].isItemEqual(smelted[2]) ) {
							refineryItemStacks[4].stackSize += smelted[2].stackSize;
						}
					}
					
					--refineryItemStacks[0].stackSize;
					if (refineryItemStacks[0].stackSize <= 0) {
						refineryItemStacks[0] = null;
					}
				}
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		if ( refineryItemStacks[0] == null )
			return false;
		
		ItemStack oreItem = refineryItemStacks[0];
		if ( RefineryRecipes.refining().isOreAllowed(oreItem) ) {
			RefineryRecipe[] recipes = RefineryRecipes.refining().getRefineryRecipes(oreItem.itemID);

			if ( recipes != null && recipes.length == 3 ) {
				boolean ok = true;
				for ( int i = 0; i < recipes.length; i++) {
					if ( refineryItemStacks[2+recipes[i].slotId] != null )
						ok = false;
				}
				if ( ok ) return true;
				
				ok = true;
				for ( int i = 0; i < recipes.length; i++) {
					if ( 
							refineryItemStacks[2+recipes[i].slotId] != null &&
							refineryItemStacks[2+recipes[i].slotId].stackSize + recipes[i].max > getInventoryStackLimit()
					) ok = false;
				}
				if ( ok ) return true;
			}
		}
		
		return false;
	}

	@Override
	public int getCurrentFuelBurnTime() {
		return getItemBurnTime(refineryItemStacks[1]);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(refineryItemStacks[1]);
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return getItemBurnWidth(refineryItemStacks[1]);
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return refineryItemStacks[1];
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
		refineryItemStacks[1] = stack;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		((BlockMachine)TMFCore.refineryIdle).updateMachineBlockState(isBurning, world, x, y, z);
	}

	@Override
	protected void onInventoryHasChanged(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}
}
