package slimevoid.tmf.machines.tileentities;

import slimevoid.tmf.minerals.items.ItemMineralMixedDust;
import slimevoid.tmf.minerals.items.ItemMineralDust;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityAutomaticMixingTable extends TileEntityMachine {

	/**
	 * 0 = blueprint
	 * 1 = output
	 * 2-10 = input
	 */
	private ItemStack[] stacks = new ItemStack[11];

	
	
	@Override
	public void updateEntity() {
		smeltItem();
	}
	
	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if ( index == 0 && stack != null && !(stack.getItem() instanceof ItemMineralMixedDust) )
			return;
		if ( index == 1 && stack != null )
			return;
		if ( index >= 2 && stack != null && (!(stack.getItem() instanceof ItemMineralDust) || stack.getItem() instanceof ItemMineralMixedDust) )
			return;
		
		stacks[index] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.tmf.automixtable";
	}

	
	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = ntbCompound.getTagList("Items");
		stacks = new ItemStack[getSizeInventory()];
		
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemInSlot = (NBTTagCompound)items.tagAt(i);
			byte itemBytes = itemInSlot.getByte("Slot");
			
			if (itemBytes >= 0 && itemBytes < stacks.length) {
				stacks[itemBytes] = ItemStack.loadItemStackFromNBT(itemInSlot);
			}
		}
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < stacks.length; ++i) {
			if (stacks[i] != null) {
				NBTTagCompound itemInSlot = new NBTTagCompound();
				itemInSlot.setByte("Slot", (byte)i);
				stacks[i].writeToNBT(itemInSlot);
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
		
		for ( int i = 2; i < stacks.length; i++ ) {
			if ( stacks[i] != null && stacks[i].stackSize > 0 ) {
				return i;
			}
		}
		
		return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void smeltItem() {
		if ( canSmelt() ) {			
			// Get blueprint's levels
			int meta = stacks[0].getItemDamage();
			int blueprintLevelA = ItemMineralMixedDust.getBurnTimeLevel(meta);
			int blueprintLevelB = ItemMineralMixedDust.getBurnSpeedLevel(meta);
			int blueprintLevelC = ItemMineralMixedDust.getBurnWidthLevel(meta);
			
			// Subtract form first from stacks
			int inputLevelA = blueprintLevelA;
			int inputLevelB = blueprintLevelB;
			int inputLevelC = blueprintLevelC;
			for ( int i = 2; i < stacks.length; i++ ) {
				if ( stacks[i] == null )
					continue;

				int inputMeta = ItemMineralMixedDust.getDustMeta(stacks[i]);
				if ( !ItemMineralMixedDust.isMetaCleanDust(inputMeta) )
					continue;

				
				if ( ItemMineralMixedDust.getBurnTimeLevel(inputMeta) > 0 ) {
					if ( inputLevelA == 0 )
						continue;
					if ( stacks[i].stackSize <= inputLevelA ) {
						inputLevelA -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelA;
						inputLevelA = 0;
					}
				} else if ( ItemMineralMixedDust.getBurnSpeedLevel(inputMeta) > 0 ) {
					if ( inputLevelB == 0 )
						continue;
					if ( stacks[i].stackSize <= inputLevelB ) {
						inputLevelB -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelB;
						inputLevelB = 0;
					}
				} else if ( ItemMineralMixedDust.getBurnWidthLevel(inputMeta) > 0 ) {
					if ( inputLevelC == 0 )
						continue;
					if ( stacks[i].stackSize <= inputLevelC ) {
						inputLevelC -= stacks[i].stackSize;
						stacks[i] = null;
					} else {
						stacks[i].stackSize -= inputLevelC;
						inputLevelC = 0;
					}
				}
			}
			
			// Output
			if ( stacks[1] == null ) {
				stacks[1] = new ItemStack(stacks[0].itemID,1,stacks[0].getItemDamage());
			} else {
				stacks[1].stackSize++;
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		if ( stacks[0] == null )
			return false;
		
		if ( stacks[1] != null && stacks[1].stackSize >= getInventoryStackLimit() )
			return false;
		
		// Get blueprint's levels
		int meta = stacks[0].getItemDamage();
		int blueprintLevelA = ItemMineralMixedDust.getBurnTimeLevel(meta);
		int blueprintLevelB = ItemMineralMixedDust.getBurnSpeedLevel(meta);
		int blueprintLevelC = ItemMineralMixedDust.getBurnWidthLevel(meta);
		
		// Get total input's level (all the items combined)
		int inputLevelA = 0;
		int inputLevelB = 0;
		int inputLevelC = 0;
		for ( int i = 2; i < stacks.length; i++ ) {
			if ( stacks[i] == null )
				continue;
			
			int inputMeta = ItemMineralMixedDust.getDustMeta(stacks[i]);
			if ( !ItemMineralMixedDust.isMetaCleanDust(inputMeta) )
				continue;
			
			if ( ItemMineralMixedDust.getBurnTimeLevel(inputMeta) > 0 ) {
				inputLevelA += stacks[i].stackSize;
			} else if ( ItemMineralMixedDust.getBurnSpeedLevel(inputMeta) > 0 ) {
				inputLevelB += stacks[i].stackSize;
			} else if ( ItemMineralMixedDust.getBurnWidthLevel(inputMeta) > 0 ) {
				inputLevelC += stacks[i].stackSize;
			}
		}
		
		// True if there is enough input to create the blueprint
		return ( 
				inputLevelA >= blueprintLevelA &&
				inputLevelB >= blueprintLevelB &&
				inputLevelC >= blueprintLevelC
		);
	}

	@Override
	public int getCurrentFuelBurnTime() {
		return 0;
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return 0;
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return 0;
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return null;
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {}

	@Override
	protected void onInventoryHasChanged(World world, int x, int y, int z) {}

}
