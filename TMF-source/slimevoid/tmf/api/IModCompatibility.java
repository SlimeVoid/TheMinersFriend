package slimevoid.tmf.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IModCompatibility {

    public void registerBlockAndItemInformation();

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings();

    public void registerPacketExecutors();

    public void registerTickHandlers();

    public void registerEventHandlers();
}
