package net.bomb7878.worldscanner.items.custom.scanners;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractScannerItem extends Item {
    public AbstractScannerItem() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .setNoRepair()
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            ScanResult result = performScan(level, player);
            sendChatResults(player, result);
        }

        return InteractionResultHolder.success(stack);
    }

    protected abstract ScanResult performScan(Level level, Player player);

    protected void sendChatResults(Player player, ScanResult result) {
        if (result.isEmpty()) {
            player.sendSystemMessage(Component.translatable("chat.messages.worldscanner.scanResult.failure"));
            return;
        }

        player.sendSystemMessage(Component.translatable("chat.messages.worldscanner.scanResult.success"));
        result.getGroupedResults().forEach((name, count) -> {
            String modName = result.getModName(name);
            String modInfo;
            if (modName.equals("minecraft")) {
                modInfo = Component.translatable("chat.messages.worldscanner.scanResult.modFormatting.vanilla").getString();
            } else {
                modInfo = Component.translatable(
                        "chat.messages.worldscanner.scanResult.modFormatting",
                        modName
                ).getString();
            }
            String heightInfo = result.getHeightRange(name);
            player.sendSystemMessage(Component.translatable(
                    String.format("§7- %s%s: §ax%d %s", name, modInfo, count, heightInfo)
            ));
        });
    }

    protected static class ScanResult {
        private final Map<String, Integer> counts = new HashMap<>();
        private final Map<String, String> modSources = new HashMap<>();
        private final Map<String, int[]> heightRanges = new HashMap<>();

        // Старый метод (для жидкостей и мобов)
        public void add(String name, ResourceLocation id) {
            counts.merge(name, 1, Integer::sum);
            modSources.put(name, id.getNamespace());
        }

        // Новый метод (для руд)
        public void add(String name, ResourceLocation id, int y) {
            counts.merge(name, 1, Integer::sum);
            modSources.put(name, id.getNamespace());

            int[] range = heightRanges.getOrDefault(name, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
            range[0] = Math.min(range[0], y);
            range[1] = Math.max(range[1], y);
            heightRanges.put(name, range);
        }

        public boolean isEmpty() {
            return counts.isEmpty();
        }

        public Map<String, Integer> getGroupedResults() {
            return counts;
        }

        public String getModName(String name) {
            return modSources.getOrDefault(name, "minecraft");
        }

        public String getHeightRange(String name) {
            int[] range = heightRanges.get(name);
            return range != null ? String.format(" §8[§9%d§8-§c%d§8]", range[1], range[0]) : "";
        }
    }
}