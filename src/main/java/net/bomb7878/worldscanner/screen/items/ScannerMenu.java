package net.bomb7878.worldscanner.screen.items;

import net.bomb7878.worldscanner.items.custom.scanners.AbstractScannerItem;
import net.bomb7878.worldscanner.screen.ModMenuTypes;
import net.bomb7878.worldscanner.util.ScannerType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ScannerMenu extends AbstractContainerMenu {
    private final ItemStack scanner;
    private final ScannerType scannerType;
    private AbstractScannerItem.ScanResult scanResults;

    public ScannerMenu(int containerId, Inventory playerInventory, ItemStack scanner) {
        super(ModMenuTypes.SCANNER_MENU.get(), containerId);
        this.scanner = scanner;
        this.scannerType = ScannerType.getFromItem(scanner.getItem());
    }

    public ScannerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, extraData.readItem());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem() == scanner || player.getOffhandItem() == scanner;
    }

    public ScannerType getScannerType() {
        return scannerType;
    }

    public void setScanResults(AbstractScannerItem.ScanResult results) {
        this.scanResults = results;
    }

    public AbstractScannerItem.ScanResult getScanResults() {
        return scanResults;
    }

    public ItemStack getScanner() {
        return scanner;
    }
}
