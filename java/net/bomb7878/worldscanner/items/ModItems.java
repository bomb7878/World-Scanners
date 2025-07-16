package net.bomb7878.worldscanner.items;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.blocks.ModBlocks;
import net.bomb7878.worldscanner.items.custom.*;
import net.bomb7878.worldscanner.items.custom.scanners.LiquidChunkScanner;
import net.bomb7878.worldscanner.items.custom.scanners.MobChunkScanner;
import net.bomb7878.worldscanner.items.custom.scanners.OreChunkScanner;
import net.bomb7878.worldscanner.tools.ModItemTier;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WorldScanner.MOD_ID);

    public static final RegistryObject<Item> ORE_SCANNER = ITEMS.register(
            "ore_chunk_scanner", OreChunkScanner::new
    );

    public static final RegistryObject<Item> LIQUID_SCANNER = ITEMS.register(
            "liquid_chunk_scanner", LiquidChunkScanner::new
    );

    public static final RegistryObject<Item> MOB_SCANNER = ITEMS.register(
            "mob_chunk_scanner", MobChunkScanner::new
    );

    public static final RegistryObject<Item> SCANNERS_SCREEN = ITEMS.register("scanners_screen",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> MOD_ICON = ITEMS.register("mod_icon",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_ORE = ITEMS.register("dark_diamond_ore",
            () -> new BlockItem(ModBlocks.DARK_DIAMOND_ORE.get(), new Item.Properties()
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND = ITEMS.register("dark_diamond",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_SWORD = ITEMS.register("dark_diamond_sword",
            () -> new SwordItem(
                    ModItemTier.DARK_DIAMOND,
                    3,
                    -2.4f,
                    new Item.Properties()
                            .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_PICKAXE = ITEMS.register("dark_diamond_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.DARK_DIAMOND,
                    1,
                    -2.8f,
                    new Item.Properties()
                            .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_AXE = ITEMS.register("dark_diamond_axe",
            () -> new AxeItem(
                    ModItemTier.DARK_DIAMOND,
                    5.5f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_SHOVEL = ITEMS.register("dark_diamond_shovel",
            () -> new ShovelItem(
                    ModItemTier.DARK_DIAMOND,
                    1.5f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> DARK_DIAMOND_HOE = ITEMS.register("dark_diamond_hoe",
            () -> new HoeItem(
                    ModItemTier.DARK_DIAMOND,
                    -3,
                    0f,
                    new Item.Properties()
                            .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> BLOCK_OF_DARK_DIAMOND = ITEMS.register("block_of_dark_diamond",
            () -> new BlockItem(ModBlocks.BLOCK_OF_DARK_DIAMOND.get(), new Item.Properties()
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore",
            () -> new BlockItem(ModBlocks.TIN_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLOCK_OF_RAW_TIN = ITEMS.register("block_of_raw_tin",
            () -> new BlockItem(ModBlocks.BLOCK_OF_RAW_TIN.get(), new Item.Properties()));

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
            () -> new SwordItem(
                    ModItemTier.TIN,
                    3,
                    -2.4f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.TIN,
                    1,
                    -2.8f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe",
            () -> new AxeItem(
                    ModItemTier.TIN,
                    6.5f,
                    -3.15f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel",
            () -> new ShovelItem(
                    ModItemTier.TIN,
                    1.5f,
                    -3.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe",
            () -> new HoeItem(
                    ModItemTier.TIN,
                    -2,
                    -1.5f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BLOCK_OF_TIN = ITEMS.register("block_of_tin",
            () -> new BlockItem(ModBlocks.BLOCK_OF_TIN.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALLOY_FURNACE = ITEMS.register("alloy_furnace",
            () -> new BlockItem(ModBlocks.ALLOY_FURNACE.get(), new Item.Properties()
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(
                    ModItemTier.BRONZE,
                    3,
                    -2.4f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.BRONZE,
                    1,
                    -2.8f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(
                    ModItemTier.BRONZE,
                    5.5f,
                    -3.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(
                    ModItemTier.BRONZE,
                    1.5f,
                    -3.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(
                    ModItemTier.BRONZE,
                    -1,
                    -2.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BLOCK_OF_BRONZE = ITEMS.register("block_of_bronze",
            () -> new BlockItem(ModBlocks.BLOCK_OF_BRONZE.get(), new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(
                    ModItemTier.STEEL,
                    3,
                    -2.4f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.STEEL,
                    1,
                    -2.8f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(
                    ModItemTier.STEEL,
                    5.5f,
                    -3.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(
                    ModItemTier.STEEL,
                    1.5f,
                    -3.0f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(
                    ModItemTier.STEEL,
                    -3,
                    -0.5f,
                    new Item.Properties()
            ));

    public static final RegistryObject<Item> BLOCK_OF_STEEL = ITEMS.register("block_of_steel",
            () -> new BlockItem(ModBlocks.BLOCK_OF_STEEL.get(), new Item.Properties()
                    .rarity(Rarity.RARE)));

    public static final RegistryObject<Item> DARK_STEEL_INGOT = ITEMS.register("dark_steel_ingot",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_NUGGET = ITEMS.register("dark_steel_nugget",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_SWORD = ITEMS.register("dark_steel_sword",
            () -> new SwordItem(
                    ModItemTier.DARK_STEEL,
                    4,
                    -2.4f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_PICKAXE = ITEMS.register("dark_steel_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.DARK_STEEL,
                    1,
                    -2.8f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_AXE = ITEMS.register("dark_steel_axe",
            () -> new AxeItem(
                    ModItemTier.DARK_STEEL,
                    7f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_SHOVEL = ITEMS.register("dark_steel_shovel",
            () -> new ShovelItem(
                    ModItemTier.DARK_STEEL,
                    1.5f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> DARK_STEEL_HOE = ITEMS.register("dark_steel_hoe",
            () -> new HoeItem(
                    ModItemTier.DARK_STEEL,
                    -3,
                    0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> BLOCK_OF_DARK_STEEL = ITEMS.register("block_of_dark_steel",
            () -> new BlockItem(ModBlocks.BLOCK_OF_DARK_STEEL.get(), new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_INGOT = ITEMS.register("ender_steel_ingot",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_NUGGET = ITEMS.register("ender_steel_nugget",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_SWORD = ITEMS.register("ender_steel_sword",
            () -> new SwordItem(
                    ModItemTier.ENDER_STEEL,
                    5,
                    -2.4f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_PICKAXE = ITEMS.register("ender_steel_pickaxe",
            () -> new PickaxeItem(
                    ModItemTier.ENDER_STEEL,
                    1,
                    -2.8f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_AXE = ITEMS.register("ender_steel_axe",
            () -> new AxeItem(
                    ModItemTier.ENDER_STEEL,
                    7f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_SHOVEL = ITEMS.register("ender_steel_shovel",
            () -> new ShovelItem(
                    ModItemTier.ENDER_STEEL,
                    1f,
                    -3.0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_HOE = ITEMS.register("ender_steel_hoe",
            () -> new HoeItem(
                    ModItemTier.ENDER_STEEL,
                    -5,
                    0f,
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> BLOCK_OF_ENDER_STEEL = ITEMS.register("block_of_ender_steel",
            () -> new BlockItem(ModBlocks.BLOCK_OF_ENDER_STEEL.get(), new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> ENDER_STEEL_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("ender_steel_upgrade_smithing_template",
            () -> ModSmithingTemplateItem.createEnderSteelUpgradeTemplate());

    public static RegistryObject<Item> NETHERITE_BLOOM = ITEMS.register("netherite_bloom",
            () -> new NetheriteBloom(new Item.Properties()));

    public static RegistryObject<Item> ANCIENT_DARK_CRYSTAL = ITEMS.register("ancient_dark_crystal",
            () -> new AncientDarkCrystal(new Item.Properties()));
}
