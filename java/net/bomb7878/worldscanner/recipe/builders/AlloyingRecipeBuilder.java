package net.bomb7878.worldscanner.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.bomb7878.worldscanner.recipe.AlloyFurnaceRecipe;
import net.bomb7878.worldscanner.recipe.util.IngredientWithCount;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import net.bomb7878.worldscanner.util.FluidJSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AlloyingRecipeBuilder implements RecipeBuilder {
    private final ItemStack result;
    private final List<IngredientWithCount> ingredients = new ArrayList<>();
    private final FluidStack fluid;
    private final int processingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeCategory category = RecipeCategory.MISC;

    public AlloyingRecipeBuilder(ItemStack result, FluidStack fluid, int processingTime) {
        this.result = result;
        this.fluid = fluid;
        this.processingTime = processingTime;
    }

    public static AlloyingRecipeBuilder alloying(ItemStack result, FluidStack fluid, int processingTime) {
        return new AlloyingRecipeBuilder(result, fluid, processingTime);
    }

    public AlloyingRecipeBuilder requires(Ingredient ingredient, int count) {
        this.ingredients.add(new IngredientWithCount(ingredient, count));
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(RequirementsStrategy.OR);

        finishedRecipeConsumer.accept(new Result(
                recipeId,
                this.result,
                this.ingredients,
                this.fluid,
                this.processingTime,
                this.advancement,
                new ResourceLocation(recipeId.getNamespace(), "recipes/" + this.category.getFolderName() + "/" + recipeId.getPath()))
        );
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final List<IngredientWithCount> ingredients;
        private final FluidStack fluid;
        private final int processingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack result, List<IngredientWithCount> ingredients,
                      FluidStack fluid, int processingTime, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.ingredients = ingredients;
            this.fluid = fluid;
            this.processingTime = processingTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            // Добавляем ингредиенты с количеством
            JsonArray ingredientsArray = new JsonArray();
            for (IngredientWithCount ing : this.ingredients) {
                JsonObject ingredientJson = new JsonObject();
                ingredientJson.add("ingredient", ing.ingredient.toJson());
                if (ing.count > 1) {
                    ingredientJson.addProperty("count", ing.count);
                }
                ingredientsArray.add(ingredientJson);
            }
            json.add("ingredients", ingredientsArray);

            // Добавляем результат
            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result.getItem()).toString());
            if (this.result.getCount() > 1) {
                resultJson.addProperty("count", this.result.getCount());
            }
            json.add("result", resultJson);

            // Добавляем жидкость
            json.add("fluid", FluidJSONUtil.toJson(this.fluid));

            // Добавляем время обработки
            json.addProperty("processingTime", this.processingTime);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return AlloyFurnaceRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}