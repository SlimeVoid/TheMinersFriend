package slimevoid.tmf.client.gui;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.machines.inventory.ContainerGeologicalEquipment;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public class GuiGeologicalEquipment extends GuiContainer {
	private TileEntityGeologicalEquipment geoEquip;

	public GuiGeologicalEquipment(EntityPlayer entityplayer, TileEntityGeologicalEquipment geoEquip) {
		super(new ContainerGeologicalEquipment(entityplayer.inventory, geoEquip));
		this.geoEquip = geoEquip;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		int tex = mc.renderEngine.getTexture("/TheMinersFriend/gui/geoequip.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);

		int var7;
		
		if ( geoEquip.isBurning() ) {
			var7 = this.geoEquip.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(sizeX + 9, sizeY + 52 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
		}
		var7 = this.geoEquip.getCookProgressScaled(24);
		this.drawTexturedModalRect(sizeX + 31, sizeY + 51, 176, 14, var7 + 1, 16);
	}

}
