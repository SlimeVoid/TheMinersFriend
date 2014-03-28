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
package net.slimevoid.tmf.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.slimevoid.tmf.blocks.machines.inventory.ContainerAutomaticMixingTable;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import net.slimevoid.tmf.core.lib.ResourceLib;

import org.lwjgl.opengl.GL11;

public class GuiAutomaticMixingTable extends GuiContainer {
    private TileEntityAutomaticMixingTable autoMixTable;

    public GuiAutomaticMixingTable(EntityPlayer entityplayer, TileEntityAutomaticMixingTable autoMixTable) {
        super(new ContainerAutomaticMixingTable(entityplayer.inventory, autoMixTable));
        this.xSize = 177;
        this.ySize = 221;
        this.autoMixTable = autoMixTable;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        // /int tex = mc.renderEngine.getTexture(ResourceLib.GUI_AUTOMIXTABLE);
        GL11.glColor4f(1.0F,
                       1.0F,
                       1.0F,
                       1.0F);
        mc.renderEngine.bindTexture(ResourceLib.GUI_AUTOMIXTABLE);
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
