package slimevoid.tmf.client.renderers.models;

import java.io.File;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import slimevoid.lib.render.ModelSlimevoidObject;
import slimevoid.lib.render.WavefrontOBJModelLoader;
import slimevoid.lib.render.WavefrontOBJModelLoader.FaceMissingTextureException;
import slimevoid.tmf.client.renderers.TileEntitySpecialRendererGrinder;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.blocks.BlockMachine;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.PositionTextureVertex;

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
					new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"grinderStatic.obj").getFile())
			);
			if ( FMLClientHandler.instance().getClient().gameSettings.fancyGraphics ) {
				rollerModel = (new WavefrontOBJModelLoader()).loadObjFile(
						this, 
						256, 256, 
						new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"hd/grinderRoller.obj").getFile())
				);
			} else {
				rollerModel = (new WavefrontOBJModelLoader()).loadObjFile(
						this, 
						256, 256, 
						new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"grinderRoller.obj").getFile())
				);
			}
			axlesModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"grinderAxles.obj").getFile())
			);
			gearsModel = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"grinderGears.obj").getFile())
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
		renderer.bindTexture(ResourceLib.MACHINE_PREFIX+"grinderStatic.png");
		staticModel.render(0.0625F);

		GL11.glPushMatrix();
		if ( tile != null && tile.getBlockType() != null && ((BlockMachine)(tile.getBlockType())).isActive ) {
			GL11.glTranslatef(0, 0.5f, 0.5f);	
			if ( dir ) 	GL11.glRotatef((System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			else 		GL11.glRotatef((-System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			GL11.glTranslatef(0, -0.5f, -0.5f);
		}
		renderer.bindTexture(ResourceLib.MACHINE_PREFIX+"grinderRoller.png");
		rollerModel.render(0.0625F);
		renderer.bindTexture(ResourceLib.MACHINE_PREFIX+"grinderGears.png");
		gearsModel.render(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		if ( tile != null && tile.getBlockType() != null && ((BlockMachine)(tile.getBlockType())).isActive ) {
			GL11.glTranslatef(0, 0.21875f, 0.5f);	
			if ( dir ) 	GL11.glRotatef((-System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			else 		GL11.glRotatef((System.currentTimeMillis()/rotationSpeedDivider)%360, 1, 0, 0);
			GL11.glTranslatef(0, -0.21875f, -0.5f);
		}
		renderer.bindTexture(ResourceLib.MACHINE_PREFIX+"grinderAxles.png");
		axlesModel.render(0.0625F);
		GL11.glPopMatrix();
	}
}
