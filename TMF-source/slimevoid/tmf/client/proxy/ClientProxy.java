package slimevoid.tmf.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.client.network.ClientPacketHandler;
import slimevoid.tmf.client.network.handlers.ClientPacketMiningToolBeltHandler;
import slimevoid.tmf.client.network.packets.executors.ClientMiningToolBeltUpdateExecutor;
import slimevoid.tmf.client.tickhandlers.MiningHelmetRenderTickHandler;
import slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleInToolbelt;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleOnHotbar;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.TMFInit;
import slimevoid.tmf.core.lib.ArmorLib;
import slimevoid.tmf.core.lib.CommandLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.proxy.CommonProxy;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	
	@Override
	public void preInit() {
		super.preInit();
		ClientPacketHandler.init();
		
		ClientPacketMiningToolBeltHandler clientToolBeltHandler = new ClientPacketMiningToolBeltHandler();
		clientToolBeltHandler.registerPacketHandler(
				CommandLib.UPDATE_TOOL_BELT_CONTENTS,
				new ClientMiningToolBeltUpdateExecutor());
		ClientPacketHandler.registerPacketHandler(PacketLib.MINING_TOOL_BELT, clientToolBeltHandler);		
		
		EventLib.registerClientEvents();
	}

	@Override
	public void registerRenderInformation() {
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Registering Renderers...");
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetIron,
						ArmorLib.IRON_MINING_HELMET)
		);
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetGold,
						ArmorLib.GOLD_MINING_HELMET)
		);
		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetDiamond,
						ArmorLib.DIAMOND_MINING_HELMET)
		);
	}
	
	@Override
	public void registerTickHandler() {
		super.registerTickHandler();
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Registering Client tick handlers...");
		
		MotionSensorTickHandler motionSensor = new MotionSensorTickHandler(
				TMFCore.motionSensorMaxEntityDistance, 
				TMFCore.motionSensorMaxGameTicks, 
				TMFCore.motionSensorDrawRight
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
