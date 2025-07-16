package net.bomb7878.worldscanner.datagen;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WorldScanner.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.MOD_ICON);
        simpleItem(ModItems.BRONZE_INGOT);
        simpleItem(ModItems.BRONZE_NUGGET);
        simpleItem(ModItems.DARK_DIAMOND);
        simpleItem(ModItems.DARK_STEEL_INGOT);
        simpleItem(ModItems.DARK_STEEL_NUGGET);
        simpleItem(ModItems.LIQUID_SCANNER);
        simpleItem(ModItems.MOB_SCANNER);
        simpleItem(ModItems.ORE_SCANNER);
        simpleItem(ModItems.RAW_TIN);
        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.TIN_NUGGET);
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.STEEL_NUGGET);
        simpleItem(ModItems.ENDER_STEEL_INGOT);
        simpleItem(ModItems.ENDER_STEEL_NUGGET);
        simpleItem(ModItems.ENDER_STEEL_UPGRADE_SMITHING_TEMPLATE);
        simpleItem(ModItems.SCANNERS_SCREEN);

        simpleItem(ModItems.NETHERITE_BLOOM);
        simpleItem(ModItems.ANCIENT_DARK_CRYSTAL);

        handheldItem(ModItems.TIN_SWORD);
        handheldItem(ModItems.TIN_PICKAXE);
        handheldItem(ModItems.TIN_AXE);
        handheldItem(ModItems.TIN_SHOVEL);
        handheldItem(ModItems.TIN_HOE);

        handheldItem(ModItems.BRONZE_SWORD);
        handheldItem(ModItems.BRONZE_PICKAXE);
        handheldItem(ModItems.BRONZE_AXE);
        handheldItem(ModItems.BRONZE_SHOVEL);
        handheldItem(ModItems.BRONZE_HOE);

        handheldItem(ModItems.STEEL_SWORD);
        handheldItem(ModItems.STEEL_PICKAXE);
        handheldItem(ModItems.STEEL_AXE);
        handheldItem(ModItems.STEEL_SHOVEL);
        handheldItem(ModItems.STEEL_HOE);

        handheldItem(ModItems.DARK_DIAMOND_SWORD);
        handheldItem(ModItems.DARK_DIAMOND_PICKAXE);
        handheldItem(ModItems.DARK_DIAMOND_AXE);
        handheldItem(ModItems.DARK_DIAMOND_SHOVEL);
        handheldItem(ModItems.DARK_DIAMOND_HOE);

        handheldItem(ModItems.DARK_STEEL_SWORD);
        handheldItem(ModItems.DARK_STEEL_PICKAXE);
        handheldItem(ModItems.DARK_STEEL_AXE);
        handheldItem(ModItems.DARK_STEEL_SHOVEL);
        handheldItem(ModItems.DARK_STEEL_HOE);

        handheldItem(ModItems.ENDER_STEEL_SWORD);
        handheldItem(ModItems.ENDER_STEEL_PICKAXE);
        handheldItem(ModItems.ENDER_STEEL_AXE);
        handheldItem(ModItems.ENDER_STEEL_SHOVEL);
        handheldItem(ModItems.ENDER_STEEL_HOE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WorldScanner.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(WorldScanner.MOD_ID, "item/" + item.getId().getPath()));
    }
}
