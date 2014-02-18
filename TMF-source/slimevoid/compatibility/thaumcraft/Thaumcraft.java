package slimevoid.compatibility.thaumcraft;

import net.minecraft.client.settings.KeyBinding;
import slimevoid.compatibility.MasterCompatibility;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.network.CommonPacketHandler;
import slimevoidlib.network.handlers.SubPacketHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Thaumcraft extends MasterCompatibility {

    public static final String MOD_ID = "Thaumcraft";

    public Thaumcraft() {
        super(MOD_ID);
    }

    @Override
    public void registerBlockAndItemInformation() {
    }

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings() {
        KeyBinding[] key = new KeyBinding[] { new KeyBinding("Change ToolBelt Wand Focus", 33) };
        boolean[] repeat = new boolean[] { false };
        KeyBindingRegistry.registerKeyBinding(new ThaumcraftKeyBindingHandler(key, repeat));
    }

    public void registerPacketExecutors() {
        SubPacketHandler handler = CommonPacketHandler.getPacketHandler(PacketLib.MOD_COMPAT);
        handler.registerPacketHandler(ThaumcraftStatic.COMMAND_CHANGE_FOCUS,
                                      new PacketChangeWandFocusExecutor());
    }

    @Override
    public void registerTickHandlers() {
        TickRegistry.registerTickHandler(new WandGuiTickHandler(),
                                         Side.CLIENT);
    }
}
