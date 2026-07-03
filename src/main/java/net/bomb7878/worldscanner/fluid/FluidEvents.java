package net.bomb7878.worldscanner.fluid;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.items.ModItems;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldScanner.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FluidEvents {
    @SubscribeEvent
    public static void onFuelBurn(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() == ModItems.MOLTEN_SOULS_BUCKET.get()) {
            event.setBurnTime(30000);
        }
    }
}
