package slimevoid.tmf.client.renderers.models;

import java.io.File;

import slimevoid.lib.data.ModelSimevoidObject;
import slimevoid.lib.util.WavefrontOBJModelLoader;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class ModelGrinder extends ModelBase {
	public ModelSimevoidObject base;
	
	public ModelGrinder(TileEntityGrinder tile) {
		base = (new WavefrontOBJModelLoader()).loadObjFile(
				this, 
				256, 256, 
				new File(TMFCore.class.getResource("/TheMinersFriend/machines/grinder.obj").getFile())
		);
	}
	
	public void renderAll() {
		base.render(0.0625F);
	}
}
