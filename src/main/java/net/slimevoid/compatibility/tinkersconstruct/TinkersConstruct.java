package net.slimevoid.compatibility.tinkersconstruct;

import net.slimevoid.compatibility.MasterCompatibility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TinkersConstruct extends MasterCompatibility {

    public static final String MOD_ID = "TinkersConstruct";

    public TinkersConstruct() {
        super(MOD_ID);
    }

    @Override
    public void registerBlockAndItemInformation() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerKeyBindings() {
    }

    @Override
    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
    }

    @Override
    public void registerEventHandlers() {
    }
}
