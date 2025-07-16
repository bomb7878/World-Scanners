package net.bomb7878.worldscanner.recipe.util;

import net.minecraft.world.item.crafting.Ingredient;

public class IngredientWithCount {
    public final Ingredient ingredient;
    public final int count;

    public IngredientWithCount(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    protected Ingredient getIngredient() {
        return ingredient;
    }

    protected int getCount() {
        return count;
    }
}
