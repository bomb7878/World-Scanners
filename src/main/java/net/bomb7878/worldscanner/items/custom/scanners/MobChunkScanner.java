package net.bomb7878.worldscanner.items.custom.scanners;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.util.ScannerType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobChunkScanner extends AbstractScannerItem {
    @Override
    public ScanResult performScan(Level level, Player player) {
        ScanResult result = new ScanResult();
        ChunkPos chunkPos = new ChunkPos(player.blockPosition());
        AABB scanArea = new AABB(
                chunkPos.getMinBlockX(), level.getMinBuildHeight(), chunkPos.getMinBlockZ(),
                chunkPos.getMaxBlockX(), level.getMaxBuildHeight(), chunkPos.getMaxBlockZ()
        );

        level.getEntitiesOfClass(LivingEntity.class, scanArea, e -> !(e instanceof Player))
                .forEach(entity -> {
                    ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
                    String mobName = entity.getType().getDescription().getString();
                    result.add(mobName, entityId);
                });

        return result;
    }

    public Component getDisplayName() {
        return ScannerType.MOB.getDisplayName();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.translatable(WorldScanner.MOD_ID + ".mob_scanner.shiftDown"));
        }else{
            components.add(Component.translatable(WorldScanner.MOD_ID + ".scanner.shift").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}