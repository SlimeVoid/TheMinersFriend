package slimevoid.compatibility;

import slimevoid.compatibility.thaumcraft.Thaumcraft;
import slimevoid.compatibility.tinkersconstruct.TinkersConstruct;

public enum Mods {

    THAUMCRAFT(new Thaumcraft()),
    TINKERS(new TinkersConstruct());

    MasterCompatibility compat;

    Mods(MasterCompatibility compatibilityClass) {
        this.compat = compatibilityClass;
    }

}
