package net.slimevoid.compatibility.mystcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.slimevoid.compatibility.MasterCompatibility;

public class Mystcraft extends MasterCompatibility {

    public static final String MOD_ID = "Mystcraft";

    public Mystcraft() {
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
