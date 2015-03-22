package net.slimevoid.compatibility.thaumcraft;

import net.minecraftforge.common.MinecraftForge;
import net.slimevoid.compatibility.MasterCompatibility;
//import net.slimevoid.compatibility.thaumcraft.client.ThaumcraftKeyBindingHandler;
//import net.slimevoid.compatibility.thaumcraft.client.WandGuiTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Thaumcraft extends MasterCompatibility {

    public static final String MOD_ID      = "Thaumcraft";
    public static final String MOD_CHANNEL = "TC";

    public Thaumcraft() {
        super(MOD_ID);
    }

    @Override
    public void registerBlockAndItemInformation() {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerKeyBindings() {
        /*FMLCommonHandler.instance().bus().register(new ThaumcraftKeyBindingHandler());*/
    }

    @Override
    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
        /*FMLCommonHandler.instance().bus().register(new WandGuiTickHandler());*/
    }

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
    }
}
