package slimevoid.tmf.machines.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import slimevoid.tmf.machines.RefineryRecipes;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGeologicalEquipment extends Container {
	private TileEntityGeologicalEquipment geoEquip;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;
	private int lastItemCookTime = 0;
	
	public ContainerGeologicalEquipment(InventoryPlayer playerInventory, TileEntityGeologicalEquipment geoEquip) {
		this.geoEquip = geoEquip;

        this.addSlotToContainer(new SlotTMFFuel(
        		geoEquip, 	// Inventory
        		0, 			// Slot
        		1, 			// Min fuel level
        		10,			// Max fuel level
        		9, 			// X
        		69,			// Y
        		false		// Only dust?
        )); // Fuel
     
        bindPlayerInventory(playerInventory);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		/*for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(
						inventoryPlayer,
						j + i * 9 + 9,
						8 + j * 18, 
						84 + i * 18)
				);
			}
		}*/

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(
					inventoryPlayer, 
					i, 
					8 + i * 18, 
					142
			));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return geoEquip.isUseableByPlayer(player);
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
				if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true)) {
					return null;
				}
				slotObject.onSlotChange(stackInSlot,stack);
				//places it into the inventory is possible since its in the player inventory
			} else if (slot != 1 && slot != 0) {
				ItemStack[] results = RefineryRecipes.refining().getRefiningResults(stackInSlot.itemID);
				if ( results != null && results.length > 0 ) {
					if ( !this.mergeItemStack(stackInSlot, 0, 1, false) ) {
						return null;
					}
				} else if ( geoEquip.isItemFuel(stackInSlot) ) {
					if ( !this.mergeItemStack(stackInSlot, 1, 2, false) ) {
						return null;
					}
				} else if (slot >= 1 && slot < 32) {
					if ( !this.mergeItemStack(stackInSlot, 32, 41, false) ) {
						return null;
					}
				} else if (slot >= 32 && slot < 41 && !this.mergeItemStack(stackInSlot, 5, 32, false)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 5, 41, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			
			if (stack.stackSize == stack.stackSize) {
				return null;
			}

			slotObject.onPickupFromSlot(entityplayer, stack);
		}

		return stack;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.geoEquip.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.geoEquip.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.geoEquip.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 3, this.geoEquip.currentItemCookTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			
			if (this.lastCookTime != this.geoEquip.cookTime) {
				var2.sendProgressBarUpdate(this, 0, this.geoEquip.cookTime);
			}
			
			if (this.lastBurnTime != this.geoEquip.burnTime) {
				var2.sendProgressBarUpdate(this, 1, this.geoEquip.burnTime);
			}
			
			if (this.lastItemBurnTime != this.geoEquip.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.geoEquip.currentItemBurnTime);
			}
			
			if (this.lastItemCookTime != this.geoEquip.currentItemCookTime) {
				var2.sendProgressBarUpdate(this, 3, this.geoEquip.currentItemCookTime);
			}
		}
		
		this.lastCookTime = this.geoEquip.cookTime;
		this.lastBurnTime = this.geoEquip.burnTime;
		this.lastItemBurnTime = this.geoEquip.currentItemBurnTime;
		this.lastItemCookTime = this.geoEquip.currentItemCookTime;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.geoEquip.cookTime = par2;
		}
		
		if (par1 == 1) {
			this.geoEquip.burnTime = par2;
		}
		
		if (par1 == 2) {
			this.geoEquip.currentItemBurnTime = par2;
		}
		
		if (par1 == 3) {
			this.geoEquip.currentItemCookTime = par2;
		}
	}
}
