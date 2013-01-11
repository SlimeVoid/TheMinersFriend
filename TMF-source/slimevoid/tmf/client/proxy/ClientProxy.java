package slimevoid.tmf.client.proxy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.client.sounds.TrackerSounds;
import slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.TMFInit;
import slimevoid.tmf.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	
	@Override
	public void preInit() {
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Registering sounds...");
		MinecraftForge.EVENT_BUS.register(new TrackerSounds());
	}

	@Override
	public void registerRenderInformation() {
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Registering Renderers...");	
	}
	
	@Override
	public void registerTickHandler() {
		super.registerTickHandler();
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Registering Client tick handlers...");
		TickRegistry.registerTickHandler(new MotionSensorTickHandler(TMFCore.motionSensorMaxEntityDistance, TMFCore.motionSensorMaxGameTicks), Side.CLIENT);
	}
	
	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}
}
