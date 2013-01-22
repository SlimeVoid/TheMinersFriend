package slimevoid.tmf.blocks.machines;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMineralDust;

public class GrinderRecipes {
	private static final GrinderRecipes grinderBase = new GrinderRecipes();
	
	private Map<Integer,ItemMineralDust> grindingMap = new HashMap<Integer,ItemMineralDust>();

	public static final GrinderRecipes grinding() {
		return grinderBase;
	}
	
	public boolean isMineralAllowed(ItemStack mineral) {
		return mineral != null && grindingMap.containsKey(mineral.itemID);
	}
	
	
	public void addRefinement(ItemMineral mineral, ItemMineralDust dust ) {		
		grindingMap.put(mineral.itemID, dust);
	}
	
	public ItemStack getRefiningResult(ItemMineral mineral) {		
		return getRefiningResult(mineral.itemID);
	}
	
	public ItemStack getRefiningResult(int mineralId) {
		if ( !grindingMap.containsKey(mineralId) )
			return null;
		
		return new ItemStack(grindingMap.get(mineralId));
		
	}
}
