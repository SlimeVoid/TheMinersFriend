package net.slimevoid.compatibility;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.tmf.core.lib.CoreLib;

public class TMFCompatibility {

    public static void registerBlockAndItemInformation() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Registered Block and Item information for : "
                                + mod.getCompat().name);
                mod.getCompat().registerBlockAndItemInformation();
            }
        }
    }

    public static void registerKeyBindings() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Registered Key Bindings for : "
                                + mod.getCompat().name);
                mod.getCompat().registerKeyBindings();
            }
        }
    }

    public static void registerPacketExecutors() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Registered Packet Executors for : "
                                + mod.getCompat().name);
                mod.getCompat().registerPacketExecutors();
            }
        }
    }

    public static void registerTickHandlers() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Registered Tick Handlers for : "
                                + mod.getCompat().name);
                mod.getCompat().registerTickHandlers();
            }
        }
    }

    public static void registerEventHandlers() {
        for (Mods mod : Mods.values()) {
            if (mod.getCompat().isLoaded) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Registered Event Handlers for : "
                                + mod.getCompat().name);
                mod.getCompat().registerEventHandlers();
            }
        }
    }
}
