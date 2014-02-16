package slimevoid.compatibility.tinkersconstruct;

import slimevoid.compatibility.MasterCompatibility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TinkersConstruct extends MasterCompatibility {

    public static final String MOD_ID = "TinkersConstruct";

    public TinkersConstruct() {
        super(MOD_ID);
    }

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings() {
    }

    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
    }
}
