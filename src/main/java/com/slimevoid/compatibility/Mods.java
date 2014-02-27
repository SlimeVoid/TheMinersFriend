package com.slimevoid.compatibility;

import com.slimevoid.compatibility.mystcraft.Mystcraft;
import com.slimevoid.compatibility.thaumcraft.Thaumcraft;
import com.slimevoid.compatibility.tinkersconstruct.TinkersConstruct;

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
