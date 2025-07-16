package net.bomb7878.worldscanner.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.recipe.AlloyFurnaceRecipe;
import net.bomb7878.worldscanner.recipe.util.IngredientWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AlloyFurnaceRecipeCategory implements IRecipeCategory<AlloyFurnaceRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(WorldScanner.MOD_ID, "alloying");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(WorldScanner.MOD_ID, "textures/gui/alloy_furnace.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public AlloyFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.ALLOY_FURNACE.get()));
        this.guiHelper = helper;
    }


    @Override
    public RecipeType<AlloyFurnaceRecipe> getRecipeType() {
        return JEIWorldScannerModPlugin.ALLOYING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.worldscanner.alloy_furnace");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(AlloyFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        int seconds = recipe.getProcessingTime() / 20;
        // Просто текст в фиксированной позиции
        Minecraft.getInstance().font.draw(stack,
                Component.translatable("gui.worldscanner.jei.category.alloying.time.seconds", seconds),
                130, 70, 0xFF808080);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 24)
                .addItemStacks(createItemStackWithCount(recipe.getIngredientCounts().get(0)));
        builder.addSlot(RecipeIngredientRole.INPUT, 97, 24)
                .addItemStacks(createItemStackWithCount(recipe.getIngredientCounts().get(1)));

        builder.addSlot(RecipeIngredientRole.INPUT, 15, 18)
                        .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluid()))
                        .setFluidRenderer(8000, false, 10,55);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 51)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 37, 15);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 37, 60 );
    }

    private List<ItemStack> createItemStackWithCount(IngredientWithCount ingredientWithCount) {
        List<ItemStack> stacks = List.of(ingredientWithCount.ingredient.getItems());
        if (ingredientWithCount.count > 1) {
            stacks.forEach(stack -> stack.setCount(ingredientWithCount.count));
        }
        return stacks;
    }
}
