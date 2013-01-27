package slimevoid.tmf.client.renderers;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.helpers.ItemHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemRendererToolBelt implements IItemRenderer {

	private Minecraft mc;

	public ItemRendererToolBelt(Minecraft client) {
		this.mc = client;
	}

	public static void init() {
		MinecraftForgeClient.registerItemRenderer(TMFCore.miningToolBeltId,
				new ItemRendererToolBelt(FMLClientHandler.instance()
						.getClient()));
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.INVENTORY)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemstack,
			Object... data) {
		if (type.equals(ItemRenderType.INVENTORY)) {
			doRenderInventoryItem(itemstack, (RenderBlocks) data[0]);
		} else {
			//if (type.equals(ItemRenderType.EQUIPPED)) {
			doRenderEquippedItem(itemstack, (RenderBlocks) data[0],
					(EntityLiving) data[1]);
			//}
		}
	}

	private void doRenderInventoryItem(ItemStack toolBelt,
			RenderBlocks renderBlocks) {
	}

	private void doRenderEquippedItem(ItemStack toolBelt,
			RenderBlocks renderBlocks, EntityLiving entityliving) {
		GL11.glPushMatrix();
		int index = 0;
		ItemStack itemstack = toolBelt;
		ItemStack tool = ItemHelper.getSelectedTool(entityliving,
				entityliving.worldObj, toolBelt);
		if (tool != null) {
			itemstack = tool;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine
				.getTexture(itemstack.getItem().getTextureFile()));

		Tessellator tessellator = Tessellator.instance;
		int icon = entityliving.getItemIcon(itemstack, index);
		float v1 = ((float) (icon % 16 * 16) + 0.0F) / 256.0F;
		float v2 = ((float) (icon % 16 * 16) + 15.99F) / 256.0F;
		float v3 = ((float) (icon / 16 * 16) + 0.0F) / 256.0F;
		float v4 = ((float) (icon / 16 * 16) + 15.99F) / 256.0F;
		ItemRenderer.renderItemIn2D(tessellator, v2, v3, v1, v4, 0.0625F);

		if (itemstack != null && itemstack.hasEffect() && index == 0) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.mc.renderEngine.bindTexture(this.mc.renderEngine
					.getTexture("%blur%/misc/glint.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float colourMultiplier = 0.76F;
			GL11.glColor4f(0.5F * colourMultiplier, 0.25F * colourMultiplier, 0.8F * colourMultiplier, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float s2 = 0.125F;
			GL11.glScalef(s2, s2, s2);
			float tTime = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(tTime, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F,
					0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(s2, s2, s2);
			tTime = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-tTime, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F,
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
