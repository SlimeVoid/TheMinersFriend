package slimevoid.tmf.client.renderers.models;

import java.io.File;

import slimevoid.lib.data.ModelSimevoidObject;
import slimevoid.lib.util.WavefrontOBJModelLoader;
import slimevoid.lib.util.WavefrontOBJModelLoader.FaceMissingTextureException;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class ModelGrinder extends ModelBase {
	public ModelSimevoidObject base;
	
	public ModelGrinder(TileEntityGrinder tile) {
		try {
			base = (new WavefrontOBJModelLoader()).loadObjFile(
					this, 
					256, 256, 
					new File(TMFCore.class.getResource(ResourceLib.MODEL_GRINDER).getFile())
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
	}
}
