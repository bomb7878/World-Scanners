package net.bomb7878.worldscanner.fluid;

import net.bomb7878.worldscanner.WorldScanner;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
    public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");
    public static final ResourceLocation MOLTEN_SOULS_RL = new ResourceLocation(WorldScanner.MOD_ID, "misc/in_molten_souls");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, WorldScanner.MOD_ID);

    public static final RegistryObject<FluidType> MOLTEN_SOULS_FLUID_TYPE = register("molten_souls_fluid",
            FluidType.Properties.create()
                    .lightLevel(10)
                    .density(5000)
                    .viscosity(7500)
                    .temperature(3000)
                    .rarity(Rarity.EPIC)
                    .canSwim(false)
                    .canHydrate(false)
                    .canPushEntity(false)
                    .canDrown(false)
                    .canExtinguish(false)
                    .supportsBoating(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.LAVA_EXTINGUISH)
                    .motionScale(0.0023333333333333335)
                    .pathType(BlockPathTypes.LAVA)
                    .adjacentPathType(null)
    );

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(
                WATER_STILL_RL,
                WATER_FLOWING_RL,
                MOLTEN_SOULS_RL,
                0xFF00BFFF,
                new Vector3f(100f / 255f, 149f / 255f, 237/255f),
                properties
        ));
    }
}
