package slimevoid.tmf.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.data.MiningToolBelt;
import slimevoid.tmf.inventory.ContainerMiningToolBelt;
import slimevoid.tmf.items.ItemMotionSensor;
import slimevoid.tmf.lib.GuiLib;
import slimevoid.tmf.lib.NamingLib;

public class GuiMiningToolBelt extends GuiContainer {
	MiningToolBelt data;
	
	public GuiMiningToolBelt(EntityPlayer entityplayer, MiningToolBelt toolBelt) {
		super(new ContainerMiningToolBelt(entityplayer.inventory, toolBelt));
		this.xSize = 177;
		this.ySize = 221;
		data = toolBelt;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int motionSensorButtonLength = this.fontRenderer.getStringWidth(NamingLib.MOTION_SENSOR_SETTINGS);
		GuiButton motionSensorSettings = new GuiButton(
				GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID,
				(this.width / 2) - (motionSensorButtonLength / 2),
				(this.height / 2),
				motionSensorButtonLength + 6,
				20,
				NamingLib.MOTION_SENSOR_SETTINGS);
		this.controlList.add(motionSensorSettings);
		((GuiButton)this.controlList.get(GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID)).enabled = false;
	}
	
	@Override
    protected void actionPerformed(GuiButton button) {
		switch(button.id) {
			case GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID :
				System.out.println("Settings");
				break;
			default :
				System.out.println("Default");
				break;
		}
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		ItemStack itemstack = this.data != null ? this.data.getStackInSlot(3) : null;
		GuiButton motionSensorButton = ((GuiButton)this.controlList.get(GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID));
		if (itemstack != null && itemstack.getItem() instanceof ItemMotionSensor) {
			motionSensorButton.enabled = true;
		} else {
			motionSensorButton.enabled = false;
		}
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
