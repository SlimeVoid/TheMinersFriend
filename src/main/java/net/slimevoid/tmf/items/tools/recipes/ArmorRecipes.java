package net.slimevoid.tmf.items.tools.recipes;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ArmorRecipes implements IRecipe {

    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack           recipeOutput;

    /** Is a List of ItemStack that composes the recipe. */
    public final ArrayList<ItemStack> recipeItems;

    /**
     * Constructor to create a recipe that doesn't ignores the Chef Knife's
     * damage value.
     * 
     * @param itemStackOutput
     *            The output of the recipe.
     * @param inputRecipe
     *            The recipe needed to create the output ItemStack.
     */
    public ArmorRecipes(ItemStack itemStackOutput, Object... inputRecipe) {
        this.recipeOutput = itemStackOutput;
        this.recipeItems = recipeAsArrayList(inputRecipe);
    }

    /**
     * Helper method that takes apart the recipe and puts it into a list.
     * Essentially identical to the implementation of
     * CraftingManager.addShapelessRecipe(ItemStack, Object...).
     * 
     * @param recipe
     *            The recipe, as an Object[], to be changed to an arrayList.
     * @return
     */
    private ArrayList<ItemStack> recipeAsArrayList(Object[] recipe) {
        ArrayList<ItemStack> recipeList = new ArrayList<ItemStack>();
        int i = recipe.length;

        for (int j = 0; j < i; ++j) {
            Object partOfRecipe = recipe[j];

            if (partOfRecipe instanceof ItemStack) {
                recipeList.add(((ItemStack) partOfRecipe).copy());
            } else if (partOfRecipe instanceof Item) {
                recipeList.add(new ItemStack((Item) partOfRecipe));
            } else {
                if (!(partOfRecipe instanceof Block)) {
                    throw new RuntimeException("Invalid Helmet Recipe!  Attemped to add: "
                                               + partOfRecipe + " to recipe");
                }

                recipeList.add(new ItemStack((Block) partOfRecipe));
            }
        }
        return recipeList;
    }

    /**
     * Used to check if a recipe matches current crafting inventory. Different
     * from vanilla one in that it doesn't compare damage value of the chef
     * knife.
     * 
     * @param inventoryCrafting
     *            The inventory of the crafting table being used by the player.
     * @param world
     *            The current world that player is in.
     */
    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ArrayList<ItemStack> recipeList = new ArrayList<ItemStack>(this.recipeItems);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemInCraftingTable = inventoryCrafting.getStackInRowAndColumn(j,
                                                                                         i);

                if (itemInCraftingTable != null) {
                    boolean flag = false;
                    Iterator<ItemStack> itemsInRecipe = recipeList.iterator();

                    while (itemsInRecipe.hasNext()) {
                        ItemStack itemInRecipe = itemsInRecipe.next();

                        if (itemInCraftingTable.getItem().equals(itemInRecipe.getItem())) {
                            flag = true;
                            recipeList.remove(itemInRecipe);
                            break;

                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }
            }
        }

        return recipeList.isEmpty();
    }

    protected int getArmorDamage(InventoryCrafting inventoryCrafting) {
        int armorDamage = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemInCraftingTable = inventoryCrafting.getStackInRowAndColumn(j,
                                                                                         i);
                if (itemInCraftingTable != null
                    && itemInCraftingTable.getItem() instanceof ItemArmor) {
                    armorDamage = itemInCraftingTable.getItemDamage();
                }
            }
        }
        return armorDamage;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack output = this.recipeOutput.copy();
        output.setItemDamage(this.getArmorDamage(inventoryCrafting));
        return output;
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

}
