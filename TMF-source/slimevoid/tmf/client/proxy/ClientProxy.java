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
package slimevoid.tmf.client.proxy;

import java.io.File;

import net.minecraft.client.Minecraft;
import slimevoid.tmf.client.renderers.ItemRendererToolBelt;
import slimevoid.tmf.client.renderers.SimpleBlockRenderingHandlerGrinder;
import slimevoid.tmf.client.renderers.TileEntitySpecialRendererGrinder;
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
import slimevoid.tmf.core.lib.CoreLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.proxy.CommonProxy;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import slimevoidlib.core.SlimevoidCore;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
	public void registerConfigurationProperties(File configFile) {
		super.registerConfigurationProperties(configFile);
		ConfigurationLib.ClientConfig();
	}

	@Override
	public void registerRenderInformation() {
		SlimevoidCore.console(CoreLib.MOD_ID, "Registering Renderers...");
//		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetIron,
						ResourceLib.IRON_MINING_HELMET);
//		);
//		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetGold,
						ResourceLib.GOLD_MINING_HELMET);
//		);
//		MinecraftForgeClient.preloadTexture(
				ArmorLib.registerArmorTexture(
						TMFCore.miningHelmetDiamond,
						ResourceLib.DIAMOND_MINING_HELMET);
//		);
//		MinecraftForgeClient.preloadTexture(ResourceLib.ITEM_SPRITE_PATH);
//		MinecraftForgeClient.preloadTexture(ResourceLib.RESOURCE_SPRITE_PATH);
//		MinecraftForgeClient.preloadTexture(ResourceLib.MACHINE_TEXTURE_PATH);
		
		ItemRendererToolBelt.init();
	}
	
	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		SlimevoidCore.console(CoreLib.MOD_ID, "Registering Client tick handlers...");
		
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
		return Minecraft.getMinecraft().mcDataDir.getPath();
	}
	
	@Override
	public void registerTESRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrinder.class, new TileEntitySpecialRendererGrinder());
		RenderingRegistry.registerBlockHandler(new SimpleBlockRenderingHandlerGrinder());
	}
}
