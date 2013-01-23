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
