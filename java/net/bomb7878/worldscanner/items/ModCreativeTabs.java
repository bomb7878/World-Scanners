package net.bomb7878.worldscanner.items;

import net.bomb7878.worldscanner.WorldScanner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldScanner.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {
    public static CreativeModeTab tab;

    @SubscribeEvent
    public static void regTab(CreativeModeTabEvent.Register e) {
        tab = e.registerCreativeModeTab(
                new ResourceLocation(WorldScanner.MOD_ID, "world_scanners"),
                builder -> builder
                        .icon(() -> new ItemStack(ModItems.MOD_ICON.get()))
                        .title(Component.translatable("itemGroup.worldscanner"))
        );
    }
}
