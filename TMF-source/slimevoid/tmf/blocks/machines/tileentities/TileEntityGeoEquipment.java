package slimevoid.tmf.blocks.machines.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGeoEquipment extends TileEntityMachine {
	/**
	 * Fuel
	 */
	private ItemStack geoEquipmentFuel;

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return geoEquipmentFuel;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (geoEquipmentFuel != null) {
			ItemStack newStack;
			
			if (geoEquipmentFuel.stackSize <= amount) {
				newStack = geoEquipmentFuel;
				geoEquipmentFuel = null;
				return newStack;
			} else {
				newStack = geoEquipmentFuel.splitStack(amount);
				
				if (geoEquipmentFuel.stackSize == 0) {
					geoEquipmentFuel = null;
				}
				
				return newStack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (geoEquipmentFuel != null) {
			ItemStack stack = geoEquipmentFuel;
			geoEquipmentFuel = null;
			return stack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		geoEquipmentFuel = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.tmf.geoeq";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void smeltItem() {}

	@Override
	protected boolean canSmelt() {
		return false;
	}

	@Override
	public int getCurrentFuelBurnTime() {
		return getItemBurnTime(geoEquipmentFuel);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(geoEquipmentFuel);
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return getItemBurnWidth(geoEquipmentFuel);
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return geoEquipmentFuel;
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
		geoEquipmentFuel = stack;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x,
			int y, int z) {
		// TODO :: Geological Equipment
		//((BlockGeoEquipment)TMFCore.geoEquipIdle).updateMachineBlockState(isBurning, world, x, y, z);
	}

}
