package slimevoid.tmf.handlers;

import java.util.EnumSet;

import slimevoid.tmf.core.TMFCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MotionSensorTickHandler implements ITickHandler {
	private final Minecraft mc;
	
	public MotionSensorTickHandler() {
		mc = FMLClientHandler.instance().getClient();
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if ( type.equals(EnumSet.of(TickType.CLIENT)) ) {
			GuiScreen guiScreen = this.mc.currentScreen;
			if ( guiScreen == null )
				onTickInGame();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return null;
	}
	private void onTickInGame() {
		if ( mc.thePlayer != null && mc.thePlayer.inventory != null ) {
			for ( int i = 0; i < 9; i++ ) {
				ItemStack item = mc.thePlayer.inventory.mainInventory[i];
				if ( item != null )
					System.out.println(item.getItem().shiftedIndex+" - "+TMFCore.motionSensorId);
			}
		}
	}
}
