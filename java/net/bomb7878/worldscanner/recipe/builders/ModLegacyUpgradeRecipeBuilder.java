package net.bomb7878.worldscanner.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModLegacyUpgradeRecipeBuilder implements RecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<?> type;
    private String group;

    public ModLegacyUpgradeRecipeBuilder(Ingredient base, Ingredient addition, RecipeCategory category, Item result, RecipeSerializer<?> type) {
        this.base = base;
        this.addition = addition;
        this.category = category;
        this.result = result;
        this.type = type;
    }

    public static ModLegacyUpgradeRecipeBuilder smithing(Ingredient pBase, Ingredient pAddition, RecipeCategory pCategory, Item pResult) {
        return new ModLegacyUpgradeRecipeBuilder(pBase, pAddition, pCategory, pResult, RecipeSerializer.SMITHING);
    }

    public ModLegacyUpgradeRecipeBuilder unlocks(String pKey, CriterionTriggerInstance pCriterion) {
        this.advancement.addCriterion(pKey, pCriterion);
        return this;
    }

    @Override
    public ModLegacyUpgradeRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@org.jetbrains.annotations.Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        // Создаем путь для advancement (достижения)
        ResourceLocation advancementId = new ResourceLocation(
                resourceLocation.getNamespace(),
                "recipes/" + this.category.getFolderName() + "/" + resourceLocation.getPath());

        // Создаем и передаем готовый рецепт в consumer
        consumer.accept(new Result(
                resourceLocation,
                this.type,
                this.base,
                this.addition,
                this.result,
                this.advancement,
                advancementId
        ));
    }

    private void ensureValid(ResourceLocation pLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pLocation);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient addition;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<?> type;

        public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient pBase, Ingredient pAddition, Item pResult, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.type = pType;
            this.base = pBase;
            this.addition = pAddition;
            this.result = pResult;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("base", this.base.toJson());
            pJson.add("addition", this.addition.toJson());
            JsonObject $$1 = new JsonObject();
            $$1.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            pJson.add("result", $$1);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
