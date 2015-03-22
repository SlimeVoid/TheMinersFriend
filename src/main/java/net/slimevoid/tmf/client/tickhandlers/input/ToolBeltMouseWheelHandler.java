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
package net.slimevoid.tmf.client.tickhandlers.input;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import net.slimevoid.tmf.core.lib.CommandLib;
import net.slimevoid.tmf.core.lib.KeyBindings;
import net.slimevoid.tmf.core.lib.PacketLib;

import org.lwjgl.input.Keyboard;

public class ToolBeltMouseWheelHandler {

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        int wheel = event.dwheel;
        EntityPlayer entityplayer = FMLClientHandler.instance().getClient().thePlayer;
        World world = entityplayer.getEntityWorld();
        ItemStack heldItem = entityplayer.getHeldItem();
        if (ItemHelper.isToolBelt(heldItem)) {
            if (isScrolling(wheel)) {
                if (wheel > 0) {
                    KeyBindings.doToolBeltCycle(CommandLib.CYCLE_TOOLBELT_UP);
                    event.setCanceled(true);
                }
                if (wheel < 0) {
                    KeyBindings.doToolBeltCycle(CommandLib.CYCLE_TOOLBELT_DOWN);
                    event.setCanceled(true);
                }
            }
            if (isRightClicking(event.button)) {
                PacketLib.sendToolBeltGuiRequest(world,
                                                 entityplayer);
                event.setCanceled(true);
            }
        }
    }

    private boolean isScrolling(int wheel) {
        return GameSettings.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak)
               && wheel != 0;
    }

    private boolean isRightClicking(int button) {
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && button == 1;
    }
}
