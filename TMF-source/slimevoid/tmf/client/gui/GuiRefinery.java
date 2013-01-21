package slimevoid.tmf.client.gui;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.blocks.machines.inventory.ContainerRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiRefinery extends GuiContainer {
	private TileEntityRefinery refinery;

	public GuiRefinery(EntityPlayer entityplayer, TileEntityRefinery refinery) {
		super(new ContainerRefinery(entityplayer.inventory, refinery));
		this.refinery = refinery;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		int tex = mc.renderEngine.getTexture("/TheMinersFriend/gui/refinery.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);
		
		int var7;
		if ( refinery.isBurning() ) {
			var7 = this.refinery.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(sizeX + 56, sizeY + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
		}
		
		var7 = this.refinery.getCookProgressScaled(24);
		this.drawTexturedModalRect(sizeX + 79, sizeY + 34, 176, 14, var7 + 1, 16);
	}

}
