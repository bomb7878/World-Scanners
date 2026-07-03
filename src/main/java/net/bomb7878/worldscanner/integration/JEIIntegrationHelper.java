package net.bomb7878.worldscanner.integration;

import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.bomb7878.worldscanner.integration.categories.AdvancedAlloyFurnaceRecipeCategory;
import net.bomb7878.worldscanner.integration.categories.AlloyFurnaceRecipeCategory;
import net.bomb7878.worldscanner.recipe.AdvancedAlloyFurnaceRecipe;
import net.bomb7878.worldscanner.recipe.AlloyFurnaceRecipe;
import net.bomb7878.worldscanner.screen.blocks.AdvancedAlloyFurnaceScreen;
import net.bomb7878.worldscanner.screen.blocks.AlloyFurnaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

public class JEIIntegrationHelper {
    public static void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new AlloyFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new AdvancedAlloyFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    public static void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<AlloyFurnaceRecipe> recipesAlloying = rm.getAllRecipesFor(AlloyFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(AlloyFurnaceRecipeCategory.ALLOYING_TYPE, recipesAlloying);

        List<AdvancedAlloyFurnaceRecipe> recipesAdvancedAlloying = rm.getAllRecipesFor(AdvancedAlloyFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(AdvancedAlloyFurnaceRecipeCategory.ADVANCED_ALLOYING_TYPE, recipesAdvancedAlloying);
    }

    public static void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloyFurnaceScreen.class,
                81, 30, 14, 19,
                AlloyFurnaceRecipeCategory.ALLOYING_TYPE);

        registration.addRecipeClickArea(AdvancedAlloyFurnaceScreen.class,
                81, 30, 14, 19,
                AdvancedAlloyFurnaceRecipeCategory.ADVANCED_ALLOYING_TYPE);
    }
}
