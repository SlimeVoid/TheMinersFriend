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
package slimevoid.tmf.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import slimevoid.tmf.client.renderers.models.ModelGrinder;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoidlib.tileentity.TileEntityBase;

public class TileEntitySpecialRendererGrinder extends TileEntitySpecialRenderer {	
	
	public void bindResource(ResourceLocation resourceLocation) {
		this.bindTexture(resourceLocation);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTickTime) {
		ModelGrinder grinder = new ModelGrinder((TileEntityGrinder) tile);
		
		GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)x, (float)y, (float)z);
			
			int meta = 0;
			if ( tile.worldObj != null )
				meta = ((TileEntityBase) tile).getRotation();
			
			boolean dir = true;
			switch (meta) {
				case 2:
					dir = false;
					break;
				case 3:
					GL11.glTranslatef(0.5f, 0.5f, 0.5f);
					GL11.glRotatef(180, 0, 1, 0);
					GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
					dir = false;
					break;
				case 4:
					GL11.glTranslatef(0.5f, 0.5f, 0.5f);
					GL11.glRotatef(270, 0, 1, 0);
					GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
					break;
				case 5:
					GL11.glTranslatef(0.5f, 0.5f, 0.5f);
					GL11.glRotatef(90, 0, 1, 0);
					GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
					break;
			}

			grinder.renderAll(this, dir);
			
			if ( tile.getBlockType() != null ) {
				//grinder.updateBounds(tile.getBlockType());
			}
			
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
