package net.bomb7878.worldscanner.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.runtime.IJeiRuntime;
import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.integration.JEIWorldScannerModPlugin;
import net.bomb7878.worldscanner.screen.renderer.FluidTankRenderer;
import net.bomb7878.worldscanner.util.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(WorldScanner.MOD_ID, "textures/gui/alloy_furnace.png");
    private FluidTankRenderer renderer;

    public AlloyFurnaceScreen(AlloyFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(8000, true, 10, 55);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pPoseStack, x, y);
        renderer.render(pPoseStack, x + 15, y + 18, menu.getFluidStack());
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()){
            blit(pPoseStack, x + 81, y + 30, 176, 0, 14, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int titleColor = 0xFFD700;
        this.font.draw(pPoseStack,
                Component.translatable("block.worldscanner.alloy_furnace"),
                8, 6, titleColor);
        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);

        // Добавляем проверку для стрелки прогресса
        if(isMouseOverProgressArrow(pMouseX, pMouseY, x, y)) {
            renderTooltip(pPoseStack, Component.translatable("jei.worldscanner.recipes"), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseOverProgressArrow(int mouseX, int mouseY, int guiLeft, int guiTop) {
        return MouseUtil.isMouseOver(mouseX, mouseY,
                guiLeft + 81, guiTop + 30,
                14, menu.getScaledProgress() > 0 ? menu.getScaledProgress() : 14);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if(isMouseOverProgressArrow((int)mouseX, (int)mouseY, x, y)) {
            IJeiRuntime jeiRuntime = JEIWorldScannerModPlugin.getJeiRuntime();
            if (jeiRuntime != null) {
                jeiRuntime.getRecipesGui().showTypes(List.of(JEIWorldScannerModPlugin.ALLOYING_TYPE));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderFluidAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 15, 18)) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
