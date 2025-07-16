package net.bomb7878.worldscanner.datagen;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.items.ModItems;
import net.bomb7878.worldscanner.recipe.builders.AlloyingRecipeBuilder;
import net.bomb7878.worldscanner.recipe.builders.ModLegacyUpgradeRecipeBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_DARK_DIAMOND.get(), ModItems.DARK_DIAMOND, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_DARK_DIAMOND, ModItems.DARK_DIAMOND, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_RAW_TIN.get(), ModItems.RAW_TIN, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_RAW_TIN, ModItems.RAW_TIN, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_TIN.get(), ModItems.TIN_INGOT, consumer);
        createSimpleIngotRecipe(ModItems.TIN_INGOT.get(), ModItems.TIN_NUGGET, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_TIN, ModItems.TIN_INGOT, consumer);
        createNineItemsFromItemRecipe(ModItems.TIN_INGOT, ModItems.TIN_NUGGET, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_BRONZE.get(), ModItems.BRONZE_INGOT, consumer);
        createSimpleIngotRecipe(ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_NUGGET, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_BRONZE, ModItems.BRONZE_INGOT, consumer);
        createNineItemsFromItemRecipe(ModItems.BRONZE_INGOT, ModItems.BRONZE_NUGGET, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_STEEL.get(), ModItems.STEEL_INGOT, consumer);
        createSimpleIngotRecipe(ModItems.STEEL_INGOT.get(), ModItems.STEEL_NUGGET, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_STEEL, ModItems.STEEL_INGOT, consumer);
        createNineItemsFromItemRecipe(ModItems.STEEL_INGOT, ModItems.STEEL_NUGGET, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_DARK_STEEL.get(), ModItems.DARK_STEEL_INGOT, consumer);
        createSimpleIngotRecipe(ModItems.DARK_STEEL_INGOT.get(), ModItems.DARK_STEEL_NUGGET, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_DARK_STEEL, ModItems.DARK_STEEL_INGOT, consumer);
        createNineItemsFromItemRecipe(ModItems.DARK_STEEL_INGOT, ModItems.DARK_STEEL_NUGGET, consumer);

        createSimpleBlockRecipe(ModBlocks.BLOCK_OF_ENDER_STEEL.get(), ModItems.ENDER_STEEL_INGOT, consumer);
        createSimpleIngotRecipe(ModItems.ENDER_STEEL_INGOT.get(), ModItems.ENDER_STEEL_NUGGET, consumer);
        createNineItemsFromBlockRecipe(ModBlocks.BLOCK_OF_ENDER_STEEL, ModItems.ENDER_STEEL_INGOT, consumer);
        createNineItemsFromItemRecipe(ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_NUGGET, consumer);

        createEquipmentRecipe(ModItems.TIN_SWORD, ModItems.TIN_INGOT, consumer, 1);
        createEquipmentRecipe(ModItems.TIN_PICKAXE, ModItems.TIN_INGOT, consumer, 2);
        createEquipmentRecipe(ModItems.TIN_AXE, ModItems.TIN_INGOT, consumer, 3);
        createEquipmentRecipe(ModItems.TIN_SHOVEL, ModItems.TIN_INGOT, consumer, 4);
        createEquipmentRecipe(ModItems.TIN_HOE, ModItems.TIN_INGOT, consumer, 5);

        createEquipmentRecipe(ModItems.BRONZE_SWORD, ModItems.BRONZE_INGOT, consumer, 1);
        createEquipmentRecipe(ModItems.BRONZE_PICKAXE, ModItems.BRONZE_INGOT, consumer, 2);
        createEquipmentRecipe(ModItems.BRONZE_AXE, ModItems.BRONZE_INGOT, consumer, 3);
        createEquipmentRecipe(ModItems.BRONZE_SHOVEL, ModItems.BRONZE_INGOT, consumer, 4);
        createEquipmentRecipe(ModItems.BRONZE_HOE, ModItems.BRONZE_INGOT, consumer, 5);

        createEquipmentRecipe(ModItems.STEEL_SWORD, ModItems.STEEL_INGOT, consumer, 1);
        createEquipmentRecipe(ModItems.STEEL_PICKAXE, ModItems.STEEL_INGOT, consumer, 2);
        createEquipmentRecipe(ModItems.STEEL_AXE, ModItems.STEEL_INGOT, consumer, 3);
        createEquipmentRecipe(ModItems.STEEL_SHOVEL, ModItems.STEEL_INGOT, consumer, 4);
        createEquipmentRecipe(ModItems.STEEL_HOE, ModItems.STEEL_INGOT, consumer, 5);

        createEquipmentRecipe(ModItems.DARK_STEEL_SWORD, ModItems.DARK_STEEL_INGOT, consumer, 1);
        createEquipmentRecipe(ModItems.DARK_STEEL_PICKAXE, ModItems.DARK_STEEL_INGOT, consumer, 2);
        createEquipmentRecipe(ModItems.DARK_STEEL_AXE, ModItems.DARK_STEEL_INGOT, consumer, 3);
        createEquipmentRecipe(ModItems.DARK_STEEL_SHOVEL, ModItems.DARK_STEEL_INGOT, consumer, 4);
        createEquipmentRecipe(ModItems.DARK_STEEL_HOE, ModItems.DARK_STEEL_INGOT, consumer, 5);

        createEquipmentSmithingRecipe(ModItems.DARK_STEEL_SWORD, ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_SWORD, consumer, 1);
        createEquipmentSmithingRecipe(ModItems.DARK_STEEL_PICKAXE, ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_PICKAXE, consumer, 2);
        createEquipmentSmithingRecipe(ModItems.DARK_STEEL_AXE, ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_AXE, consumer, 2);
        createEquipmentSmithingRecipe(ModItems.DARK_STEEL_SHOVEL, ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_SHOVEL, consumer, 2);
        createEquipmentSmithingRecipe(ModItems.DARK_STEEL_HOE, ModItems.ENDER_STEEL_INGOT, ModItems.ENDER_STEEL_HOE, consumer, 2);

        createSimpleSmelting(consumer, List.of(ModItems.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot");
        createSimpleBlasting(consumer, List.of(ModItems.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot");
        createSimpleSmelting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot");
        createSimpleBlasting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot");
        createBlockOfRawOreToBlockOfOreIngotsSmelting(consumer, ModBlocks.BLOCK_OF_RAW_TIN.get(), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.BLOCK_OF_TIN.get(), 6.3f, 1800, "tin_block");
        createBlockOfRawOreToBlockOfOreIngotsBlasting(consumer, ModBlocks.BLOCK_OF_RAW_TIN.get(), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.BLOCK_OF_TIN.get(), 6.3f, 900, "tin_block");

        createSimpleSmelting(consumer, List.of(ModItems.DARK_DIAMOND_ORE.get()), RecipeCategory.MISC,
                ModItems.DARK_DIAMOND.get(), 1.0f, 200, "dark_diamond");
        createSimpleBlasting(consumer, List.of(ModItems.DARK_DIAMOND_ORE.get()), RecipeCategory.MISC,
                ModItems.DARK_DIAMOND.get(), 0.7f, 100, "dark_diamond");

        createBlockOfRawOreToBlockOfOreIngotsSmelting(consumer, Blocks.RAW_COPPER_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.COPPER_BLOCK, 6.3f, 1800, "copper_block");
        createBlockOfRawOreToBlockOfOreIngotsBlasting(consumer, Blocks.RAW_COPPER_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.COPPER_BLOCK, 6.3f, 900, "copper_block");
        createBlockOfRawOreToBlockOfOreIngotsSmelting(consumer, Blocks.RAW_IRON_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.IRON_BLOCK, 6.3f, 1800, "iron_block");
        createBlockOfRawOreToBlockOfOreIngotsBlasting(consumer, Blocks.RAW_IRON_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.IRON_BLOCK, 6.3f, 900, "iron_block");
        createBlockOfRawOreToBlockOfOreIngotsSmelting(consumer, Blocks.RAW_GOLD_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.GOLD_BLOCK, 7.0f, 1800, "gold_block");
        createBlockOfRawOreToBlockOfOreIngotsBlasting(consumer, Blocks.RAW_GOLD_BLOCK, RecipeCategory.BUILDING_BLOCKS,
                Blocks.GOLD_BLOCK, 7.0f, 900, "gold_block");

        createAlloyingRecipe(ModItems.BRONZE_INGOT, 4, 100, 260,
                Items.COPPER_INGOT, 3, ModItems.TIN_INGOT.get(), 1, consumer);

        createAlloyingRecipe(ModItems.STEEL_INGOT, 1, 250, 300,
                Items.IRON_INGOT, 1, Items.COAL, 4, consumer);

        createAlloyingRecipe(ModItems.DARK_STEEL_INGOT, 8, 350, 500,
                ModItems.STEEL_INGOT.get(), 8, ModItems.ANCIENT_DARK_CRYSTAL.get(), 1, consumer);

        createAlloyingRecipe(ModItems.ENDER_STEEL_NUGGET, 3, 500, 360,
                ModItems.DARK_STEEL_INGOT.get(), 1, Items.ENDER_EYE, 1, consumer);

        createAlloyingRecipe(ModItems.BLOCK_OF_BRONZE, 4, 750, 1500,
                Items.COPPER_BLOCK, 3, ModItems.BLOCK_OF_TIN.get(), 1, consumer);

        createAlloyingRecipe(ModItems.BLOCK_OF_STEEL, 1, 1000, 2000,
                Items.IRON_BLOCK, 1, Items.COAL_BLOCK, 4, consumer);

        createAlloyingRecipe(ModItems.ENDER_STEEL_INGOT, 3, 1500, 2400,
                ModItems.BLOCK_OF_DARK_STEEL.get(), 1, Items.ENDER_EYE, 9, consumer);
    }

    private static void createSimpleBlockRecipe(Block block, RegistryObject<Item> item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .define('K', item.get())
                .pattern("KKK")
                .pattern("KKK")
                .pattern("KKK")
                .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(item.get()).build()))
                .save(consumer);
    }

    private static void createSimpleIngotRecipe(Item ingot, RegistryObject<Item> nugget, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .define('K', nugget.get())
                .pattern("KKK")
                .pattern("KKK")
                .pattern("KKK")
                .unlockedBy("has_" + nugget.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(nugget.get()).build()))
                .save(consumer, new ResourceLocation(WorldScanner.MOD_ID, getItemName(ingot)) + "_from_" + getItemName(nugget.get()));
    }

    private static void createNineItemsFromBlockRecipe(RegistryObject<Block> block, RegistryObject<Item> item, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get(), 9)
                .requires(block.get())
                .unlockedBy("has_" + block.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(block.get()).build()))
                .save(consumer, new ResourceLocation(WorldScanner.MOD_ID, getItemName(item.get())) + "_from_" + getItemName(block.get()));
    }

    private static void createNineItemsFromItemRecipe(RegistryObject<Item> parentItem, RegistryObject<Item> childItem, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, childItem.get(), 9)
                .requires(parentItem.get())
                .unlockedBy("has_" + parentItem.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                        .of(parentItem.get()).build()))
                .save(consumer);
    }

    /**
     * categoryModifier:
     * 1 -> sword
     * 2 -> pickaxe
     * 3 -> axe
     * 4 -> shovel
     * 5 -> hoe
     */
    private static void createEquipmentRecipe(RegistryObject<Item> tool, RegistryObject<Item> item, Consumer<FinishedRecipe> consumer, int categoryModifier) {
        if (categoryModifier > 6 || categoryModifier < 1) {
            throw new IllegalArgumentException("Invalid tool modifier: " + categoryModifier);
        }

        if (categoryModifier == 1) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, tool.get())
                    .define('K', item.get())
                    .define('S', Ingredient.of(Items.STICK))
                    .pattern(" K ")
                    .pattern(" K ")
                    .pattern(" S ")
                    .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(item.get()).build()))
                    .save(consumer);
        } else if (categoryModifier == 2) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool.get())
                    .define('K', item.get())
                    .define('S', Ingredient.of(Items.STICK))
                    .pattern("KKK")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(item.get()).build()))
                    .save(consumer);
        } else if (categoryModifier == 3) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool.get())
                    .define('K', item.get())
                    .define('S', Ingredient.of(Items.STICK))
                    .pattern("KK ")
                    .pattern("KS ")
                    .pattern(" S ")
                    .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(item.get()).build()))
                    .save(consumer);
        } else if (categoryModifier == 4) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool.get())
                    .define('K', item.get())
                    .define('S', Ingredient.of(Items.STICK))
                    .pattern(" K ")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(item.get()).build()))
                    .save(consumer);
        } else if (categoryModifier == 5) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool.get())
                    .define('K', item.get())
                    .define('S', Ingredient.of(Items.STICK))
                    .pattern("KK ")
                    .pattern(" S ")
                    .pattern(" S ")
                    .unlockedBy("has_" + item.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(item.get()).build()))
                    .save(consumer);
        }
    }

    protected static void oreCooking(Consumer<FinishedRecipe> consumer, RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer, List<ItemLike> itemLikes, RecipeCategory category, ItemLike itemLike, float experience, int ticks, String group, String processingMethodModifier) {
        Iterator var9 = itemLikes.iterator();

        while (var9.hasNext()) {
            ItemLike itemlike = (ItemLike) var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), category, itemLike, experience, ticks, recipeSerializer).group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(consumer, new ResourceLocation(WorldScanner.MOD_ID, getItemName(itemLike)) + processingMethodModifier + "_" + getItemName(itemlike));
        }

    }

    protected static void createSimpleSmelting(Consumer<FinishedRecipe> consumer, List<ItemLike> itemLikes, RecipeCategory recipeCategory, ItemLike itemLike, float experience, int ticks, String group) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, itemLikes, recipeCategory, itemLike, experience, ticks, group, "_from_smelting");
    }

    protected static void createSimpleBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> itemLikes, RecipeCategory recipeCategory, ItemLike itemLike, float experience, int ticks, String group) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, itemLikes, recipeCategory, itemLike, experience, ticks, group, "_from_blasting");
    }

    protected static void createBlockOfRawOreToBlockOfOreIngotsSmelting(Consumer<FinishedRecipe> consumer, Block rawOreBlock, RecipeCategory recipeCategory, Block blockOfOreIngots, float experience, int ticks, String group) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, List.of(rawOreBlock), recipeCategory, blockOfOreIngots, experience, ticks, group, "_of_smelting_from");
    }

    protected static void createBlockOfRawOreToBlockOfOreIngotsBlasting(Consumer<FinishedRecipe> consumer, Block rawOreBlock, RecipeCategory recipeCategory, Block blockOfOreIngots, float experience, int ticks, String group) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, List.of(rawOreBlock), recipeCategory, blockOfOreIngots, experience, ticks, group, "_of_blasting_from");
    }

    protected static void createEquipmentSmithingTransformRecipe(Item improvedTool, RegistryObject<Item> addition, Item resultItem, Consumer<FinishedRecipe> consumer, int toolModifier) {
        if (toolModifier < 1 || toolModifier > 2) {
            throw new IllegalArgumentException("Tool modifier must be 1 or 2");
        }

        if (toolModifier == 1) {
            SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ENDER_STEEL_UPGRADE_SMITHING_TEMPLATE.get()),
                            Ingredient.of(improvedTool),
                            Ingredient.of(addition.get()), RecipeCategory.COMBAT, resultItem)
                    .unlocks("has_" + addition.getId().getPath(), has(addition.get()))
                    .save(consumer, getItemName(resultItem) + "_smithing");
        } else if (toolModifier == 2) {
            SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ENDER_STEEL_UPGRADE_SMITHING_TEMPLATE.get()),
                            Ingredient.of(improvedTool),
                            Ingredient.of(addition.get()), RecipeCategory.TOOLS, resultItem)
                    .unlocks("has_" + addition.getId().getPath(), has(addition.get()))
                    .save(consumer, getItemName(resultItem) + "_smithing");
        }
    }
    /**
     categoryModifier:
     1 -> sword and armor
     2 -> pickaxe, axe, shovel, hoe
     */
    protected static void createEquipmentSmithingRecipe(RegistryObject<Item> improvedTool, RegistryObject<Item> improvingIngredient, RegistryObject<Item> resultItem, Consumer<FinishedRecipe> consumer, int categoryModifier) {
        if (categoryModifier < 1 || categoryModifier > 2) {
            throw new IllegalArgumentException("Tool modifier must be 1 or 2");
        }

        if (categoryModifier == 1) {
            ModLegacyUpgradeRecipeBuilder.smithing(Ingredient.of(improvedTool.get()), Ingredient.of(improvingIngredient.get()), RecipeCategory.COMBAT, resultItem.get())
                    .unlockedBy("has_" + improvingIngredient.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(improvingIngredient.get()).build()))
                    .save(consumer, new ResourceLocation(WorldScanner.MOD_ID, resultItem.getId().getPath() + "_smithing"));
        }else if(categoryModifier == 2) {
            ModLegacyUpgradeRecipeBuilder.smithing(Ingredient.of(improvedTool.get()), Ingredient.of(improvingIngredient.get()), RecipeCategory.TOOLS, resultItem.get())
                    .unlockedBy("has_" + improvingIngredient.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
                            .of(improvingIngredient.get()).build()))
                    .save(consumer, new ResourceLocation(WorldScanner.MOD_ID, resultItem.getId().getPath() + "_smithing"));
        }
    }

    protected static void createAlloyingRecipe(RegistryObject<Item> resultItem, int resultItemCount, int fluidAmount, int processingTime, Item firstItem, int firstItemCount, Item secondItem, int secondItemCount, Consumer<FinishedRecipe> consumer){
        AlloyingRecipeBuilder.alloying(
                        new ItemStack(resultItem.get(), resultItemCount),
                        new FluidStack(Fluids.LAVA, fluidAmount),
                        processingTime
                )
                .requires(Ingredient.of(firstItem), firstItemCount)
                .requires(Ingredient.of(secondItem), secondItemCount)
                .unlockedBy("has_" + firstItem.getDescriptionId(), has(firstItem))
                .unlockedBy("has_" + secondItem.getDescriptionId(), has(secondItem))
                .save(consumer, new ResourceLocation(WorldScanner.MOD_ID,getItemName(resultItem.get()) + "_from_alloying"));
    }
}
