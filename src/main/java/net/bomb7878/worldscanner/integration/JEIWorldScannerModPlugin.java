package net.bomb7878.worldscanner.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.bomb7878.worldscanner.WorldScanner;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

@JeiPlugin
public class JEIWorldScannerModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WorldScanner.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        if(ModList.get().isLoaded("jei")) {
            JEIIntegrationHelper.registerCategories(registration);
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if(ModList.get().isLoaded("jei")) {
            JEIIntegrationHelper.registerRecipes(registration);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        if(ModList.get().isLoaded("jei")) {
            JEIIntegrationHelper.registerGuiHandlers(registration);
        }
    }
}
