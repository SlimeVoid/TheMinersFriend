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
package slimevoid.tmf.client.gui;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.inventory.ContainerGrinder;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public class GuiGrinder extends GuiContainer {
	private TileEntityGrinder grinder;

	public GuiGrinder(EntityPlayer entityplayer, TileEntityGrinder grinder) {
		super(new ContainerGrinder(entityplayer.inventory, grinder));
		this.grinder = grinder;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(ResourceLib.GUI_GRINDER);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);
		
		int var7;
		if ( grinder.isBurning() ) {
			var7 = this.grinder.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(sizeX + 56, sizeY + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
		}
		
		var7 = this.grinder.getCookProgressScaled(24);
		this.drawTexturedModalRect(sizeX + 79, sizeY + 34, 176, 14, var7 + 1, 16);
	}

}
