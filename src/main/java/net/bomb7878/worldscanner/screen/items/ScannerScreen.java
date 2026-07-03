package net.bomb7878.worldscanner.screen.items;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.bomb7878.worldscanner.items.custom.scanners.AbstractScannerItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class ScannerScreen extends AbstractContainerScreen<ScannerMenu> {
    private float scrollOffset;
    private boolean isScrolling;
    private final int scrollbarWidth = 6;
    private final int contentHeight = 120; // Высота области с контентом
    private final int visibleLines = 8; // Количество видимых строк
    private static final int TEXT_WIDTH = 250;

    private boolean scanPerformed = false;

    public ScannerScreen(ScannerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 276;
        this.imageHeight = 240;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, menu.getScannerType().getTexture());

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        int titleWidth = font.width(title);
        font.draw(poseStack, title,
                (imageWidth - titleWidth) / 2.0f,
                5f,
                0xFFD700);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);

        // Отрисовка результатов с учетом прокрутки
        renderScrollingResults(poseStack, mouseX, mouseY);

        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    private void renderScrollingResults(PoseStack poseStack, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (menu.getScanResults() == null) return;


        // Настройка области отсечения
        enableScissor(leftPos + 8, topPos + 20, leftPos + imageWidth - 8, topPos + imageHeight - 30);

        // Отрисовка контента с учетом прокрутки
        poseStack.pushPose();
        poseStack.translate(0, -scrollOffset, 0);
        int yPos = topPos + 20;
        for (Map.Entry<String, Integer> entry : menu.getScanResults().getGroupedResults().entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            String modName = menu.getScanResults().getModName(name);

            String modInfo = modName.equals("minecraft")
                    ? Component.translatable("chat.messages.worldscanner.scanResult.modFormatting.vanilla").getString()
                    : Component.translatable("chat.messages.worldscanner.scanResult.modFormatting", modName).getString();

            String heightInfo = menu.getScanResults().getHeightRange(name);

            if (font.width(name) + font.width(modInfo) + font.width(heightInfo) > TEXT_WIDTH - 120 ) {
                // Первая строка: название
                font.draw(poseStack,
                        Component.literal("§7" + name + (modInfo.isEmpty()? ":" : "")),
                        x + 15 + 32, yPos, 0xFFFFFF);

                // Вторая строка: мод и количество
                String infoLine;

                if(modInfo.isEmpty()){
                    infoLine = String.format("§ax%d %s",
                            count,
                            heightInfo);
                }else{
                    infoLine = String.format("%s: §ax%d %s",
                            modInfo,
                            count,
                            heightInfo);
                }

                font.draw(poseStack,
                        Component.literal("§7" + infoLine),
                        x + 15 + 32, yPos + 10, 0xFFFFFF);

                yPos += 20;
            } else {

                String line = String.format("§7%s%s: §ax%d %s", name, modInfo, count, heightInfo);

                font.draw(poseStack,
                        Component.literal(line),
                        x + 15 + 32, yPos, 0xFFFFFF);
                yPos += 10;
            }
        }

        poseStack.popPose();
        disableScissor();

        // Отрисовка полосы прокрутки
        renderScrollbar(poseStack);
    }


    private void renderScrollbar(PoseStack poseStack) {
        if (menu.getScanResults() == null) return;

        int totalContentHeight = calculateTotalContentHeight();
        if (totalContentHeight <= contentHeight) return;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        float scrollRatio = scrollOffset / (totalContentHeight - contentHeight);
        int scrollbarHeight = (int) (contentHeight * (contentHeight / (float) totalContentHeight));
        int scrollbarY = y + 22 + (int) ((contentHeight - scrollbarHeight) * scrollRatio);

        // Современный скроллбар (более тонкий и прозрачный)
        fill(poseStack,
                x + imageWidth - 16, scrollbarY,
                x + imageWidth - 12, scrollbarY + scrollbarHeight,
                0x80A0A0A0);
    }

    private int calculateTotalContentHeight() {
        if (menu.getScanResults() == null) return 0;

        int height = 0;
        for (String name : menu.getScanResults().getGroupedResults().keySet()) {
            height += (font.width(name) > TEXT_WIDTH - 50) ? 20 : 12;
        }
        return height;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (menu.getScanResults() == null) return false;

        int totalContentHeight = menu.getScanResults().getGroupedResults().size() * 12;
        float maxOffset = Math.max(0, totalContentHeight - contentHeight);

        scrollOffset = (float) Mth.clamp(scrollOffset - delta * 10, 0, maxOffset);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOverScrollbar(mouseX, mouseY)) {
            isScrolling = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) isScrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isScrolling) {
            int totalContentHeight = menu.getScanResults().getGroupedResults().size() * 10;
            float scrollableHeight = contentHeight - getScrollbarHeight(totalContentHeight);

            float scrollDelta = (float) dragY / scrollableHeight;
            scrollOffset = Mth.clamp(scrollOffset + scrollDelta * totalContentHeight,
                    0, totalContentHeight - contentHeight);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private boolean isMouseOverScrollbar(double mouseX, double mouseY) {
        return mouseX >= leftPos + imageWidth - 12 &&
                mouseX <= leftPos + imageWidth - 6 &&
                mouseY >= topPos + 20 &&
                mouseY <= topPos + 20 + contentHeight;
    }

    private int getScrollbarHeight(int totalContentHeight) {
        return (int) (contentHeight * (contentHeight / (float) totalContentHeight));
    }

    public static void enableScissor(int x1, int y1, int x2, int y2) {
        RenderSystem.enableScissor(
                (int) (x1 * Minecraft.getInstance().getWindow().getGuiScale()),
                (int) (Minecraft.getInstance().getWindow().getHeight() - y2 * Minecraft.getInstance().getWindow().getGuiScale()),
                (int) ((x2 - x1) * Minecraft.getInstance().getWindow().getGuiScale()),
                (int) ((y2 - y1) * Minecraft.getInstance().getWindow().getGuiScale())
        );
    }

    public static void disableScissor() {
        RenderSystem.disableScissor();
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.addRenderableWidget(Button.builder(
                                Component.translatable("gui.worldscanner.scan_button").withStyle(ChatFormatting.GOLD),
                                button -> {
                                    Player player = Minecraft.getInstance().player;
                                    if (player != null && menu != null) {
                                        ItemStack scanner = menu.getScanner();
                                        if (scanner.getItem() instanceof AbstractScannerItem scannerItem) {
                                            AbstractScannerItem.ScanResult result = scannerItem.performScan(player.level, player);

                                            menu.setScanResults(result);
                                            scrollOffset = 0;
                                            scanPerformed = true;

                                            if (result.isEmpty()) {
                                                player.playSound(SoundEvents.NOTE_BLOCK_BASS.get(), 0.5f, 0.5f);
                                                player.sendSystemMessage(Component.translatable("chat.messages.worldscanner.scanResult.failure"));
                                                player.closeContainer();
                                            } else {
                                                player.playSound(SoundEvents.NOTE_BLOCK_PLING.get(), 0.7f, 1.2f);
                                            }
                                        }
                                    }
                                }
                        )
                        .bounds(x + (imageWidth / 2) - 50, y + imageHeight - 25 - 3, 100, 20)
                        .build()
        );
    }

    @Override
    public void onClose() {
        scanPerformed = false;
        super.onClose();
    }
}
