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
package slimevoid.tmf.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.items.tools.inventory.ContainerMiningToolBelt;
import slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;

public class GuiMiningToolBelt extends GuiContainer {

	InventoryMiningToolBelt	data;

	public GuiMiningToolBelt(EntityPlayer entityplayer, InventoryMiningToolBelt toolBelt) {
		super(new ContainerMiningToolBelt(entityplayer.inventory, toolBelt));
		this.xSize = 177;
		this.ySize = 221;
		data = toolBelt;
	}

	@Override
	public void initGui() {
		super.initGui();
		/*
		 * int motionSensorButtonLength =
		 * this.fontRenderer.getStringWidth(NamingLib.MOTION_SENSOR_SETTINGS);
		 * GuiButton motionSensorSettings = new GuiButton(
		 * GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID, (this.width / 2) -
		 * (motionSensorButtonLength / 2), (this.height / 2),
		 * motionSensorButtonLength + 6, 20, NamingLib.MOTION_SENSOR_SETTINGS);
		 * this.controlList.add(motionSensorSettings);
		 * ((GuiButton)this.controlList
		 * .get(GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID)).enabled = false;
		 */
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		/*
		 * switch(button.id) { case GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID :
		 * System.out.println("Settings"); break; default :
		 * System.out.println("Default"); break; }
		 */
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		int slot = data.getSelectedSlot();
		this.drawHighLightedSlot(	slot,
									par1,
									par2);
		/*
		 * ItemStack itemstack = this.data != null ? this.data.getStackInSlot(3)
		 * : null; GuiButton motionSensorButton =
		 * ((GuiButton)this.controlList.get
		 * (GuiLib.MOTION_SENSOR_SETTINGS_BUTTONID)); if (itemstack != null &&
		 * itemstack.getItem() instanceof ItemMotionSensor) {
		 * motionSensorButton.enabled = true; } else {
		 * motionSensorButton.enabled = false; }
		 */
	}

	private void drawHighLightedSlot(int slot, int par1, int par2) {
		// TODO :: Draw Highlighted Slot
		// Temporary Placeholder here
		Slot selectedSlot = this.inventorySlots.getSlot(slot);
		int x = selectedSlot.xDisplayPosition;
		int y = selectedSlot.yDisplayPosition;
		this.drawCenteredString(fontRenderer,
								"Slot[" + slot + "] Selected",
								50,
								100,
								0xff00ff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(	1.0F,
						1.0F,
						1.0F,
						1.0F);
		mc.renderEngine.bindTexture(ResourceLib.GUI_TOOLBELT);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(	sizeX,
								sizeY,
								0,
								0,
								xSize,
								ySize);
	}
}
