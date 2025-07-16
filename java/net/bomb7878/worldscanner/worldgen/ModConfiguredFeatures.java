package net.bomb7878.worldscanner.worldgen;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_DIAMOND_ORE_KEY = registryKey("dark_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE_KEY = registryKey("tin_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> darkDiamondOre = List.of(OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DARK_DIAMOND_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> tinOre = List.of(OreConfiguration.target(stoneReplaceables,
                ModBlocks.TIN_ORE.get().defaultBlockState()));

        register(context, DARK_DIAMOND_ORE_KEY, Feature.ORE, new OreConfiguration(darkDiamondOre, 4, 0.5F));
        register(context, TIN_ORE_KEY, Feature.ORE, new OreConfiguration(tinOre, 15));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registryKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WorldScanner.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
