package net.bomb7878.worldscanner.blocks;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.custom.AlloyFurnaceBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WorldScanner.MOD_ID);

    public static final RegistryObject<Block> DARK_DIAMOND_ORE = BLOCKS.register("dark_diamond_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5.0f, 4.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE),
                    UniformInt.of(5,8))
    );

    public static final RegistryObject<Block> BLOCK_OF_DARK_DIAMOND = BLOCKS.register("block_of_dark_diamond",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
            .strength(5.0f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.AMETHYST))
    );

    public static final RegistryObject<Block> ALLOY_FURNACE = BLOCKS.register(
            "alloy_furnace", AlloyFurnaceBlock::new
    );

    public static final RegistryObject<Block> BLOCK_OF_BRONZE = BLOCKS.register("block_of_bronze",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL))
    );

    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0f, 3.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistryObject<Block> BLOCK_OF_RAW_TIN = BLOCKS.register("block_of_raw_tin",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistryObject<Block> BLOCK_OF_TIN = BLOCKS.register("block_of_tin",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistryObject<Block> BLOCK_OF_STEEL = BLOCKS.register("block_of_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.5f, 6.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistryObject<Block> BLOCK_OF_DARK_STEEL = BLOCKS.register("block_of_dark_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(50.0f, 1200.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistryObject<Block> BLOCK_OF_ENDER_STEEL = BLOCKS.register("block_of_ender_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(75.0f, 1500.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );
}
