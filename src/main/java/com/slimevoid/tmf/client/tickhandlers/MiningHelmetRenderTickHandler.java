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
package com.slimevoid.tmf.client.tickhandlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.slimevoid.tmf.core.lib.ArmorLib;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MiningHelmetRenderTickHandler implements ITickHandler {
    private final Minecraft mc;

    public MiningHelmetRenderTickHandler() {
        this.mc = FMLClientHandler.instance().getClient();
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.CLIENT))
            || type.equals(EnumSet.of(TickType.RENDER))

        ) {
            EntityPlayer entityplayer = mc.thePlayer;
            World world = mc.theWorld;
            if (entityplayer != null && world != null) {
                if (type.equals(EnumSet.of(TickType.RENDER))) {
                    this.onRenderTick(entityplayer,
                                      world);
                }
            }
        }
    }

    private void onRenderTick(EntityPlayer entityplayer, World world) {
        ItemStack miningHelmet = ArmorLib.getHelm(entityplayer,
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

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT,
                          TickType.RENDER);
    }

    @Override
    public String getLabel() {
        return "Miners Helmet Rendering";
    }

}
