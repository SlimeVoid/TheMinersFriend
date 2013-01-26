package slimevoid.tmf.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.inventory.ContainerAutomaticMixingTable;
import slimevoid.tmf.machines.inventory.ContainerGeologicalEquipment;
import slimevoid.tmf.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GuiAutomaticMixingTable extends GuiContainer {
	private TileEntityAutomaticMixingTable autoMixTable;

	public GuiAutomaticMixingTable(EntityPlayer entityplayer, TileEntityAutomaticMixingTable autoMixTable) {
		super(new ContainerAutomaticMixingTable(entityplayer.inventory, autoMixTable));
		this.xSize = 177;
		this.ySize = 221;
		this.autoMixTable = autoMixTable;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		int tex = mc.renderEngine.getTexture(ResourceLib.GUI_AUTOMIXTABLE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);

	}
}
