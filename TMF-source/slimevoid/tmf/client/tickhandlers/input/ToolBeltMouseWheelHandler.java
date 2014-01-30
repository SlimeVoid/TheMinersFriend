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
