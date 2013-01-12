package slimevoid.tmf.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.inventory.ContainerMiningToolBelt;

public class GuiMiningToolBelt extends GuiContainer {
	MiningToolBeltData data;
	
	public GuiMiningToolBelt(EntityPlayer entityplayer, MiningToolBeltData toolBelt) {
		super(new ContainerMiningToolBelt(entityplayer.inventory, toolBelt));
		this.xSize = 177;
		this.ySize = 221;
		data = toolBelt;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		int tex = mc.renderEngine.getTexture("/TheMinersFriend/gui/toolbeltGui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);
	}
}
