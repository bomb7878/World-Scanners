package net.bomb7878.worldscanner.blocks.entity;

import net.bomb7878.worldscanner.blocks.custom.AlloyFurnaceBlock;
import net.bomb7878.worldscanner.networking.ModMessages;
import net.bomb7878.worldscanner.networking.packet.FluidSyncS2CPacket;
import net.bomb7878.worldscanner.recipe.AlloyFurnaceRecipe;
import net.bomb7878.worldscanner.recipe.util.IngredientWithCount;
import net.bomb7878.worldscanner.screen.AlloyFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.bomb7878.worldscanner.blocks.custom.AlloyFurnaceBlock.ACTIVE;

public class AlloyFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    protected final int SLOT_COUNT = 5;
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot == 1 || slot == 2) {
                onSlotContentsChanged();
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1 -> {
                    // Проверяем, что в слоте 2 нет такого же предмета
                    ItemStack slot2Stack = getStackInSlot(2);
                    yield slot2Stack.isEmpty() || !ItemStack.isSameItemSameTags(slot2Stack, stack);
                }
                case 2 -> {
                    // Проверяем, что в слоте 1 нет такого же предмета
                    ItemStack slot1Stack = getStackInSlot(1);
                    yield slot1Stack.isEmpty() || !ItemStack.isSameItemSameTags(slot1Stack, stack);
                }
                case 3 -> false;
                case 4 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }

        private void onSlotContentsChanged() {
            // Принудительно обновляем валидность обоих слотов
            for (int slot : new int[]{1, 2}) {
                ItemStack stack = getStackInSlot(slot);
                if (!stack.isEmpty() && !isItemValid(slot, stack)) {
                    // Если предмет стал невалидным, извлекаем его
                    ItemStack extracted = extractItem(slot, stack.getCount(), false);
                    // Пытаемся вернуть предмет обратно в инвентарь игрока или выбросить
                    if (level != null && !level.isClientSide) {
                        Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), extracted);
                    }
                }
            }
        }
    };

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    private boolean isActive;

    public void setActive(boolean active) {
        if (this.isActive != active) { // Только при изменении состояния
            this.isActive = active;
            if (level != null && !level.isClientSide) {
                level.setBlock(worldPosition,
                        getBlockState().setValue(ACTIVE, active), 3);
            }
        }
    }

    private final FluidTank FLUID_TANK = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 0,
                    (index, stack) -> index == 0)),

            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 3 || index == 4,
                    (index, stack) -> false)),

            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 1 || index == 2,
                    (index, stack) -> (index == 1 || index == 2) && itemHandler.isItemValid(index, stack))),

            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 1 || index == 2,
                    (index, stack) -> (index == 1 || index == 2) && itemHandler.isItemValid(index, stack))),

            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 1 || index == 2,
                    (index, stack) -> (index == 1 || index == 2) && itemHandler.isItemValid(index, stack))),

            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (index) -> index == 1 || index == 2,
                    (index, stack) -> (index == 1 || index == 2) && itemHandler.isItemValid(index, stack)))
    );

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public AlloyFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_FURNACE.get(), pPos, pBlockState);
        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress;
                    case 1 -> AlloyFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress = value;
                    case 1 -> AlloyFurnaceBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.worldscanner.alloy_furnace");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new AlloyFurnaceMenu(id, inventory, this, this.data);
    }

    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast(); // Возвращаем полный доступ без стороны
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction facing = this.getBlockState().getValue(AlloyFurnaceBlock.FACING);

                // Особые случаи для верха и низа
                if (side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                // Корректировка сторон относительно направления блока
                return switch (facing) {
                    case NORTH -> directionWrappedHandlerMap.get(side).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    default -> directionWrappedHandlerMap.get(side).cast();
                };
            }
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER){
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("alloy_furnace_progress", progress);

        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("alloy_furnace_progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AlloyFurnaceBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if(hasFluidItemInSourceSlot(pEntity)){
            transferItemFluidToFluidTank(pEntity);
        }

        boolean isActiveBefore = pEntity.isActive;
        boolean hasRecipe = hasRecipe(pEntity);

        if (hasRecipe) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
                // После завершения крафта сразу проверяем, есть ли следующий
                hasRecipe = hasRecipe(pEntity);
            }

            // Устанавливаем активность только если изменилось состояние
            pEntity.setActive(true);
        } else {
            // Сбрасываем только если нет рецепта
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        // Обновляем блок только если состояние действительно изменилось
        if(isActiveBefore != pEntity.isActive) {
            level.setBlock(pos, state.setValue(AlloyFurnaceBlock.ACTIVE, pEntity.isActive), 3);
        }


    }

    private static void transferItemFluidToFluidTank(AlloyFurnaceBlockEntity pEntity) {
        ItemStack containerStack = pEntity.itemHandler.getStackInSlot(0);
        containerStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());

                // Проверяем, стала ли ёмкость пустой после слива
                ItemStack newContainer = handler.getContainer();
                if (newContainer.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fluidHandler -> {
                    FluidStack fluidInContainer = fluidHandler.getFluidInTank(0);
                    return fluidInContainer.isEmpty();
                }).orElse(false)) {
                    // Если ёмкость пуста, перемещаем её в выходной слот
                    moveEmptyContainerToOutput(pEntity, newContainer);
                }
            }
        });
    }

    private static void moveEmptyContainerToOutput(AlloyFurnaceBlockEntity pEntity, ItemStack emptyContainer) {
        // Извлекаем пустую ёмкость из входного слота
        pEntity.itemHandler.extractItem(0, 1, false);

        // Пытаемся добавить в выходной слот (слот 4)
        if (pEntity.itemHandler.getStackInSlot(4).isEmpty()) {
            pEntity.itemHandler.setStackInSlot(4, emptyContainer);
        } else if (pEntity.itemHandler.getStackInSlot(4).getItem() == emptyContainer.getItem() &&
                pEntity.itemHandler.getStackInSlot(4).getCount() < pEntity.itemHandler.getStackInSlot(4).getMaxStackSize()) {
            pEntity.itemHandler.getStackInSlot(4).grow(1);
        } else {
            // Если выходной слот занят, возвращаем ёмкость обратно во входной слот
            pEntity.itemHandler.insertItem(0, emptyContainer, false);
        }
    }

    private static void fillTankWithFluid(AlloyFurnaceBlockEntity pEntity, FluidStack stack, @NotNull ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
    }

    private static boolean hasFluidItemInSourceSlot(AlloyFurnaceBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AlloyFurnaceBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<AlloyFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloyFurnaceRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(pEntity)) {
            pEntity.FLUID_TANK.drain(recipe.get().getFluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);

            for (IngredientWithCount ing : recipe.get().getIngredientCounts()) {
                for (int slot : new int[]{1, 2}) {
                    ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(slot);
                    if (ing.ingredient.test(stackInSlot)) {
                        pEntity.itemHandler.extractItem(slot, ing.count, false);
                        break;
                    }
                }
            }

            ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            if (pEntity.itemHandler.getStackInSlot(3).isEmpty()) {
                pEntity.itemHandler.setStackInSlot(3, result);
            } else {
                pEntity.itemHandler.getStackInSlot(3).grow(result.getCount());
            }

            pEntity.resetProgress();
        } else {
            pEntity.setActive(false); // Деактивация если рецепт внезапно стал невалидным
        }
    }

    private static boolean hasRecipe(AlloyFurnaceBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlloyFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloyFurnaceRecipe.Type.INSTANCE, inventory, level);

        if (recipe.isEmpty()) {
            entity.maxProgress = 200; // Возвращаем дефолтное значение если рецепта нет
            return false;
        }

        // Всегда обновляем maxProgress при наличии рецепта
        entity.maxProgress = recipe.get().getProcessingTime();

        List<IngredientWithCount> remainingIngredients = new ArrayList<>(recipe.get().getIngredientCounts());
        for (int slot : new int[]{1, 2}) {
            ItemStack stackInSlot = inventory.getItem(slot);
            if (stackInSlot.isEmpty()) continue;

            Iterator<IngredientWithCount> iterator = remainingIngredients.iterator();
            while (iterator.hasNext()) {
                IngredientWithCount ing = iterator.next();
                if (ing.ingredient.test(stackInSlot)){
                    if (stackInSlot.getCount() >= ing.count) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        return  canInsertAmountIntoOutputSlot(inventory, recipe.get().getResultItem(RegistryAccess.EMPTY))
                && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(RegistryAccess.EMPTY))
                && remainingIngredients.isEmpty()
                && hasCorrectFluidInTank(entity, recipe)
                && hasCorrectFluidAmountInTank(entity, recipe);
    }

    private static boolean hasCorrectFluidAmountInTank(AlloyFurnaceBlockEntity entity, Optional<AlloyFurnaceRecipe> recipe) {
        return entity.FLUID_TANK.getFluidAmount() >= recipe.get().getFluid().getAmount();
    }

    private static boolean hasCorrectFluidInTank(AlloyFurnaceBlockEntity entity, Optional<AlloyFurnaceRecipe> recipe) {
        return recipe.get().getFluid().equals(entity.FLUID_TANK.getFluid());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack resultItem) {
        return inventory.getItem(3).getItem() == resultItem.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, ItemStack result) {
        if (inventory.getItem(3).isEmpty()) {
            return true;
        }
        return inventory.getItem(3).getCount() + result.getCount() <= inventory.getItem(3).getMaxStackSize();
    }
}
