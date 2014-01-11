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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.client.gui.GuiAutomaticMixingTable;
import slimevoid.tmf.client.gui.GuiGeologicalEquipment;
import slimevoid.tmf.client.gui.GuiGrinder;
import slimevoid.tmf.client.gui.GuiMiningToolBelt;
import slimevoid.tmf.client.gui.GuiRefinery;
import slimevoid.tmf.client.renderers.BlockMachineRenderingHandler;
import slimevoid.tmf.client.renderers.ItemRendererToolBelt;
import slimevoid.tmf.client.renderers.TileEntitySpecialRendererGrinder;
import slimevoid.tmf.client.tickhandlers.MiningHelmetRenderTickHandler;
import slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleInToolbelt;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleOnHotbar;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ArmorLib;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.KeyBindings;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.items.tools.data.MiningToolBelt;
import slimevoid.tmf.proxy.CommonProxy;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.util.helpers.SlimevoidHelper;
import cpw.mods.fml.client.FMLClientHandler;
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
		SlimevoidCore.console(	CoreLib.MOD_ID,
								"Registering Renderers...");
		ArmorLib.registerArmorTexture(	TMFCore.miningHelmetIron,
										ResourceLib.IRON_MINING_HELMET);
		ArmorLib.registerArmorTexture(	TMFCore.miningHelmetGold,
										ResourceLib.GOLD_MINING_HELMET);
		ArmorLib.registerArmorTexture(	TMFCore.miningHelmetDiamond,
										ResourceLib.DIAMOND_MINING_HELMET);

		ItemRendererToolBelt.init();
	}

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		SlimevoidCore.console(	CoreLib.MOD_ID,
								"Registering Client tick handlers...");

		MotionSensorTickHandler motionSensor = new MotionSensorTickHandler(ConfigurationLib.motionSensorMaxEntityDistance, ConfigurationLib.motionSensorMaxGameTicks, ConfigurationLib.motionSensorDrawRight);
		motionSensor.addRule(new MotionSensorRuleOnHotbar());
		motionSensor.addRule(new MotionSensorRuleInToolbelt());
		TickRegistry.registerTickHandler(	motionSensor,
											Side.CLIENT);

		TickRegistry.registerTickHandler(	new MiningHelmetTickHandler(),
											Side.CLIENT);
		TickRegistry.registerTickHandler(	new MiningHelmetRenderTickHandler(),
											Side.CLIENT);
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir.getPath();
	}

	@Override
	public void registerTESRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(	TileEntityGrinder.class,
														new TileEntitySpecialRendererGrinder());
		RenderingRegistry.registerBlockHandler(new BlockMachineRenderingHandler());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case GuiLib.TOOL_BELT_GUIID:
			MiningToolBelt data = new MiningToolBelt(world, player, player.getHeldItem());
			return new GuiMiningToolBelt(player, data);
		case GuiLib.REFINERY_GUIID:
			TileEntity tile1 = SlimevoidHelper.getBlockTileEntity(	world,
																	x,
																	y,
																	z);
			if (tile1 instanceof TileEntityRefinery) {
				TileEntityRefinery tileRefinery = (TileEntityRefinery) tile1;
				return new GuiRefinery(player, tileRefinery);
			}
			return null;
		case GuiLib.GRINDER_GUIID:
			TileEntity tile2 = SlimevoidHelper.getBlockTileEntity(	world,
																	x,
																	y,
																	z);
			if (tile2 instanceof TileEntityGrinder) {
				TileEntityGrinder tileGrinder = (TileEntityGrinder) tile2;
				return new GuiGrinder(player, tileGrinder);
			}
			return null;
		case GuiLib.GEOEQUIP_GUIID:
			TileEntity tileGeo = SlimevoidHelper.getBlockTileEntity(world,
																	x,
																	y,
																	z);
			if (tileGeo instanceof TileEntityGeologicalEquipment) {
				TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) tileGeo;
				return new GuiGeologicalEquipment(player, tileGeoEquip);
			}
			return null;
		case GuiLib.MIXINGTABLE_GUIID:
			TileEntity tileMix = SlimevoidHelper.getBlockTileEntity(world,
																	x,
																	y,
																	z);
			if (tileMix instanceof TileEntityAutomaticMixingTable) {
				TileEntityAutomaticMixingTable tileMixTable = (TileEntityAutomaticMixingTable) tileMix;
				return new GuiAutomaticMixingTable(player, tileMixTable);
			}
			return null;
		default:
			return null;
		}
	}

	@Override
	public EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}

	@Override
	public World getWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
