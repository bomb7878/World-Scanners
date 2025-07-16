package net.bomb7878.worldscanner.screen;

import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.blocks.entity.AlloyFurnaceBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;

public class AlloyFurnaceMenu extends AbstractContainerMenu {
    public final AlloyFurnaceBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;

    public AlloyFurnaceMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, inventory.player.level.getBlockEntity(buf.readBlockPos()), new SimpleContainerData(2));
    }
    public AlloyFurnaceMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ALLOY_FURNACE_MENU.get(), id);
        checkContainerSize(inventory, 5);
        checkContainerDataCount(data, 2);
        this.blockEntity = (AlloyFurnaceBlockEntity) entity;
        this.level = inventory.player.level;
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventoryAndHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 37, 15));// под ведро с лавой
            this.addSlot(new SlotItemHandler(handler, 4, 37, 60));// выход пустого ведра
            this.addSlot(new SlotItemHandler(handler, 1, 63, 24));// вход 1
            this.addSlot(new SlotItemHandler(handler, 2, 97, 24));// вход 2
            this.addSlot(new SlotItemHandler(handler, 3, 80, 51));// результат крафта

        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int progressRectangleAndArrowSize = 15 + 4; // ИЗМЕНИТЬ ПОД ВЫСОТУ В ПИКСЕЛЯХ СВОЕЙ СТРЕЛКИ ПРОГРЕССА!!!

        return maxProgress != 0 && progress != 0 ? progress * progressRectangleAndArrowSize / maxProgress : 0;
    }
     /**
      must assign a slot number to each of the slots used by the GUI.Add commentMore actions
      For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
      Each time we add a Slot to the container, it automatically increases the slotIndex, which means
      0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
      9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
      36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
     */
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ALLOY_FURNACE.get());
    }

    private void addPlayerInventoryAndHotbar(Inventory pPlayerInventory) {
        // Слоты основного инвентаря (3 ряда по 9 слотов)
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 9; column++) {
                // Формула расчета:
                // X: 8 + column * 18 (шаг 18 пикселей между слотами по горизонтали)
                // Y: 86 + row * 18 (шаг 18 пикселей между рядами по вертикали)
                this.addSlot(new Slot(
                        pPlayerInventory,
                        column + row * 9 + 9, // Индекс слота (9-35)
                        8 + column * 18,      // X-координата
                        86 + row * 18         // Y-координата
                ));
            }
        }

        // Слоты хотбара (1 ряд 9 слотов)
        for(int slot = 0; slot < 9; slot++) {
            // X: 8 + slot * 18 (шаг 18 пикселей между слотами)
            // Y: 144 (фиксированная позиция ниже инвентаря)
            this.addSlot(new Slot(
                    pPlayerInventory,
                    slot,            // Индекс слота (0-8)
                    8 + slot * 18,   // X-координата
                    144              // Y-координата
            ));
        }
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public AlloyFurnaceBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public FluidStack getFluidStack() {
        return this.fluidStack;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }
}
