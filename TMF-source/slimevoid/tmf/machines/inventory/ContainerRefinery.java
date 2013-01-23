package slimevoid.tmf.machines.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import slimevoid.tmf.machines.RefineryRecipes;
import slimevoid.tmf.machines.tileentities.TileEntityMachine;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRefinery extends Container {
	private TileEntityRefinery refinery;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;
	private int lastItemCookTime = 0;
	
	public ContainerRefinery(InventoryPlayer playerInventory, TileEntityRefinery refinery) {
		this.refinery = refinery;

        this.addSlotToContainer(new Slot(refinery, 0, 56, 17)); // Ore
        this.addSlotToContainer(new Slot(refinery, 1, 56, 53)); // Fuel
        
        this.addSlotToContainer(new SlotRefinery(refinery, 2, 112, 35)); // Acxium
        this.addSlotToContainer(new SlotRefinery(refinery, 3, 130, 35)); // Bisogen
        this.addSlotToContainer(new SlotRefinery(refinery, 4, 148, 35)); // Cydrine
     
        bindPlayerInventory(playerInventory);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(
						inventoryPlayer,
						j + i * 9 + 9,
						8 + j * 18, 
						84 + i * 18)
				);
			}
		}

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
		return refinery.isUseableByPlayer(player);
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
			if (slot == 2 || slot == 3 || slot == 4) {
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
				} else if ( refinery.isItemFuel(stackInSlot) ) {
					if ( !this.mergeItemStack(stackInSlot, 1, 2, false) ) {
						return null;
					}
				} else if (slot >= 5 && slot < 32) {
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
		crafting.sendProgressBarUpdate(this, 0, this.refinery.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.refinery.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.refinery.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 3, this.refinery.currentItemCookTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			
			if (this.lastCookTime != this.refinery.cookTime) {
				var2.sendProgressBarUpdate(this, 0, this.refinery.cookTime);
			}
			
			if (this.lastBurnTime != this.refinery.burnTime) {
				var2.sendProgressBarUpdate(this, 1, this.refinery.burnTime);
			}
			
			if (this.lastItemBurnTime != this.refinery.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.refinery.currentItemBurnTime);
			}
			
			if (this.lastItemCookTime != this.refinery.currentItemCookTime) {
				var2.sendProgressBarUpdate(this, 3, this.refinery.currentItemCookTime);
			}
		}
		
		this.lastCookTime = this.refinery.cookTime;
		this.lastBurnTime = this.refinery.burnTime;
		this.lastItemBurnTime = this.refinery.currentItemBurnTime;
		this.lastItemCookTime = this.refinery.currentItemCookTime;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.refinery.cookTime = par2;
		}
		
		if (par1 == 1) {
			this.refinery.burnTime = par2;
		}
		
		if (par1 == 2) {
			this.refinery.currentItemBurnTime = par2;
		}
		
		if (par1 == 3) {
			this.refinery.currentItemCookTime = par2;
		}
	}
}
