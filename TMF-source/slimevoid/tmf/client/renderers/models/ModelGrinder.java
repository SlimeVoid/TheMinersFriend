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
		
		bottom.addVertex(5,  5,  5,  0, 0);
		bottom.addVertex(10, 5,  5,  0, 0);
		bottom.addVertex(10, 10, 5,  0, 0);
		bottom.addVertex(5,  10, 5,  0, 0);
		bottom.addVertex(5,  5,  10, 0, 0);
		bottom.addVertex(10, 5,  10, 0, 0);
		bottom.addVertex(10, 10, 10, 0, 0);
		bottom.addVertex(5,  10, 10, 0, 0);
		
		int tex0 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 0);
		int tex1 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 1);
		int tex2 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 2);
		int tex3 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 3);
		int tex4 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 4);
		int tex5 = tile.getBlockType().getBlockTexture(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, 5);
		
		bottom.addQuad(5,4,0,1, ((tex0)%16)*16+16, ((tex0)/16)*16+16, ((tex0)%16)*16, ((tex0)/16)*16, false);
		bottom.addQuad(2,3,7,6, ((tex1)%16)*16+16, ((tex1)/16)*16+16, ((tex1)%16)*16, ((tex1)/16)*16, false);
		bottom.addQuad(1,0,3,2, ((tex2)%16)*16+16, ((tex2)/16)*16+16, ((tex2)%16)*16, ((tex2)/16)*16, false);
		bottom.addQuad(4,5,6,7, ((tex3)%16)*16+16, ((tex3)/16)*16+16, ((tex3)%16)*16, ((tex3)/16)*16, false);
		bottom.addQuad(0,4,7,3, ((tex4)%16)*16+16, ((tex4)/16)*16+16, ((tex4)%16)*16, ((tex4)/16)*16, false);
		bottom.addQuad(5,1,2,6, ((tex5)%16)*16+16, ((tex5)/16)*16+16, ((tex5)%16)*16, ((tex5)/16)*16, false);
	}
	
	public void renderAll() {
		bottom.render(0.0625F);
	}
}
