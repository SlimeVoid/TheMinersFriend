package slimevoid.tmf.tools.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMiningToolBelt extends Container {
	
	// The toolbelt to which this Container Belongs
	private IInventory toolbelt;

	public ContainerMiningToolBelt(IInventory playerInventory, IInventory toolBelt) {
		this.toolbelt = toolBelt;

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 0, 69, 37)); // Pick

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 1, 69, 59)); // Spade
        
        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 2, 92, 37)); // Etc

        this.addSlotToContainer(new SlotMiningToolBelt(toolBelt, 3, 92, 59)); // Sensor
/*
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(playerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 94 + 46 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(playerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 152+46));
        }*/
        
        bindPlayerInventory((InventoryPlayer) playerInventory);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(
						inventoryPlayer,
						j + i * 9 + 9,
						8 + j * 18, 
						84 +56 + i * 18)
				);
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(
					inventoryPlayer, 
					i, 
					8 + i * 18, 
					142+56
			));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return toolbelt.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		//null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			//merges the item into player inventory since its in the inventory
			if (slot == 0) {
				if (!mergeItemStack(stackInSlot, 1,
					inventorySlots.size(), true)) {
					return null;
				}
				//places it into the inventory is possible since its in the player inventory
			} else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			slotObject.onPickupFromSlot(entityplayer, stack);
		}

		return stack;
	}
}
