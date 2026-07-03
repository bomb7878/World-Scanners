package net.bomb7878.worldscanner.items.custom.scanners;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.util.ScannerType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OreChunkScanner extends AbstractScannerItem {
    // Тег для руд — в него должны быть добавлены как ванильные, так и модовые руды
    private static final TagKey<Block> ORES_TAG = BlockTags.create(new ResourceLocation("forge", "ores"));
    private static final int DEEPSLATE_LEVEL = 0;

    @Override
    public ScanResult performScan(Level level, Player player) {
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
                        String descriptionId = state.getBlock().getDescriptionId();
                        String oreName = getTranslatedName(descriptionId);
                        assert blockId != null;

                        // Определяем тип руды (глубинная или нет)
                        boolean isDeepslate = isDeepslateVariant(descriptionId) || isDeepLevelOre(y);
                        String fullOreName = oreName + (isDeepslate ? "_deepslate" : "");

                        result.add(fullOreName, blockId, y, isDeepslate);
                    }
                }
            }
        }
        return result;
    }

    private boolean isDeepslateVariant(String descriptionId) {
        return descriptionId.contains("deepslate");
    }

    private boolean isDeepLevelOre(int y) {
        return y <= DEEPSLATE_LEVEL;
    }

    private String getTranslatedName(String descriptionId) {
        if(descriptionId.contains("coal_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".coal_ore.replace").getString();
        }else if(descriptionId.contains("iron_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".iron_ore.replace").getString();
        }else if(descriptionId.contains("copper_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".copper_ore.replace").getString();
        }else if(descriptionId.contains("lapis_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".lapis_ore.replace").getString();
        }else if(descriptionId.contains("redstone_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".redstone_ore.replace").getString();
        }else if(descriptionId.contains("emerald_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".emerald_ore.replace").getString();
        }else if(descriptionId.equals("block.minecraft.diamond_ore") || descriptionId.equals("block.minecraft.deepslate_diamond_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".diamond_ore.replace").getString();
        }else if(descriptionId.equals("block.minecraft.gold_ore") || descriptionId.equals("block.minecraft.deepslate_gold_ore")){
            return Component.translatable(WorldScanner.MOD_ID + ".gold_ore.replace").getString();
        }
        return Component.translatable(descriptionId).getString();
    }

    public Component getDisplayName() {
        return ScannerType.ORE.getDisplayName();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.translatable(WorldScanner.MOD_ID + ".ore_scanner.shiftDown"));
        }else{
            components.add(Component.translatable(WorldScanner.MOD_ID + ".scanner.shift").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}