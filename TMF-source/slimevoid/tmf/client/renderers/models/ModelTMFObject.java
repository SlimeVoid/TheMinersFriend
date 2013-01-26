package slimevoid.tmf.client.renderers.models;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelTMFObject {
	private List<TexturedQuad> quadList;
	private List<PositionTextureVertex> vertexList;
	private ModelRenderer modelRenderer;
	
	public ModelTMFObject(ModelRenderer modelRenderer) {
		quadList = new ArrayList<TexturedQuad>();
		vertexList = new ArrayList<PositionTextureVertex>();
		this.modelRenderer = modelRenderer;
	}
	
	public int addVertex(float x, float y, float z, float u, float v) {
		int id = vertexList.size();
		vertexList.add(new PositionTextureVertex(x,y,z,u,v));
		return id;
	}
	
	public void addQuad(int a, int b, int c, int d, int u1, int v1, int u2, int v2, boolean flip) {
		TexturedQuad quad = new TexturedQuad(
				new PositionTextureVertex[] {
						vertexList.get(a),
						vertexList.get(b),
						vertexList.get(c),
						vertexList.get(d)
				},
				u1,
				v1,
				u2,
				v2,
				modelRenderer.textureWidth,
				modelRenderer.textureHeight
		);
		if ( flip )
			quad.flipFace();
		quadList.add(quad);
	}
	
	@SideOnly(Side.CLIENT)
	public void render(float par2) {
		for (int i = 0; i < quadList.size(); i++) {
			quadList.get(i).draw(Tessellator.instance, par2);
		}
	}
}
