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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.helpers.ItemHelper;
import cpw.mods.fml.client.FMLClientHandler;

public class ItemRendererToolBelt implements IItemRenderer {

	private Minecraft	mc;
	private int			zLevel	= 0;

	public ItemRendererToolBelt(Minecraft client) {
		this.mc = client;
	}

	public static void init() {
		MinecraftForgeClient.registerItemRenderer(	TMFCore.miningToolBeltId,
													new ItemRendererToolBelt(FMLClientHandler.instance().getClient()));
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (type.equals(ItemRenderType.EQUIPPED)
			|| type.equals(ItemRenderType.INVENTORY)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
		if (type.equals(ItemRenderType.INVENTORY)) {
			doRenderInventoryItem(	itemstack,
									(RenderBlocks) data[0]);
		} else {
			// if (type.equals(ItemRenderType.EQUIPPED)) {
			doRenderEquippedItem(	itemstack,
									(RenderBlocks) data[0],
									(EntityLivingBase) data[1]);
			// }
		}
	}

	private void doRenderInventoryItem(ItemStack toolBelt, RenderBlocks renderBlocks) {

		TextureManager textureManager = this.mc.getTextureManager();
		int k = toolBelt.itemID;
		int l = toolBelt.getItemDamage();
		Object object = toolBelt.getIconIndex();
		float f;
		int i1;
		float f1;
		float f2;

		GL11.glDisable(GL11.GL_LIGHTING);
		ResourceLocation resourcelocation = textureManager.getResourceLocation(toolBelt.getItemSpriteNumber());
		textureManager.bindTexture(resourcelocation);

		if (object == null) {
			object = ((TextureMap) textureManager.getTexture(resourcelocation)).getAtlasSprite("missingno");
		}

		i1 = Item.itemsList[k].getColorFromItemStack(	toolBelt,
														0);
		f = (float) (i1 >> 16 & 255) / 255.0F;
		f1 = (float) (i1 >> 8 & 255) / 255.0F;
		f2 = (float) (i1 & 255) / 255.0F;

		// if (this.renderWithColor) {
		GL11.glColor4f(	f,
						f1,
						f2,
						1.0F);
		// }

		this.renderIcon(0,
						0,
						(Icon) object,
						16,
						16);
		GL11.glEnable(GL11.GL_LIGHTING);

	}

	public void renderIcon(int par1, int par2, Icon par3Icon, int par4, int par5) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (par1 + 0),
									(double) (par2 + par5),
									(double) this.zLevel,
									(double) par3Icon.getMinU(),
									(double) par3Icon.getMaxV());
		tessellator.addVertexWithUV((double) (par1 + par4),
									(double) (par2 + par5),
									(double) this.zLevel,
									(double) par3Icon.getMaxU(),
									(double) par3Icon.getMaxV());
		tessellator.addVertexWithUV((double) (par1 + par4),
									(double) (par2 + 0),
									(double) this.zLevel,
									(double) par3Icon.getMaxU(),
									(double) par3Icon.getMinV());
		tessellator.addVertexWithUV((double) (par1 + 0),
									(double) (par2 + 0),
									(double) this.zLevel,
									(double) par3Icon.getMinU(),
									(double) par3Icon.getMinV());
		tessellator.draw();
	}

	private void doRenderEquippedItem(ItemStack toolBelt, RenderBlocks renderBlocks, EntityLivingBase entityliving) {
		GL11.glPushMatrix();
		int index = 0;
		ItemStack itemstack = toolBelt;
		ItemStack tool = ItemHelper.getSelectedTool(entityliving,
													entityliving.worldObj,
													toolBelt);
		if (tool != null) {
			itemstack = tool;
		}

		Tessellator tessellator = Tessellator.instance;
		Icon icon = entityliving.getItemIcon(	itemstack,
												index);
		float v1 = icon.getMinU();// ((float) (icon % 16 * 16) + 0.0F) / 256.0F;
		float v2 = icon.getMaxU();// ((float) (icon % 16 * 16) + 15.99F) /
									// 256.0F;
		float v3 = icon.getMinV();// ((float) (icon / 16 * 16) + 0.0F) / 256.0F;
		float v4 = icon.getMaxV();// ((float) (icon / 16 * 16) + 15.99F) /
									// 256.0F;
		ItemRenderer.renderItemIn2D(tessellator,
									v2,
									v3,
									v1,
									v4,
									icon.getIconHeight(),
									icon.getIconWidth(),
									0.0625F);

		if (itemstack != null && itemstack.hasEffect(0) && index == 0) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.mc.renderEngine.bindTexture(new ResourceLocation("%blur%/misc/glint.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(	GL11.GL_SRC_COLOR,
								GL11.GL_ONE);
			float colourMultiplier = 0.76F;
			GL11.glColor4f(	0.5F * colourMultiplier,
							0.25F * colourMultiplier,
							0.8F * colourMultiplier,
							1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float s2 = 0.125F;
			GL11.glScalef(	s2,
							s2,
							s2);
			float tTime = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
			GL11.glTranslatef(	tTime,
								0.0F,
								0.0F);
			GL11.glRotatef(	-50.0F,
							0.0F,
							0.0F,
							1.0F);
			ItemRenderer.renderItemIn2D(tessellator,
										0.0F,
										0.0F,
										1.0F,
										1.0F,
										1,
										1,
										0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(	s2,
							s2,
							s2);
			tTime = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
			GL11.glTranslatef(	-tTime,
								0.0F,
								0.0F);
			GL11.glRotatef(	10.0F,
							0.0F,
							0.0F,
							1.0F);
			ItemRenderer.renderItemIn2D(tessellator,
										0.0F,
										0.0F,
										1.0F,
										1.0F,
										1,
										1,
										0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
		GL11.glPopMatrix();

	}

}
