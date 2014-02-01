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
package slimevoid.tmf.client.tickhandlers.input;

import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.ForgeSubscribe;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.CommandLib;
import slimevoid.tmf.core.lib.KeyBindings;
import cpw.mods.fml.client.FMLClientHandler;

public class ToolBeltMouseWheelHandler {

	@ForgeSubscribe
	public void onMouseEvent(MouseEvent event) {
		int wheel = event.dwheel;
		if (isToolBeltScroll(wheel)) {
			if (wheel > 0) {
				KeyBindings.doToolBeltCycle(CommandLib.CYCLE_TOOLBELT_UP);
				event.setCanceled(true);
			}
			if (wheel < 0) {
				KeyBindings.doToolBeltCycle(CommandLib.CYCLE_TOOLBELT_DOWN);
				event.setCanceled(true);
			}
		}
	}

	private boolean isToolBeltScroll(int wheel) {
		return ItemHelper.isToolBelt(FMLClientHandler.instance().getClient().thePlayer.getHeldItem())
				&& GameSettings.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak)
				&& wheel != 0;
	}
}
