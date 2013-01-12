package slimevoid.tmf.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerMiningToolBelt extends Container {
	
	// The toolbelt to which this Container Belongs
	private IInventory toolbelt;

	public ContainerMiningToolBelt(IInventory playerInventory, IInventory toolBelt) {
		this.toolbelt = toolBelt;

        this.addSlotToContainer(new Slot(toolBelt, 0, 56, 17));

        this.addSlotToContainer(new Slot(toolBelt, 1, 60, 17));
        
        this.addSlotToContainer(new Slot(toolBelt, 2, 64, 17));

        this.addSlotToContainer(new Slot(toolBelt, 3, 68, 17));

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(playerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 94 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(playerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 152));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

}
