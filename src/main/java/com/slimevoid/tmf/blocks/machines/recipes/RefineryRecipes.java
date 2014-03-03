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
package com.slimevoid.tmf.blocks.machines.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.slimevoid.tmf.blocks.ores.BlockTMFOre;
import com.slimevoid.tmf.core.TMFCore;
import com.slimevoid.tmf.items.minerals.ItemMineral;

public class RefineryRecipes {
    private static final RefineryRecipes       refiningBase = new RefineryRecipes();
    private Random                             random       = new Random();

    private Map<Integer, List<RefineryRecipe>> refiningMap  = new HashMap<Integer, List<RefineryRecipe>>();

    public static final RefineryRecipes refining() {
        return refiningBase;
    }

    public boolean isOreAllowed(ItemStack ore) {
        return ore != null && refiningMap.containsKey(ore.getItem());
    }

    public boolean isOreAllowed(Item ore) {
        return ore != null && refiningMap.containsKey(Item.getIdFromItem(ore));
    }

    public void addRefinement(BlockTMFOre ore, int min, int max, ItemMineral mineral) {
        if (mineral.getUnlocalizedName().equals(TMFCore.mineralAcxium.getUnlocalizedName())) {
            addRefinement(ore,
                          min,
                          max,
                          mineral,
                          0);
        } else if (mineral.getUnlocalizedName().equals(TMFCore.mineralBisogen.getUnlocalizedName())) {
            addRefinement(ore,
                          min,
                          max,
                          mineral,
                          1);
        } else if (mineral.getUnlocalizedName().equals(TMFCore.mineralCydrine.getUnlocalizedName())) {
            addRefinement(ore,
                          min,
                          max,
                          mineral,
                          2);
        }
    }

    private void addRefinement(BlockTMFOre ore, int min, int max, ItemMineral mineral, int slotId) {
        RefineryRecipe recipe = new RefineryRecipe();
        recipe.max = max;
        recipe.min = min;
        recipe.mineral = mineral;
        recipe.slotId = slotId;

        if (!refiningMap.containsKey(Block.getIdFromBlock(ore))) refiningMap.put(Block.getIdFromBlock(ore),
                                                                                 new ArrayList<RefineryRecipe>());

        refiningMap.get(Block.getIdFromBlock(ore)).add(recipe);
    }

    public ItemStack[] getRefiningResults(ItemStack ore) {
        return getRefiningResults(Block.getBlockFromItem(ore.getItem()));
    }

    public ItemStack[] getRefiningResults(Block ore) {
        return getRefiningResults(Block.getIdFromBlock(ore));
    }

    public ItemStack[] getRefiningResults(int oreId) {
        if (!refiningMap.containsKey(oreId)) return null;

        List<RefineryRecipe> recipes = refiningMap.get(oreId);
        ItemStack[] out = new ItemStack[3];
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).slotId > 2 || recipes.get(i).slotId < 0) continue;
            out[recipes.get(i).slotId] = recipes.get(i).generateItemStack();
        }

        return out;
    }

    public RefineryRecipe[] getRefineryRecipes(ItemStack ore) {
        return getRefineryRecipes(Block.getBlockFromItem(ore.getItem()));
    }

    public RefineryRecipe[] getRefineryRecipes(Block ore) {
        return getRefineryRecipes(Block.getIdFromBlock(ore));
    }

    public RefineryRecipe[] getRefineryRecipes(int oreId) {
        if (!refiningMap.containsKey(oreId)) return null;
        RefineryRecipe[] out = new RefineryRecipe[refiningMap.get(oreId).size()];
        for (int i = 0; i < refiningMap.get(oreId).size(); i++) {
            out[i] = refiningMap.get(oreId).get(i);
        }
        return out;
    }

    public class RefineryRecipe {
        public int         max;
        public int         min;
        public ItemMineral mineral;
        public int         slotId;

        public ItemStack generateItemStack() {
            return new ItemStack(mineral, random.nextInt((max - min + 1)) + min);
        }

        @Override
        public String toString() {
            return slotId + ":" + mineral + ":" + min + "," + max;
        }
    }
}
