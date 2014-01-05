package slimevoid.tmf.blocks.machines.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityMachine;
import slimevoidlib.inventory.ContainerBase;

public abstract class ContainerMachine extends ContainerBase {
	protected int	lastCookTime		= 0;
	protected int	lastBurnTime		= 0;
	protected int	lastItemBurnTime	= 0;
	protected int	lastItemCookTime	= 0;

	public ContainerMachine(InventoryPlayer playerInventory, IInventory customInventory, World world, int playerColOffset, int playerRowOffset) {
		super(playerInventory, customInventory, world, playerColOffset, playerRowOffset);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		if (this.hasProgressBar()) {
			crafting.sendProgressBarUpdate(	this,
											0,
											this.getMachineData().cookTime);
			crafting.sendProgressBarUpdate(	this,
											1,
											this.getMachineData().burnTime);
			crafting.sendProgressBarUpdate(	this,
											2,
											this.getMachineData().currentItemBurnTime);
			crafting.sendProgressBarUpdate(	this,
											3,
											this.getMachineData().currentItemCookTime);
		}
	}

	protected TileEntityMachine getMachineData() {
		return (TileEntityMachine) this.getInventoryData();
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		TileEntityMachine inventory = this.getMachineData();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting var2 = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != inventory.cookTime) {
				var2.sendProgressBarUpdate(	this,
											0,
											inventory.cookTime);
			}

			if (this.lastBurnTime != inventory.burnTime) {
				var2.sendProgressBarUpdate(	this,
											1,
											inventory.burnTime);
			}

			if (this.lastItemBurnTime != inventory.currentItemBurnTime) {
				var2.sendProgressBarUpdate(	this,
											2,
											inventory.currentItemBurnTime);
			}

			if (this.lastItemCookTime != inventory.currentItemCookTime) {
				var2.sendProgressBarUpdate(	this,
											3,
											inventory.currentItemCookTime);
			}
		}

		this.lastCookTime = inventory.cookTime;
		this.lastBurnTime = inventory.burnTime;
		this.lastItemBurnTime = inventory.currentItemBurnTime;
		this.lastItemCookTime = inventory.currentItemCookTime;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int updateType, int progress) {
		if (this.hasProgressBar()) {
			TileEntityMachine inventory = this.getMachineData();

			if (updateType == 0) {
				inventory.cookTime = progress;
			}

			if (updateType == 1) {
				inventory.burnTime = progress;
			}

			if (updateType == 2) {
				inventory.currentItemBurnTime = progress;
			}

			if (updateType == 3) {
				inventory.currentItemCookTime = progress;
			}
		}
	}

	protected abstract boolean hasProgressBar();
}