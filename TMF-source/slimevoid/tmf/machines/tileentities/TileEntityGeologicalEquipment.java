package slimevoid.tmf.machines.tileentities;

import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.items.ItemMineral;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGeologicalEquipment extends TileEntityMachine {
	/**
	 * Fuel
	 */
	private ItemStack fuelStack;

	/**
	 * Survey data [block height]
	 */
	private Block[] surveyData = new Block[256];
	
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if ( index == 0 )
			return fuelStack;
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		fuelStack = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.tmf.geoequip";
	}

	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = ntbCompound.getTagList("Items");
		
		NBTTagCompound itemInSlot = (NBTTagCompound)items.tagAt(0);
		fuelStack = ItemStack.loadItemStackFromNBT(itemInSlot);
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = new NBTTagList();
		
		NBTTagCompound itemInSlot = new NBTTagCompound();
		fuelStack.writeToNBT(itemInSlot);
		items.appendTag(itemInSlot);
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
			// TODO :: GeoEquip smelt item
		}
	}

	@Override
	protected boolean canSmelt() {
		if ( fuelStack == null )
			return false;
		
		// TODO :: GeoEquip can smelt
		
		return false;
	}
	
	// Override to force TMF fuel.
	@Override
	public int getItemBurnTime(ItemStack stack) {
		if ( stack == null )
			return 0;

		if ( stack.getItem() instanceof IFuelHandlerTMF ) 
			return ((ItemMineral)stack.getItem()).getBurnTime(stack);

		return 0;
	}
	
	@Override
	public int getCurrentFuelBurnTime() {
		return getItemBurnTime(fuelStack);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(fuelStack);
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return getItemBurnWidth(fuelStack);
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return fuelStack;
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
		fuelStack  = stack;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		// TODO :: GeoEquip update state
		//((BlockGrinder)TMFCore.grinderIdle).updateMachineBlockState(isBurning, world, x, y, z);
	}
}
