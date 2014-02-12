package slimevoid.compatibility;

import slimevoid.tmf.api.IModCompatibility;
import cpw.mods.fml.common.Loader;

public abstract class MasterCompatibility implements IModCompatibility {

    public String  name;
    public boolean isLoaded;

    public MasterCompatibility(String name) {
        this.name = name;
        this.isLoaded = Loader.isModLoaded(name);
    }

}
