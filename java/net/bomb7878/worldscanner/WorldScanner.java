package net.bomb7878.worldscanner;

import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.blocks.entity.ModBlockEntities;
import net.bomb7878.worldscanner.items.ModCreativeTabs;
import net.bomb7878.worldscanner.items.ModItems;
import net.bomb7878.worldscanner.networking.ModMessages;
import net.bomb7878.worldscanner.recipe.ModRecipes;
import net.bomb7878.worldscanner.screen.AlloyFurnaceScreen;
import net.bomb7878.worldscanner.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// ./gradlew build - для создания .jar

@Mod(WorldScanner.MOD_ID)
public class WorldScanner {

    public static final String MOD_ID = "worldscanner";

    public WorldScanner() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);

        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModMenuTypes.MENUS.register(bus);

        ModRecipes.SERIALIZERS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    private void addCreative(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeTabs.tab) {
            event.accept(ModItems.MOD_ICON);
            event.accept(ModItems.LIQUID_SCANNER);
            event.accept(ModItems.MOB_SCANNER);
            event.accept(ModItems.ORE_SCANNER);
            event.accept(ModItems.SCANNERS_SCREEN);

            event.accept(ModItems.DARK_DIAMOND_ORE);
            event.accept(ModItems.DARK_DIAMOND);
            event.accept(ModItems.DARK_DIAMOND_SWORD);
            event.accept(ModItems.DARK_DIAMOND_PICKAXE);
            event.accept(ModItems.DARK_DIAMOND_AXE);
            event.accept(ModItems.DARK_DIAMOND_SHOVEL);
            event.accept(ModItems.DARK_DIAMOND_HOE);
            event.accept(ModItems.BLOCK_OF_DARK_DIAMOND);

            event.accept(ModItems.NETHERITE_BLOOM);
            event.accept(ModItems.ANCIENT_DARK_CRYSTAL);

            event.accept(ModItems.TIN_ORE);
            event.accept(ModItems.RAW_TIN);
            event.accept(ModItems.BLOCK_OF_RAW_TIN);

            event.accept(ModItems.TIN_INGOT);
            event.accept(ModItems.TIN_NUGGET);
            event.accept(ModItems.TIN_SWORD);
            event.accept(ModItems.TIN_PICKAXE);
            event.accept(ModItems.TIN_AXE);
            event.accept(ModItems.TIN_SHOVEL);
            event.accept(ModItems.TIN_HOE);

            event.accept(ModItems.BLOCK_OF_TIN);

            event.accept(ModItems.ALLOY_FURNACE);

            event.accept(ModItems.BRONZE_INGOT);
            event.accept(ModItems.BRONZE_NUGGET);
            event.accept(ModItems.BRONZE_SWORD);
            event.accept(ModItems.BRONZE_PICKAXE);
            event.accept(ModItems.BRONZE_AXE);
            event.accept(ModItems.BRONZE_SHOVEL);
            event.accept(ModItems.BRONZE_HOE);
            event.accept(ModItems.BLOCK_OF_BRONZE);

            event.accept(ModItems.STEEL_INGOT);
            event.accept(ModItems.STEEL_NUGGET);
            event.accept(ModItems.STEEL_SWORD);
            event.accept(ModItems.STEEL_PICKAXE);
            event.accept(ModItems.STEEL_AXE);
            event.accept(ModItems.STEEL_SHOVEL);
            event.accept(ModItems.STEEL_HOE);
            event.accept(ModItems.BLOCK_OF_STEEL);

            event.accept(ModItems.DARK_STEEL_INGOT);
            event.accept(ModItems.DARK_STEEL_NUGGET);
            event.accept(ModItems.DARK_STEEL_SWORD);
            event.accept(ModItems.DARK_STEEL_PICKAXE);
            event.accept(ModItems.DARK_STEEL_AXE);
            event.accept(ModItems.DARK_STEEL_SHOVEL);
            event.accept(ModItems.DARK_STEEL_HOE);
            event.accept(ModItems.BLOCK_OF_DARK_STEEL);

            event.accept(ModItems.ENDER_STEEL_INGOT);
            event.accept(ModItems.ENDER_STEEL_NUGGET);
            event.accept(ModItems.ENDER_STEEL_SWORD);
            event.accept(ModItems.ENDER_STEEL_PICKAXE);
            event.accept(ModItems.ENDER_STEEL_AXE);
            event.accept(ModItems.ENDER_STEEL_SHOVEL);
            event.accept(ModItems.ENDER_STEEL_HOE);
            event.accept(ModItems.BLOCK_OF_ENDER_STEEL);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ALLOY_FURNACE_MENU.get(), AlloyFurnaceScreen::new);
        }
    }
}
