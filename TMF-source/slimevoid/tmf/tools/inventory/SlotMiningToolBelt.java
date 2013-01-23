package slimevoid.tmf.tools.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import slimevoid.tmf.tools.items.ItemMotionSensor;

public class SlotMiningToolBelt extends Slot {

	public SlotMiningToolBelt(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return ( 
				(getSlotIndex() == 0 && itemstack != null && itemstack.getItem() instanceof ItemPickaxe) ||
				(getSlotIndex() == 1 && itemstack != null && itemstack.getItem() instanceof ItemSpade) ||
				(getSlotIndex() == 2 && itemstack != null && itemstack.getItem() instanceof Item) ||
				(getSlotIndex() == 3 && itemstack != null && itemstack.getItem() instanceof ItemMotionSensor)
			
		);
	}
	
}
