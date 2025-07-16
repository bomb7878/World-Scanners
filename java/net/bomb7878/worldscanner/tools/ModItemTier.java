package net.bomb7878.worldscanner.tools;

import net.bomb7878.worldscanner.items.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModItemTier implements Tier {
    TIN(1, 200, 5.0F, 1.5F, 10, () -> Ingredient.of(ModItems.TIN_INGOT.get())),
    BRONZE(2,300, 6.0F, 2.0F, 14, () -> Ingredient.of(ModItems.BRONZE_INGOT.get())),
    STEEL(3, 900, 7.0F, 2.5F, 14, () -> Ingredient.of(ModItems.STEEL_INGOT.get())),
    DARK_DIAMOND(3, 1796, 8.5F, 3.5F, 15, () -> Ingredient.of(ModItems.DARK_DIAMOND.get())),
    DARK_STEEL(4, 2592, 10.0F, 5.0F, 22, () -> Ingredient.of(ModItems.DARK_STEEL_INGOT.get())),
    ENDER_STEEL(4, 3253, 12.0F, 7.0F, 25, () -> Ingredient.of(ModItems.ENDER_STEEL_INGOT.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModItemTier(int level, int uses, float speed, float attackDamage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
