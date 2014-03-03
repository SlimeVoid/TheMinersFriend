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
package com.slimevoid.tmf.core.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import com.slimevoid.library.util.helpers.PacketHelper;
import com.slimevoid.tmf.client.tickhandlers.input.ToolBeltKeyBindingHandler;
import com.slimevoid.tmf.core.helpers.ItemHelper;
import com.slimevoid.tmf.network.packets.PacketMiningToolBelt;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings {

    private static Minecraft mc;
    // public static KeyBinding TOOL_BELT_KEY = new
    // KeyBinding(ItemLib.MINING_TOOLBELT, Keyboard.KEY_LSHIFT);
    public static KeyBinding MINING_MODE_KEY = new KeyBinding(ItemLib.MINING_MODE, Keyboard.KEY_M, "key.categories.misc");

    public static void registerKeyBindings() {
        mc = FMLClientHandler.instance().getClient();
        ClientRegistry.registerKeyBinding(MINING_MODE_KEY);
        MinecraftForge.EVENT_BUS.register(new ToolBeltKeyBindingHandler());
    }

    public static void checkTMFInput() {
        if (FMLClientHandler.instance().getClient().currentScreen == null) {
            if (MINING_MODE_KEY.isPressed()) {
                doMiningModeKeyUp();
            }
        }
    }

    public static void doMiningModeKeyUp() {
        World world = mc.theWorld;
        EntityPlayer entityplayer = mc.thePlayer;
        if (world.isRemote) {
            ItemStack toolBelt = ItemHelper.getToolBelt(entityplayer,
                                                        world,
                                                        true);
            if (toolBelt != null) {
                PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.TOGGLE_MINING_MODE);
                PacketHelper.sendToServer(packet);
            }
        }
    }

    public static void doToolBeltCycle(int direction) {
        World world = mc.theWorld;
        EntityPlayer entityplayer = mc.thePlayer;
        if (world.isRemote) {
            ItemStack toolBelt = ItemHelper.getToolBelt(entityplayer,
                                                        world,
                                                        true);
            if (toolBelt != null) {
                PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.CYCLE_TOOL_BELT);
                packet.side = direction;
                // packet.setToolBeltId(toolBelt.getItemDamage());
                PacketHelper.sendToServer(packet);
            }
        }
    }
}
