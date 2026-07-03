package net.bomb7878.worldscanner.items.custom.scanners;

import net.bomb7878.worldscanner.screen.items.ScannerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractScannerItem extends Item implements MenuProvider {
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

        if (hand == InteractionHand.MAIN_HAND && !level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            ScanResult result = performScan(level, player);
            if(!player.isShiftKeyDown()) {
                NetworkHooks.openScreen(serverPlayer, this, buf -> {
                    buf.writeItemStack(stack, false);
                });
            }else {
                sendChatResults(player, result);
                player.getCooldowns().addCooldown(this, 20);
            }
        }
        return InteractionResultHolder.success(stack);
    }

    public abstract ScanResult performScan(Level level, Player player);

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ScannerMenu(containerId, playerInventory, player.getItemInHand(player.getUsedItemHand()));
    }

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

    public static class ScanResult {
        private final Map<String, Integer> counts = new HashMap<>();
        private final Map<String, String> modSources = new HashMap<>();
        private final Map<String, int[]> heightRanges = new HashMap<>();
        private final Map<String, int[]> deepslateHeightRanges = new HashMap<>();

        // метод для жидкостей и мобов
        public void add(String name, ResourceLocation id) {
            counts.merge(name, 1, Integer::sum);
            modSources.put(name, id.getNamespace());
        }

        // метод для руд
        public void add(String name, ResourceLocation id, int y, boolean isDeepslate) {
            String baseName = name.endsWith("_deepslate") ?
                    name.substring(0, name.length() - "_deepslate".length()) : name;

            counts.merge(baseName, 1, Integer::sum);
            modSources.put(baseName, id.getNamespace());

            if (isDeepslate) {
                int[] range = deepslateHeightRanges.getOrDefault(baseName, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
                range[0] = Math.min(range[0], y);
                range[1] = Math.max(range[1], y);
                deepslateHeightRanges.put(baseName, range);
            } else {
                int[] range = heightRanges.getOrDefault(baseName, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
                range[0] = Math.min(range[0], y);
                range[1] = Math.max(range[1], y);
                heightRanges.put(baseName, range);
            }
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
            int[] normalRange = heightRanges.get(name);
            int[] deepslateRange = deepslateHeightRanges.get(name);

            String normalRangeStr = normalRange != null ? String.format("§8[§9%d§8-§c%d§8]", normalRange[1], normalRange[0]) : "";
            String deepslateRangeStr = deepslateRange != null ? String.format("§8[§5%d§8-§4%d§8]", deepslateRange[1], deepslateRange[0]) : "";

            return normalRangeStr + (deepslateRange != null ? " " + deepslateRangeStr : "");
        }
    }
}