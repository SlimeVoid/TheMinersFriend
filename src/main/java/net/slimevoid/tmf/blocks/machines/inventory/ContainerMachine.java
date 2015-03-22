/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.tmf.blocks.machines.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.inventory.ContainerBase;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityMachine;

public abstract class ContainerMachine extends ContainerBase {
    protected int lastCookTime     = 0;
    protected int lastBurnTime     = 0;
    protected int lastItemBurnTime = 0;
    protected int lastItemCookTime = 0;

    public ContainerMachine(InventoryPlayer playerInventory, IInventory customInventory, World world, int playerColOffset, int playerRowOffset) {
        super(playerInventory, customInventory, world, playerColOffset, playerRowOffset);
    }

    @Override
    public void onCraftGuiOpened(ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        if (this.hasProgressBar()) {
            crafting.sendProgressBarUpdate(this,
                                           0,
                                           this.getMachineData().cookTime);
            crafting.sendProgressBarUpdate(this,
                                           1,
                                           this.getMachineData().burnTime);
            crafting.sendProgressBarUpdate(this,
                                           2,
                                           this.getMachineData().currentItemBurnTime);
            crafting.sendProgressBarUpdate(this,
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
                var2.sendProgressBarUpdate(this,
                                           0,
                                           inventory.cookTime);
            }

            if (this.lastBurnTime != inventory.burnTime) {
                var2.sendProgressBarUpdate(this,
                                           1,
                                           inventory.burnTime);
            }

            if (this.lastItemBurnTime != inventory.currentItemBurnTime) {
                var2.sendProgressBarUpdate(this,
                                           2,
                                           inventory.currentItemBurnTime);
            }

            if (this.lastItemCookTime != inventory.currentItemCookTime) {
                var2.sendProgressBarUpdate(this,
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
