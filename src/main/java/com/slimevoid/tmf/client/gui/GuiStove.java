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
package com.slimevoid.tmf.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.slimevoid.tmf.blocks.machines.inventory.ContainerStove;
import com.slimevoid.tmf.blocks.machines.tileentities.TileEntityStove;
import com.slimevoid.tmf.core.lib.ResourceLib;

public class GuiStove extends GuiContainer {
    private TileEntityStove stove;

    public GuiStove(EntityPlayer entityplayer, TileEntityStove tileStove) {
        super(new ContainerStove(entityplayer.inventory, tileStove));
        this.xSize = 177;
        this.ySize = 221;
        this.stove = tileStove;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        // /int tex = mc.renderEngine.getTexture(ResourceLib.GUI_AUTOMIXTABLE);
        GL11.glColor4f(1.0F,
                       1.0F,
                       1.0F,
                       1.0F);
        mc.renderEngine.bindTexture(ResourceLib.GUI_STOVE);
        int sizeX = (width - xSize) / 2;
        int sizeY = (height - ySize) / 2;
        drawTexturedModalRect(sizeX,
                              sizeY,
                              0,
                              0,
                              xSize,
                              ySize);

    }
}
