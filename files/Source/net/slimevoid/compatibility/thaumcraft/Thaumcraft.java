package net.slimevoid.compatibility.thaumcraft;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.slimevoid.compatibility.MasterCompatibility;
import net.slimevoid.compatibility.thaumcraft.client.WandGuiTickHandler;

public class Thaumcraft extends MasterCompatibility {

    public static final String MOD_ID = "Thaumcraft";
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
        KeyBinding key = new KeyBinding("Change ToolBelt Wand Focus", 33, "key.categories.misc");
        boolean[] repeat = new boolean[]{false};
        ClientRegistry.registerKeyBinding(key);
    }

    @Override
    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
        MinecraftForge.EVENT_BUS.register(new WandGuiTickHandler());
    }

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
    }
}
