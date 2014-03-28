package net.slimevoid.compatibility;

public class TMFCompatibility {

    public static void registerBlockAndItemInformation() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                mod.getCompat().registerBlockAndItemInformation();
            }
        }
    }

    public static void registerKeyBindings() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                mod.getCompat().registerKeyBindings();
            }
        }
    }

    public static void registerPacketExecutors() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                mod.getCompat().registerPacketExecutors();
            }
        }
    }

    public static void registerTickHandlers() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                mod.getCompat().registerTickHandlers();
            }
        }
    }

    public static void registerEventHandlers() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                mod.getCompat().registerEventHandlers();
            }
        }
    }
}
