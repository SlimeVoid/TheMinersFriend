package slimevoid.tmf.client.renderers.models;

import java.io.File;

import org.lwjgl.opengl.GL11;

import slimevoid.lib.data.ModelSimevoidObject;
import slimevoid.lib.util.WavefrontOBJModelLoader;
import slimevoid.lib.util.WavefrontOBJModelLoader.FaceMissingTextureException;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.blocks.BlockMachine;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class ModelGrinder extends ModelBase {
	public ModelSimevoidObject base;
	public ModelSimevoidObject spinner;
	public TileEntityGrinder tile;
	
	public ModelGrinder(TileEntityGrinder tile) {
		this.tile = tile;
		try {
			base = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(ResourceLib.MODEL_GRINDER).getFile())
			);
			spinner = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(ResourceLib.MACHINE_PREFIX+"grinderSpinner.obj").getFile())
			);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ArithmeticException e) {
			e.printStackTrace();
		} catch (FaceMissingTextureException e) {
			e.printStackTrace();
		}
	}
	
	public void renderAll() {
		base.render(0.0625F);
		GL11.glPushMatrix();
		if ( tile != null && tile.getBlockType() != null && ((BlockMachine)(tile.getBlockType())).isActive ) {
			GL11.glTranslatef(0.5f, 0, 0.5f);	
			GL11.glRotatef(System.currentTimeMillis()%360, 0, 1, 0);
			GL11.glTranslatef(-0.5f, 0, -0.5f);
		}
		spinner.render(0.0625F);
		GL11.glPopMatrix();
	}
}
