package slimevoid.compatibility;

import slimevoid.compatibility.mystcraft.Mystcraft;
import slimevoid.compatibility.thaumcraft.Thaumcraft;
import slimevoid.compatibility.tinkersconstruct.TinkersConstruct;

public enum Mods {

    THAUMCRAFT(new Thaumcraft()),
    TINKERS(new TinkersConstruct()),
    MYSTCRAFT(new Mystcraft());

    private MasterCompatibility compat;

    Mods(MasterCompatibility compatibilityClass) {
        this.setCompat(compatibilityClass);
    }

    public MasterCompatibility getCompat() {
        return compat;
    }

    public void setCompat(MasterCompatibility compat) {
        this.compat = compat;
    }

}
