package slimevoid.tmf.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;

import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.minerals.items.ItemMineral;

public class RefineryRecipes {
	private static final RefineryRecipes refiningBase = new RefineryRecipes();
	private Random random = new Random();
	
	private Map<Integer,List<RefineryRecipe>> refiningMap = new HashMap<Integer,List<RefineryRecipe>>();

	public static final RefineryRecipes refining() {
		return refiningBase;
	}
	
	public boolean isOreAllowed(ItemStack ore) {
		return ore != null && refiningMap.containsKey(ore.itemID);
	}
	
	public void addRefinement(BlockTMFOre ore, int min, int max, ItemMineral mineral ) {
		if ( mineral.itemID == TMFCore.mineralAcxiumId+256 ) {
			addRefinement(ore,min,max,mineral,0);
		} else if ( mineral.itemID == TMFCore.mineralBisogenId+256 ) {
			addRefinement(ore,min,max,mineral,1);
		} else if ( mineral.itemID == TMFCore.mineralCydrineId+256 ) {
			addRefinement(ore,min,max,mineral,2);
		}
	}
	
	private void addRefinement(BlockTMFOre ore, int min, int max, ItemMineral mineral, int slotId ) {
		RefineryRecipe recipe = new RefineryRecipe();
		recipe.max = max;
		recipe.min = min;
		recipe.mineral = mineral;
		recipe.slotId = slotId;
		
		if ( !refiningMap.containsKey(ore.blockID) )
			refiningMap.put(ore.blockID, new ArrayList<RefineryRecipe>());
		
		refiningMap.get(ore.blockID).add(recipe);
	}
	
	public ItemStack[] getRefiningResults(BlockTMFOre ore) {		
		return getRefiningResults(ore.blockID);
	}
	
	public ItemStack[] getRefiningResults(int oreId) {
		if ( !refiningMap.containsKey(oreId) )
			return null;
		
		List<RefineryRecipe> recipes = refiningMap.get(oreId);
		ItemStack[] out = new ItemStack[3];
		for ( int i = 0; i < recipes.size(); i++ ) {
			if ( recipes.get(i).slotId > 2 || recipes.get(i).slotId < 0 )
				continue;
			out[recipes.get(i).slotId] = recipes.get(i).generateItemStack();
		}
		
		return out;
	}
	
	public RefineryRecipe[] getRefineryRecipes(BlockTMFOre ore) {	
		return getRefineryRecipes(ore.blockID);
	}
	public RefineryRecipe[] getRefineryRecipes(int oreId) {
		if ( !refiningMap.containsKey(oreId) )
			return null;
		RefineryRecipe[] out = new RefineryRecipe[refiningMap.get(oreId).size()];
		for ( int i = 0; i < refiningMap.get(oreId).size(); i++ ) {
			out[i] = refiningMap.get(oreId).get(i);
		}
		return out;
	}
	
	public class RefineryRecipe {
		public int max;
		public int min;
		public ItemMineral mineral;
		public int slotId;
		
		public ItemStack generateItemStack() {
			return new ItemStack(
					mineral,
					random.nextInt((max-min+1))+min
			);
		}
		
		public String toString() {
			return slotId+":"+mineral+":"+min+","+max;
		}
	}
}