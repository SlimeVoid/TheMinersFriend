package com.slimevoid.compatibility.thaumcraft;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import com.slimevoid.compatibility.MasterCompatibility;
import com.slimevoid.compatibility.thaumcraft.client.ThaumcraftKeyBindingHandler;
import com.slimevoid.compatibility.thaumcraft.client.WandGuiTickHandler;

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
        KeyBinding[] key = new KeyBinding[] { new KeyBinding("Change ToolBelt Wand Focus", 33) };
        boolean[] repeat = new boolean[] { false };
        KeyBindingRegistry.registerKeyBinding(new ThaumcraftKeyBindingHandler(key, repeat));
    }

    @Override
    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
        TickRegistry.registerTickHandler(new WandGuiTickHandler(),
                                         Side.CLIENT);
    }

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
    }
}
