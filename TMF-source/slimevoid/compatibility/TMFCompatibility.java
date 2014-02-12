package slimevoid.compatibility;

public class TMFCompatibility {

    public static void registerKeyBindings() {
        for (Mods mod : Mods.values()) {
            if (mod.compat.isLoaded) {
                mod.compat.registerKeyBindings();
            }
        }
    }

    public static void registerPacketExecutors() {
        for (Mods mod : Mods.values()) {
            if (mod.compat.isLoaded) {
                mod.compat.registerPacketExecutors();
            }
        }
    }

    public static void registerTickHandlers() {
        for (Mods mod : Mods.values()) {
            if (mod.compat.isLoaded) {
                mod.compat.registerTickHandlers();
            }
        }
    }
}
