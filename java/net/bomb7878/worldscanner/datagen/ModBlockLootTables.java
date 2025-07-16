package net.bomb7878.worldscanner.datagen;

import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.items.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BLOCK_OF_RAW_TIN.get());
        dropSelf(ModBlocks.BLOCK_OF_TIN.get());
        dropSelf(ModBlocks.BLOCK_OF_DARK_DIAMOND.get());
        dropSelf(ModBlocks.ALLOY_FURNACE.get());
        dropSelf(ModBlocks.BLOCK_OF_BRONZE.get());
        dropSelf(ModBlocks.BLOCK_OF_STEEL.get());
        dropSelf(ModBlocks.BLOCK_OF_DARK_STEEL.get());
        dropSelf(ModBlocks.BLOCK_OF_ENDER_STEEL.get());

        add(ModBlocks.DARK_DIAMOND_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DARK_DIAMOND_ORE.get(), ModItems.DARK_DIAMOND.get()));
        add(ModBlocks.TIN_ORE.get(),
                (block) -> createSimilarCopperOreDrops(ModBlocks.TIN_ORE.get(), ModItems.RAW_TIN.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected LootTable.Builder createSimilarCopperOreDrops(Block block, Item item) {
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
