package net.bomb7878.worldscanner.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.util.IngredientWithCount;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
public class AdvancedAlloyFurnaceRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final NonNullList<IngredientWithCount> ingredientWithCounts;
    private final List<FluidStack> fluidOptions;
    private final int processingTime;

    public AdvancedAlloyFurnaceRecipe(ResourceLocation id, ItemStack output,
            NonNullList<IngredientWithCount> ingredientWithCounts, List<FluidStack> fluidOptions, int processingTime) {
        this.id = id;
        this.result = output;
        this.ingredientWithCounts = ingredientWithCounts;
        this.fluidOptions = fluidOptions;
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

    public boolean matchesFluid(FluidStack tankFluid) {
        if (fluidOptions.isEmpty()) return true; // Рецепт не требует жидкости

        for (FluidStack option : fluidOptions) {
            if (tankFluid.isFluidEqual(option) &&
                    tankFluid.getAmount() >= option.getAmount()) {
                return true;
            }
        }
        return false;
    }

    public List<FluidStack> getFluidOptions() {
        return fluidOptions;
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

    public static class Type implements RecipeType<AdvancedAlloyFurnaceRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "advanced_alloying";
    }

    public static class Serializer implements RecipeSerializer<AdvancedAlloyFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(WorldScanner.MOD_ID, "advanced_alloying");

        @Override
        public AdvancedAlloyFurnaceRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<IngredientWithCount> ingredientWithCounts = NonNullList.create();

            for (JsonElement element : ingredients) {
                JsonObject ingredientObj = element.getAsJsonObject();
                Ingredient ingredient = Ingredient.fromJson(ingredientObj.get("ingredient"));
                int count = GsonHelper.getAsInt(ingredientObj, "count", 1);
                ingredientWithCounts.add(new IngredientWithCount(ingredient, count));
            }

            JsonArray fluidsArray = GsonHelper.getAsJsonArray(jsonObject, "fluids");
            List<FluidStack> fluidOptions = new ArrayList<>();
            for (JsonElement element : fluidsArray) {
                fluidOptions.add(FluidJSONUtil.readFluid(element.getAsJsonObject()));
            }

            int processingTime = GsonHelper.getAsInt(jsonObject, "processingTime", 100);

            return new AdvancedAlloyFurnaceRecipe(id, output, ingredientWithCounts, fluidOptions, processingTime);
        }

        @Override
        public @Nullable AdvancedAlloyFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<IngredientWithCount> ingredientWithCounts = NonNullList.create();

            for (int i = 0; i < ingredientCount; i++) {
                Ingredient ingredient = Ingredient.fromNetwork(buf);
                int count = buf.readVarInt();
                ingredientWithCounts.add(new IngredientWithCount(ingredient, count));
            }

            ItemStack output = buf.readItem();

            int fluidCount = buf.readVarInt();
            NonNullList<FluidStack> fluidStacks = NonNullList.create();
            for (int i = 0; i < fluidCount; i++) {
                fluidStacks.add(buf.readFluidStack());
            }

            int processingTime = buf.readVarInt();

            return new AdvancedAlloyFurnaceRecipe(id, output, ingredientWithCounts, fluidStacks, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AdvancedAlloyFurnaceRecipe recipe) {
            buf.writeVarInt(recipe.ingredientWithCounts.size());

            for (IngredientWithCount ing : recipe.ingredientWithCounts) {
                ing.ingredient.toNetwork(buf);
                buf.writeVarInt(ing.count);
            }

            buf.writeItem(recipe.result);

            buf.writeVarInt(recipe.fluidOptions.size());
            for (FluidStack fluid : recipe.fluidOptions) {
                buf.writeFluidStack(fluid);
            }

            buf.writeVarInt(recipe.processingTime);
        }
    }
}
