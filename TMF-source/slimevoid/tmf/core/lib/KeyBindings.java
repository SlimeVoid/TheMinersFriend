package slimevoid.tmf.core.lib;

import org.lwjgl.input.Keyboard;

import slimevoid.tmf.client.tickhandlers.input.ToolBeltKeyBindingHandler;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class KeyBindings {
	
	private static Minecraft mc;
	private static KeyBinding TOOL_BELT_KEY = new KeyBinding(NamingLib.MINING_TOOL_BELT, Keyboard.KEY_B);

	public static void registerKeyBindings() {
		mc = FMLClientHandler.instance().getClient();
		KeyBindingRegistry.registerKeyBinding(
				new ToolBeltKeyBindingHandler(
						new KeyBinding[]{TOOL_BELT_KEY}));
	}
	
	public static void doToolBeltKeyUp() {
		// TODO Key binding to cycle tools
		World world = mc.theWorld;
		EntityPlayer entityplayer = mc.thePlayer;
		if (world.isRemote) {
			ItemStack toolBelt = ItemLib.getToolBelt(entityplayer, world, true);
			if (toolBelt != null) {
				PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.CYCLE_TOOL_BELT);
				packet.setToolBeltId(toolBelt.getItemDamage());
				PacketDispatcher.sendPacketToServer(packet.getPacket());
			}
		}
	}
}
