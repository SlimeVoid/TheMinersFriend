package net.slimevoid.compatibility;

import net.minecraftforge.fml.common.Loader;
import net.slimevoid.tmf.api.IModCompatibility;

public abstract class MasterCompatibility implements IModCompatibility {

    public String name;
    public boolean isLoaded;

    public MasterCompatibility(String name) {
        this.name = name;
        this.isLoaded = Loader.isModLoaded(name);
    }

}
