package slimevoid.tmf.blocks.machines;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;

import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMineralDust;

public class GrinderRecipes {
	private static final GrinderRecipes grinderBase = new GrinderRecipes();
	private Random random = new Random();
	
	private Map<Integer,GrinderRecipe> grindingMap = new HashMap<Integer,GrinderRecipe>();

	public static final GrinderRecipes grinding() {
		return grinderBase;
	}
	
	public boolean isMineralAllowed(ItemStack mineral) {
		return mineral != null && grindingMap.containsKey(mineral.itemID);
	}
	
	public void addRefinement(ItemMineral mineral, int min, int max, ItemMineralDust dust ) {	
		GrinderRecipe recipe = new GrinderRecipe();
		recipe.dust = dust;
		recipe.min = min;
		recipe.max = max;
		
		grindingMap.put(mineral.itemID, recipe);
	}
	
	public ItemStack getRefiningResult(ItemMineral mineral) {		
		return getRefiningResult(mineral.itemID);
	}
	
	public ItemStack getRefiningResult(int mineralId) {
		if ( !grindingMap.containsKey(mineralId) )
			return null;
		
		return grindingMap.get(mineralId).generateItemStack();
		
	}
	
	public class GrinderRecipe {
		public int max;
		public int min;
		public ItemMineralDust dust;
		
		public ItemStack generateItemStack() {
			return new ItemStack(
					dust,
					random.nextInt((max-min+1))+min
			);
		}
	}
}
