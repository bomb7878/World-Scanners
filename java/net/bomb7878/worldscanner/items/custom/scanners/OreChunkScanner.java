package net.bomb7878.worldscanner.items.custom.scanners;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class OreChunkScanner extends AbstractScannerItem {
    // Тег для руд — в него должны быть добавлены как ванильные, так и модовые руды
    private static final TagKey<Block> ORES_TAG = BlockTags.create(new ResourceLocation("forge", "ores"));

    @Override
    protected ScanResult performScan(Level level, Player player) {
        ScanResult result = new ScanResult();
        ChunkPos chunkPos = new ChunkPos(player.blockPosition());
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
            for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                for (int y = level.getMinBuildHeight(); y <= level.getMaxBuildHeight(); y++) {
                    pos.set(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    boolean isOreBlockTagged = state.is(ORES_TAG);

                    if (isOreBlockTagged) {
                        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                        String oreName = getTranslatedName(state.getBlock().getDescriptionId());
                        assert blockId != null;
                        result.add(oreName, blockId, y);
                    }
                }
            }
        }
        return result;
    }

    private String getTranslatedName(String descriptionId) {
        return Component.translatable(descriptionId).getString();
    }
}