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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.slimevoid.tmf.items.ItemTMF;
import com.slimevoid.tmf.items.minerals.ItemMineralDust;

public class GrinderRecipes {
    private static final GrinderRecipes grinderBase = new GrinderRecipes();
    private Random                      random      = new Random();

    private Map<Integer, GrinderRecipe> grindingMap = new HashMap<Integer, GrinderRecipe>();

    public static final GrinderRecipes grinding() {
        return grinderBase;
    }

    public boolean isMineralAllowed(ItemStack mineral) {
        return mineral != null
               && grindingMap.containsKey(Item.getIdFromItem(mineral.getItem()));
    }

    public void addRefinement(ItemTMF mineral, int min, int max, ItemMineralDust dust) {
        GrinderRecipe recipe = new GrinderRecipe();
        recipe.dust = dust;
        recipe.min = min;
        recipe.max = max;

        grindingMap.put(Item.getIdFromItem(mineral),
                        recipe);
    }

    public ItemStack getRefiningResult(ItemStack item) {
        return getRefiningResult(item.getItem());
    }

    public ItemStack getRefiningResult(Item item) {
        return getRefiningResult(Item.getIdFromItem(item));
    }

    public ItemStack getRefiningResult(int mineralId) {
        if (!grindingMap.containsKey(mineralId)) return null;

        return grindingMap.get(mineralId).generateItemStack();

    }

    public class GrinderRecipe {
        public int             max;
        public int             min;
        public ItemMineralDust dust;

        public ItemStack generateItemStack() {
            return new ItemStack(dust, random.nextInt((max - min + 1)) + min);
        }
    }
}
