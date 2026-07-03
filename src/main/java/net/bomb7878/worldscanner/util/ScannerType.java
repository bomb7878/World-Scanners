package net.bomb7878.worldscanner.util;

import net.bomb7878.worldscanner.WorldScanner;
import net.bomb7878.worldscanner.items.custom.scanners.LiquidChunkScanner;
import net.bomb7878.worldscanner.items.custom.scanners.MobChunkScanner;
import net.bomb7878.worldscanner.items.custom.scanners.OreChunkScanner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public enum ScannerType {
    ORE("ore"),
    LIQUID("liquid"),
    MOB("mob");

    private final String name;

    ScannerType(String name) {
        this.name = name;
    }

    public static ScannerType getFromItem(Item item) {
        if (item instanceof OreChunkScanner) return ORE;
        if (item instanceof LiquidChunkScanner) return LIQUID;
        if (item instanceof MobChunkScanner) return MOB;
        return ORE; // default
    }

    public Component getDisplayName() {
        return Component.translatable("gui.worldscanner.scanner_type." + name);
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(WorldScanner.MOD_ID, "textures/gui/" + name + "_chunk_scanner" + ".png");
    }
}