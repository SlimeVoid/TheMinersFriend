package slimevoid.tmf.blocks.machines.tileentities;

import java.util.ArrayList;
import java.util.List;

import slimevoid.tmf.blocks.machines.RefineryRecipes;
import slimevoid.tmf.blocks.machines.RefineryRecipes.RefineryRecipe;
import slimevoid.tmf.blocks.machines.blocks.BlockRefinery;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.items.ItemMineral;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
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
	public ItemStack decrStackSize(int index, int ammount) {
		if (refineryItemStacks[index] != null) {
			ItemStack newStack;
			
			if (refineryItemStacks[index].stackSize <= ammount) {
				newStack = refineryItemStacks[index];
				refineryItemStacks[index] = null;
				return newStack;
			} else {
				newStack = refineryItemStacks[index].splitStack(ammount);
				
				if (refineryItemStacks[index].stackSize == 0) {
					refineryItemStacks[index] = null;
				}
				
				return newStack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if (refineryItemStacks[index] != null) {
			ItemStack stack = refineryItemStacks[index];
			refineryItemStacks[index] = null;
			return stack;
		} else {
			return null;
		}
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
		NBTTagList var2 = ntbCompound.getTagList("Items");
		refineryItemStacks = new ItemStack[getSizeInventory()];
		
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			
			if (var5 >= 0 && var5 < refineryItemStacks.length) {
				refineryItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList var2 = new NBTTagList();
		
		for (int var3 = 0; var3 < refineryItemStacks.length; ++var3) {
			if (refineryItemStacks[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				refineryItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		ntbCompound.setTag("Items", var2);
		
		super.writeToNBT(ntbCompound);
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
					if (refineryItemStacks[2] == null) {
						refineryItemStacks[2] = smelted[0].copy();
					} else if (refineryItemStacks[2].isItemEqual(smelted[0]) ) {
						refineryItemStacks[2].stackSize += smelted[0].stackSize;
					}
					
					if (refineryItemStacks[3] == null) {
						refineryItemStacks[3] = smelted[1].copy();
					} else if (refineryItemStacks[3].isItemEqual(smelted[1]) ) {
						refineryItemStacks[3].stackSize += smelted[1].stackSize;
					}
					
					if (refineryItemStacks[4] == null) {
						refineryItemStacks[4] = smelted[2].copy();
					} else if (refineryItemStacks[4].isItemEqual(smelted[2]) ) {
						refineryItemStacks[4].stackSize += smelted[2].stackSize;
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

			if ( recipes != null && recipes.length <= 3 ) {
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
							refineryItemStacks[2+recipes[i].slotId].stackSize + recipes[i].max > getSizeInventory()
					) return false;
				}
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getCurrentFuelBurnTime() {
		if ( refineryItemStacks[1] == null )
			return 0;
		
		return TileEntityFurnace.getItemBurnTime(refineryItemStacks[1]);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		if ( refineryItemStacks[1] == null )
			return 0;
		
		if ( refineryItemStacks[1].getItem() instanceof IFuelHandlerTMF ) {
			return ((ItemMineral)refineryItemStacks[1].getItem()).getBurnSpeed(refineryItemStacks[1]);
		} else {
			return 200;
		}
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		if ( refineryItemStacks[1] == null )
			return 0;
		
		if ( refineryItemStacks[1].getItem() instanceof IFuelHandlerTMF ) {
			return ((ItemMineral)refineryItemStacks[1].getItem()).getBurnWidth(refineryItemStacks[1]);
		} else {
			return 1;
		}
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
		BlockRefinery.updateRefineryBlockState(isBurning, world, x, y, z);
	}
}
