package net.bomb7878.worldscanner.datagen;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.custom.AlloyFurnaceBlock;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WorldScanner.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BLOCK_OF_BRONZE);
        blockWithItem(ModBlocks.BLOCK_OF_STEEL);
        blockWithItem(ModBlocks.BLOCK_OF_DARK_DIAMOND);
        blockWithItem(ModBlocks.BLOCK_OF_TIN);
        blockWithItem(ModBlocks.BLOCK_OF_RAW_TIN);
        blockWithItem(ModBlocks.DARK_DIAMOND_ORE);
        blockWithItem(ModBlocks.TIN_ORE);
        blockWithItem(ModBlocks.BLOCK_OF_DARK_STEEL);
        blockWithItem(ModBlocks.BLOCK_OF_ENDER_STEEL);

        registerAlloyFurnace("block/alloy_furnace_inactive", "block/alloy_furnace_active");
    }

    private void registerAlloyFurnace(String inactivePNGLocation, String activePNGLocation) {
        // Создаем модели для разных состояний
        ModelFile inactiveModel = models().getExistingFile(
                modLoc(inactivePNGLocation));
        ModelFile activeModel = models().getExistingFile(
                modLoc(activePNGLocation));

        // Генерируем все варианты состояний
        getVariantBuilder(ModBlocks.ALLOY_FURNACE.get())
                .forAllStates(state -> {
                    Direction facing = state.getValue(AlloyFurnaceBlock.FACING);
                    boolean active = state.getValue(AlloyFurnaceBlock.ACTIVE);

                    int yRot = switch (facing) {
                        case EAST -> 90;
                        case SOUTH -> 180;
                        case WEST -> 270;
                        default -> 0; // NORTH
                    };

                    ModelFile model = active ? activeModel : inactiveModel;
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(yRot)
                            .build();
                });
    }

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
