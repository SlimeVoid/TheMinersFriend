package slimevoid.tmf.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import slimevoid.tmf.inventory.ContainerMiningToolBelt;

public class GuiMiningToolBelt extends GuiContainer {
	private IInventory toolBelt;
	
	public GuiMiningToolBelt(IInventory playerInventory, IInventory toolBelt) {
		super(new ContainerMiningToolBelt(playerInventory ,toolBelt));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
	}
}
