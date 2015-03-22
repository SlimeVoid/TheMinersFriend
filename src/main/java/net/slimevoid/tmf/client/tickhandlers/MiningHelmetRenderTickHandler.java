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
package net.slimevoid.tmf.client.tickhandlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.slimevoid.tmf.core.lib.ArmorLib;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class MiningHelmetRenderTickHandler {
    private final Minecraft mc;

    public MiningHelmetRenderTickHandler() {
        this.mc = FMLClientHandler.instance().getClient();
    }

    @SubscribeEvent
    public void onRenderEvent(RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            EntityPlayer entityplayer = mc.thePlayer;
            World world = mc.theWorld;
            if (entityplayer != null && world != null) {
                this.onRenderTick(entityplayer,
                                  world);
            }
        }
    }

    private void onRenderTick(EntityPlayer entityplayer, World world) {
        ItemStack miningHelmet = ArmorLib.getPlayerHelm(entityplayer,
                                                        world);
        if (miningHelmet != null) {
            doRenderMinersLamp(entityplayer,
                               world);
        }
    }

    private void doRenderMinersLamp(EntityPlayer entityplayer, World world) {
        // System.out.println("Render Lamp");
        GL11.glPushMatrix();
        // TODO :: Render Lamp
        GL11.glPopMatrix();
    }

}
