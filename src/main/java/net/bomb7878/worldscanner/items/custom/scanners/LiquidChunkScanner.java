package net.bomb7878.worldscanner.items.custom.scanners;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.util.ScannerType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiquidChunkScanner extends AbstractScannerItem {
    @Override
    public ScanResult performScan(Level level, Player player) {
        ScanResult result = new ScanResult();
        ChunkPos chunkPos = new ChunkPos(player.blockPosition());
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
            for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                for (int y = level.getMinBuildHeight(); y <= level.getMaxBuildHeight(); y++) {
                    pos.set(x, y, z);
                    FluidState fluidState = level.getFluidState(pos);
                    if (!fluidState.isEmpty()) {
                        Fluid fluid = fluidState.getType();
                        ResourceLocation fluidId = ForgeRegistries.FLUIDS.getKey(fluid);
                        assert fluidId != null;
                        String fluidName = getFluidName(fluid.getFluidType().getDescriptionId());
                        result.add(fluidName, fluidId);
                    }
                }
            }
        }
        return result;
    }

    private String getFluidName(String descriptionFluidId) {
        return Component.translatable(descriptionFluidId).getString();
    }

    public Component getDisplayName() {
        return ScannerType.LIQUID.getDisplayName();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.translatable(WorldScanner.MOD_ID + ".liquid_scanner.shiftDown"));
        }else{
            components.add(Component.translatable(WorldScanner.MOD_ID + ".scanner.shift").withStyle(ChatFormatting.DARK_AQUA));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}