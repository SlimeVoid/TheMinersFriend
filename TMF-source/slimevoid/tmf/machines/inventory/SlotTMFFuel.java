package slimevoid.tmf.machines.inventory;

import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.minerals.items.ItemMineralDust;
import slimevoid.tmf.minerals.items.ItemMineralMixedDust;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTMFFuel extends Slot {
	private int minFuelLevel;
	private int maxFuelLevel;
	private boolean onlyDust;
	
	public SlotTMFFuel(IInventory inventory, int index, int minFuelLevel, int maxFuelLevel, int x, int y, boolean onlyDust) {
		super(inventory, index, x, y);
		this.minFuelLevel = minFuelLevel;
		this.maxFuelLevel = maxFuelLevel;
		this.onlyDust = onlyDust;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if ( stack == null || stack.getItem() == null )
			return false;
		if ( !(stack.getItem() instanceof IFuelHandlerTMF) )
			return false;
		if ( onlyDust && !(stack.getItem() instanceof ItemMineralDust) )
			return false;

		int totalLevel = 1;
		if ( stack.getItem() instanceof ItemMineralMixedDust )
			totalLevel = ItemMineralMixedDust.getTotalLevel(stack.getItemDamage());

		
		return (
				totalLevel >= minFuelLevel &&
				totalLevel <= maxFuelLevel
		);
	}
}
