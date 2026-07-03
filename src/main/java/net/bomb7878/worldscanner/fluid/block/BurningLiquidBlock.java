package net.bomb7878.worldscanner.fluid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;
import java.util.function.Supplier;
/**
 * Based on BurningLiquidBlock from Tinkers' Construct (by SlimeKnights)
 * Original source: https://github.com/SlimeKnights/TinkersConstruct/blob/1.20.1/LICENSE
 * Licensed under MIT License
 */
public class BurningLiquidBlock extends LiquidBlock {
    private final int burnTime;
    private final float damage;

    public BurningLiquidBlock(Supplier<? extends FlowingFluid> supplier, BlockBehaviour.Properties properties, int burnTime, float damage) {
        super(supplier, properties);
        this.burnTime = burnTime;
        this.damage = damage;
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // Проверка: если entity не огнеупорна и погружена в жидкость
        if (!entity.fireImmune() && entity.getFluidTypeHeight(this.getFluid().getFluidType()) > 0.0) {
            // Поджечь entity на burnTime тиков
            entity.setSecondsOnFire(this.burnTime);

            // Нанести урон и воспроизвести звук при успехе
            if (entity.hurt(level.damageSources().lava(), this.damage)) {
                entity.playSound(SoundEvents.LAVA_POP, 0.4F, 2.0F + level.random.nextFloat() * 0.4F);
            }
        }
    }

    public static Function<Supplier<? extends FlowingFluid>, LiquidBlock> createBurning(int lightLevel, int burnTime, float damage) {
        return (fluid) -> {
            return new BurningLiquidBlock(fluid,
                    Properties.of(Material.LAVA)
                            .lightLevel((state) -> lightLevel)
                            .strength(100.0F)
                            .noLootTable()
                            .noCollission()
                            .noOcclusion(),
                    burnTime, damage);
        };
    }
}
