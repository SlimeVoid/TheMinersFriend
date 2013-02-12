package slimevoid.tmf.machines.inventory;

import slimevoid.tmf.machines.tileentities.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerTMFMachine extends Container {
	protected TileEntityMachine inventory;
	protected int lastCookTime = 0;
	protected int lastBurnTime = 0;
	protected int lastItemBurnTime = 0;
	protected int lastItemCookTime = 0;
	
	protected void bindPlayerUpperInventory(InventoryPlayer inventoryPlayer, int yDiff) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(
						inventoryPlayer,
						j + i * 9 + 9,
						8 + j * 18, 
						84 + yDiff + i * 18)
				);
			}
		}
	}
	
	protected void bindPlayerHotBar(InventoryPlayer inventoryPlayer, int yDiff) {
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(
					inventoryPlayer, 
					i, 
					8 + i * 18, 
					142 + yDiff
			));
		}
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int yDiff) {
		this.bindPlayerUpperInventory(inventoryPlayer, yDiff);
		this.bindPlayerHotBar(inventoryPlayer, yDiff);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.inventory.isUseableByPlayer(player);
	}
}
