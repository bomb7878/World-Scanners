package net.bomb7878.worldscanner.fluid;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.items.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, WorldScanner.MOD_ID);

    public static final RegistryObject<FlowingFluid> MOLTEN_SOULS = FLUIDS.register("molten_souls_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_SOULS_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_SOULS = FLUIDS.register("flowing_molten_souls",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_SOULS_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties MOLTEN_SOULS_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SOULS_FLUID_TYPE, MOLTEN_SOULS, FLOWING_MOLTEN_SOULS)
            .slopeFindDistance(1)
            .levelDecreasePerBlock(2)
            .block(ModBlocks.MOLTEN_SOULS_BLOCK)
            .bucket(ModItems.MOLTEN_SOULS_BUCKET);
}
