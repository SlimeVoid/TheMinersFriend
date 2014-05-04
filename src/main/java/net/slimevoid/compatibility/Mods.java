package net.slimevoid.compatibility;

//import net.slimevoid.compatibility.mystcraft.Mystcraft;
import net.slimevoid.compatibility.thaumcraft.Thaumcraft;
import net.slimevoid.compatibility.tinkersconstruct.TinkersConstruct;

public enum Mods {

    THAUMCRAFT(new Thaumcraft()),
    // MYSTCRAFT(new Mystcraft()),
    TINKERS(new TinkersConstruct());

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
