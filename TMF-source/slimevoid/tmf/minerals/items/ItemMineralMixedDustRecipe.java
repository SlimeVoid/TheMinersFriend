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
package slimevoid.tmf.minerals.items;

import slimevoid.tmf.core.TMFCore;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ItemMineralMixedDustRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting craftMatrix, World world) {
		ItemStack a = null;
		ItemStack b = null;
		// Fetch the two dusts
		for ( int i = 0; i < craftMatrix.getInventoryStackLimit(); i++ ) {
			if ( craftMatrix.getStackInSlot(i) != null ) {
				if ( craftMatrix.getStackInSlot(i).getItem() instanceof ItemMineralDust ) {
					if ( a == null ) {
						a = craftMatrix.getStackInSlot(i);
					} else if ( b == null  ) {
						b = craftMatrix.getStackInSlot(i);
					} else {
						return false;
					}
				}
			}
		}

		// Check if there are two dusts and
		// make sure the total dust level does not go over 9
		return ( 
				a != null && b != null  &&
				ItemMineralMixedDust.getTotalLevel(
						ItemMineralMixedDust.mixDustMeta(
								ItemMineralMixedDust.getDustMeta(a),
								ItemMineralMixedDust.getDustMeta(b)
						)
				) < 10
		);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craftMatrix) {
		ItemStack item = null;
		ItemStack a = null;
		ItemStack b = null;
		// Fetch the two dusts
		for ( int i = 0; i < craftMatrix.getInventoryStackLimit(); i++ ) {
			if ( craftMatrix.getStackInSlot(i) != null ) {
				if ( craftMatrix.getStackInSlot(i).getItem() instanceof ItemMineralDust ) {
					if ( a == null ) {
						a = craftMatrix.getStackInSlot(i);
					} else if ( b == null ) {
						b = craftMatrix.getStackInSlot(i);
					}
				}
			}
		}
		
		// Check if there are two dusts
		if ( a != null && b != null ) {
			// Mix the dusts
			int size = 1;
			if ( a.itemID == TMFCore.dustMixedId && b.itemID == TMFCore.dustMixedId )
				size = 2;
			
			item = new ItemStack(TMFCore.dustMixed,size);
			item.setItemDamage(
					ItemMineralMixedDust.mixDustMeta(
							ItemMineralMixedDust.getDustMeta(a),
							ItemMineralMixedDust.getDustMeta(b)
					)
			);
		}
		
		
		return item;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(TMFCore.dustMixed,0xfff);
	}
}
