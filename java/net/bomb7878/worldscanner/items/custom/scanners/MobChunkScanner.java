package net.bomb7878.worldscanner.items.custom.scanners;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class MobChunkScanner extends AbstractScannerItem {
    @Override
    protected ScanResult performScan(Level level, Player player) {
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
}