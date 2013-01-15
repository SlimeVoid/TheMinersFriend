package slimevoid.tmf.client.tickhandlers.input;

import java.util.EnumSet;

import slimevoid.tmf.core.lib.KeyBindings;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ToolBeltKeyBindingHandler extends KeyHandler {

	public ToolBeltKeyBindingHandler(KeyBinding[] keyBindings) {
		super(keyBindings, new boolean[]{false});
	}

	@Override
	public String getLabel() {
		return "ToolBeltKeyBindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {		
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (tickEnd)
			KeyBindings.doToolBeltKeyUp();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
