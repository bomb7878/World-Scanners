package net.bomb7878.worldscanner.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.recipe.AlloyFurnaceRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIWorldScannerModPlugin implements IModPlugin {
    private static IJeiRuntime jeiRuntime;
    public static RecipeType<AlloyFurnaceRecipe> ALLOYING_TYPE =
            new RecipeType<>(AlloyFurnaceRecipeCategory.UID, AlloyFurnaceRecipe.class);

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JEIWorldScannerModPlugin.jeiRuntime = jeiRuntime;
    }

    public static IJeiRuntime getJeiRuntime() {
        return jeiRuntime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WorldScanner.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new AlloyFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<AlloyFurnaceRecipe> recipesAlloying = rm.getAllRecipesFor(AlloyFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(ALLOYING_TYPE, recipesAlloying);
    }
}
