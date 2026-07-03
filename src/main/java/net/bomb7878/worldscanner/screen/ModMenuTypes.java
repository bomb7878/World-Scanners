package net.bomb7878.worldscanner.screen;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.screen.blocks.AdvancedAlloyFurnaceMenu;
import net.bomb7878.worldscanner.screen.blocks.AlloyFurnaceMenu;
import net.bomb7878.worldscanner.screen.items.ScannerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, WorldScanner.MOD_ID);

    public static final RegistryObject<MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE_MENU =
            registerMenuType(AlloyFurnaceMenu::new, "alloy_furnace_menu");

    public static final RegistryObject<MenuType<AdvancedAlloyFurnaceMenu>> ADVANCED_ALLOY_FURNACE_MENU =
            registerMenuType(AdvancedAlloyFurnaceMenu::new, "advanced_alloy_furnace_menu");

    public static final RegistryObject<MenuType<ScannerMenu>> SCANNER_MENU =
            registerMenuType(ScannerMenu::new, "scanner_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
