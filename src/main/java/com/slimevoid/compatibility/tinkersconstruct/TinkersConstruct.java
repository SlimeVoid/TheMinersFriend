package com.slimevoid.compatibility.tinkersconstruct;

import com.slimevoid.compatibility.MasterCompatibility;

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

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings() {
    }

    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
    }

    @Override
    public void registerEventHandlers() {
    }
}
