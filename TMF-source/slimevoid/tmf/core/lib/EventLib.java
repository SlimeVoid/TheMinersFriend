package slimevoid.tmf.core.lib;

import net.minecraftforge.common.MinecraftForge;
import slimevoid.tmf.client.sounds.TrackerSounds;
import slimevoid.tmf.core.events.MiningToolBeltBreakSpeed;
import slimevoid.tmf.core.events.MiningToolBeltHarvestCheck;
import slimevoid.tmf.core.events.MiningToolBeltEntityInteract;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventLib {
	
	@SideOnly(Side.CLIENT)
	public static void registerClientEvents() {
		MinecraftForge.EVENT_BUS.register(new TrackerSounds());
	}
	
	public static void registerCommonEvents() {
		MinecraftForge.EVENT_BUS.register(new MiningToolBeltEntityInteract());
		MinecraftForge.EVENT_BUS.register(new MiningToolBeltHarvestCheck());
		MinecraftForge.EVENT_BUS.register(new MiningToolBeltBreakSpeed());
	}
}
