package net.slimevoid.tmf.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModCompatibility {

    public void registerBlockAndItemInformation();

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings();

    public void registerPacketExecutors();

    public void registerTickHandlers();

    public void registerEventHandlers();
}
