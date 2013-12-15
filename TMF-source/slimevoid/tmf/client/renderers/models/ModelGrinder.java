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
package slimevoid.tmf.client.renderers.models;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.client.renderers.TileEntitySpecialRendererGrinder;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ModelLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoidlib.render.ModelSlimevoidObject;
import slimevoidlib.render.WavefrontOBJModelLoader;
import slimevoidlib.render.WavefrontOBJModelLoader.FaceMissingTextureException;

public class ModelGrinder extends ModelBase {
	public ModelSlimevoidObject staticModel;
	public ModelSlimevoidObject rollerModel;
	public ModelSlimevoidObject axlesModel;
	public ModelSlimevoidObject gearsModel;
	
	public TileEntityGrinder tile;
	
	public int rotationSpeedDivider = 20;
	
	public ModelGrinder(TileEntityGrinder tile) {
		this.tile = tile;
		try {
			staticModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(
							ResourceLib.getModelPath(true)+"grinderStatic.obj").getFile())
			);
			rollerModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(
							ResourceLib.getModelPath(true)+"grinderRoller.obj").getFile())
			);
			gearsModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(
							ResourceLib.getModelPath(true)+"grinderGears.obj").getFile())
			);
			axlesModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(
							ResourceLib.getModelPath(false)+"grinderAxles.obj").getFile())
			);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ArithmeticException e) {
			e.printStackTrace();
		} catch (FaceMissingTextureException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBounds(Block block) {
		ModelSlimevoidObject.ModelSlimevoidObjectBounds[] bounds = new ModelSlimevoidObject.ModelSlimevoidObjectBounds[4];
		bounds[0] = staticModel.getBounds();
		bounds[1] = rollerModel.getBounds();
		bounds[2] = axlesModel.getBounds();
		bounds[3] = gearsModel.getBounds();

		int minX = 0;
		int minY = 0;
		int minZ = 0;
		int maxX = 0;
		int maxY = 0;
		int maxZ = 0;
		
		for ( ModelSlimevoidObject.ModelSlimevoidObjectBounds bound: bounds ) {
			if ( bound.minX < minX || (minX == 0 && bound.minX != 0) )
				minX = (int) bound.minX;
			if ( bound.minY < minY )
				minY = (int) bound.minY;
			if ( bound.minZ < minZ || (minZ == 0 && bound.minZ != 0) )
				minZ = (int) bound.minZ;
			
			if ( bound.maxX > maxX )
				maxX = (int) bound.maxX;
			if ( bound.maxY > maxY )
				maxY = (int) bound.maxY;
			if ( bound.maxZ > maxZ )
				maxZ = (int) bound.maxZ;
		}
				
		block.setBlockBounds((float)minX/16f, (float)minY/16f, (float)minZ/16f, (float)maxX/16f, (float)maxY/16f, (float)maxZ/16f);
	}
	
	public void renderAll(TileEntitySpecialRendererGrinder renderer, boolean dir) {
		renderer.bindResource(ModelLib.GRINDER_STATIC);
		staticModel.render(0.0625F);

		GL11.glPushMatrix();
		if ( tile != null && tile.getBlockType() != null && tile.isActive ) {
			GL11.glTranslatef(0, 0.5f, 0.5f);	
			if ( dir ) 	GL11.glRotatef((System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			else 		GL11.glRotatef((-System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			GL11.glTranslatef(0, -0.5f, -0.5f);
		}
		renderer.bindResource(ModelLib.GRINDER_ROLLERS);
		rollerModel.render(0.0625F);
		renderer.bindResource(ModelLib.GRINDER_GEARS);
		gearsModel.render(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		if ( tile != null && tile.getBlockType() != null && tile.isActive ) {
			GL11.glTranslatef(0, 0.21875f, 0.5f);	
			if ( dir ) 	GL11.glRotatef((-System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			else 		GL11.glRotatef((System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			GL11.glTranslatef(0, -0.21875f, -0.5f);
		}
		renderer.bindResource(ModelLib.GRINDER_AXELS);
		axlesModel.render(0.0625F);
		GL11.glPopMatrix();
	}
}
