package slimevoid.tmf.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.client.tickhandlers.MiningHelmetRenderTickHandler;
import slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleInToolbelt;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleOnHotbar;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ArmorLib;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.KeyBindings;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.core.lib.ReferenceLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.proxy.CommonProxy;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	
	@Override
	public void preInit() {
		super.preInit();
		
		PacketLib.registerClientPacketExecutors();
		
		EventLib.registerClientEvents();
		
		KeyBindings.registerKeyBindings();
	}

	@Override
	public void registerConfigurationProperties() {
		super.registerConfigurationProperties();
		ConfigurationLib.ClientConfig();
	}

	@Override
	public void registerRenderInformation() {
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering Renderers...");
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetIron,
						ResourceLib.IRON_MINING_HELMET)
		);
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetGold,
						ResourceLib.GOLD_MINING_HELMET)
		);
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetDiamond,
						ResourceLib.DIAMOND_MINING_HELMET)
		);
		MinecraftForgeClient.preloadTexture(ResourceLib.ITEM_SPRITE_PATH);
		MinecraftForgeClient.preloadTexture(ResourceLib.RESOURCE_SPRITE_PATH);
		MinecraftForgeClient.preloadTexture(ResourceLib.MACHINE_TEXTURE_PATH);
	}
	
	@Override
	public void registerTickHandler() {
		super.registerTickHandler();
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering Client tick handlers...");
		
		MotionSensorTickHandler motionSensor = new MotionSensorTickHandler(
				ConfigurationLib.motionSensorMaxEntityDistance, 
				ConfigurationLib.motionSensorMaxGameTicks, 
				ConfigurationLib.motionSensorDrawRight
		);
		motionSensor.addRule(new MotionSensorRuleOnHotbar());
		motionSensor.addRule(new MotionSensorRuleInToolbelt());
		TickRegistry.registerTickHandler(motionSensor, Side.CLIENT);

		TickRegistry.registerTickHandler(new MiningHelmetTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new MiningHelmetRenderTickHandler(), Side.CLIENT);
	}
	
	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}
}
