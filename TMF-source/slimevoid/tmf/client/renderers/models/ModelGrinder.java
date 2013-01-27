package slimevoid.tmf.client.renderers.models;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class ModelGrinder extends ModelBase {
	public ModelTMFObject bottom;
	
	public ModelGrinder(TileEntityGrinder tile) {
		
		bottom = new ModelTMFObject((new ModelRenderer(this, 0, 0)).setTextureSize(256, 256));
		
		bottom.addVertex(16, 0,  0,  0, 0);
		bottom.addVertex(16, 0,  16, 0, 0);
		bottom.addVertex(0,  0,  16, 0, 0);
		bottom.addVertex(0,  0,  0,  0, 0);
		bottom.addVertex(16, 16, 0,  0, 0);
		bottom.addVertex(0,  16, 0,  0, 0);
		bottom.addVertex(0,  16, 16, 0, 0);
		bottom.addVertex(16, 16, 16, 0, 0);
				
		bottom.addVertexTexture(0.312500f, 0.937500f);
		bottom.addVertexTexture(0.375000f, 0.937500f);
		bottom.addVertexTexture(0.375000f, 0.875000f);
		bottom.addVertexTexture(0.312500f, 0.875000f);
		bottom.addVertexTexture(0.250000f, 0.875000f);
		bottom.addVertexTexture(0.250000f, 0.937500f);
		bottom.addVertexTexture(0.187500f, 0.937500f);
		bottom.addVertexTexture(0.187500f, 0.875000f);
		bottom.addVertexTexture(0.125000f, 0.875000f);
		bottom.addVertexTexture(0.125000f, 0.937500f);
		bottom.addVertexTexture(0.062500f, 0.937500f);
		bottom.addVertexTexture(0.062500f, 0.875000f);
		
		bottom.addQuad(0, 1, 2, 3, 0, 1, 2, 3, false);
		bottom.addQuad(4, 5, 6, 7, 0, 3, 2, 1, false);
		bottom.addQuad(0, 4, 7, 1, 4, 5, 6, 7, false);
		bottom.addQuad(1, 7, 6, 2, 4, 5, 6, 7, false);
		bottom.addQuad(2, 6, 5, 3, 8, 9, 10, 11, false);
		bottom.addQuad(4, 0, 3, 5, 6, 7, 4, 5, false);
	}
	
	public void renderAll() {
		bottom.render(0.0625F);
	}
}
