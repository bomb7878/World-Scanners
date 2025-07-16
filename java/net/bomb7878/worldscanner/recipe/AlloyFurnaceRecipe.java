package net.bomb7878.worldscanner.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.recipe.util.IngredientWithCount;
import net.bomb7878.worldscanner.util.FluidJSONUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AlloyFurnaceRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final NonNullList<IngredientWithCount> ingredientWithCounts;
    private final FluidStack fluidStack;
    private final int processingTime;

    public AlloyFurnaceRecipe(ResourceLocation id, ItemStack output,
                              NonNullList<IngredientWithCount> ingredientWithCounts, FluidStack fluidStack, int processingTime) {
        this.id = id;
        this.result = output;
        this.ingredientWithCounts = ingredientWithCounts;
        this.fluidStack = fluidStack;
        this.processingTime = processingTime;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        List<Integer> inputSlots = Arrays.asList(1, 2); // Слоты для ингредиентов
        List<IngredientWithCount> remainingIngredients = new ArrayList<>(ingredientWithCounts);

        for (int slot : inputSlots) {
            ItemStack stackInSlot = pContainer.getItem(slot);
            if (stackInSlot.isEmpty()) continue;

            Iterator<IngredientWithCount> iterator = remainingIngredients.iterator();
            while (iterator.hasNext()) {
                IngredientWithCount ing = iterator.next();
                if (ing.ingredient.test(stackInSlot)){
                    if (stackInSlot.getCount() >= ing.count) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        return remainingIngredients.isEmpty();
    }

    public FluidStack getFluid() {
        return fluidStack;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public NonNullList<IngredientWithCount> getIngredientCounts() {
        return ingredientWithCounts;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return this.getResultItem(registryAccess).copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (IngredientWithCount ing : ingredientWithCounts) {
            ingredients.add(ing.ingredient);
        }
        return ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlloyFurnaceRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "alloying";
    }

    public static class Serializer implements RecipeSerializer<AlloyFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(WorldScanner.MOD_ID, "alloying");

        @Override
        public AlloyFurnaceRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<IngredientWithCount> ingredientWithCounts = NonNullList.create();

            for (JsonElement element : ingredients) {
                JsonObject ingredientObj = element.getAsJsonObject();
                Ingredient ingredient = Ingredient.fromJson(ingredientObj.get("ingredient"));
                int count = GsonHelper.getAsInt(ingredientObj, "count", 1);
                ingredientWithCounts.add(new IngredientWithCount(ingredient, count));
            }

            FluidStack fluid = FluidJSONUtil.readFluid(jsonObject.get("fluid").getAsJsonObject());
            int processingTime = GsonHelper.getAsInt(jsonObject, "processingTime", 200);

            return new AlloyFurnaceRecipe(id, output, ingredientWithCounts, fluid, processingTime);
        }

        @Override
        public @Nullable AlloyFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<IngredientWithCount> ingredientWithCounts = NonNullList.create();

            for (int i = 0; i < ingredientCount; i++) {
                Ingredient ingredient = Ingredient.fromNetwork(buf);
                int count = buf.readVarInt();
                ingredientWithCounts.add(new IngredientWithCount(ingredient, count));
            }

            ItemStack output = buf.readItem();
            FluidStack fluid = buf.readFluidStack();
            int processingTime = buf.readVarInt();

            return new AlloyFurnaceRecipe(id, output, ingredientWithCounts, fluid, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlloyFurnaceRecipe recipe) {
            buf.writeVarInt(recipe.ingredientWithCounts.size());

            for (IngredientWithCount ing : recipe.ingredientWithCounts) {
                ing.ingredient.toNetwork(buf);
                buf.writeVarInt(ing.count);
            }

            buf.writeItem(recipe.result);
            buf.writeFluidStack(recipe.fluidStack);
            buf.writeVarInt(recipe.processingTime);
        }
    }
}
