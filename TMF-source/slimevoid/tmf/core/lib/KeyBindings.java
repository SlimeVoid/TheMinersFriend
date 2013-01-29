/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.tmf.core.lib;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import slimevoid.tmf.client.tickhandlers.input.ToolBeltKeyBindingHandler;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class KeyBindings {
	
	private static Minecraft mc;
	private static KeyBinding TOOL_BELT_KEY = new KeyBinding(ItemLib.MINING_TOOLBELT, Keyboard.KEY_B);
	private static KeyBinding MINING_MODE_KEY = new KeyBinding(ItemLib.MINING_MODE, Keyboard.KEY_M);

	public static void registerKeyBindings() {
		mc = FMLClientHandler.instance().getClient();
		KeyBindingRegistry.registerKeyBinding(
				new ToolBeltKeyBindingHandler(
						new KeyBinding[]{TOOL_BELT_KEY, MINING_MODE_KEY}, new boolean[]{false, false}));
	}
	
	public static void doToolBeltKeyUp() {
		World world = mc.theWorld;
		EntityPlayer entityplayer = mc.thePlayer;
		if (world.isRemote) {
			ItemStack toolBelt = ItemHelper.getToolBelt(entityplayer, world, true);
			if (toolBelt != null) {
				PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.CYCLE_TOOL_BELT);
				packet.setToolBeltId(toolBelt.getItemDamage());
				PacketDispatcher.sendPacketToServer(packet.getPacket());
			}
		}
	}
	
	public static void doMiningModeKeyUp() {
		World world = mc.theWorld;
		EntityPlayer entityplayer = mc.thePlayer;
		if (world.isRemote) {
			ItemStack toolBelt = ItemHelper.getToolBelt(entityplayer, world, true);
			if (toolBelt != null) {
				PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.TOGGLE_MINING_MODE);
				packet.setToolBeltId(toolBelt.getItemDamage());
				PacketDispatcher.sendPacketToServer(packet.getPacket());
			}
		}
	}

	public static void doKeyUp(EnumSet<TickType> types, KeyBinding kb) {
		if (kb.equals(TOOL_BELT_KEY)) {
			doToolBeltKeyUp(); 
		}
		if (kb.equals(MINING_MODE_KEY)) {
			doMiningModeKeyUp();
		}
	}
}
