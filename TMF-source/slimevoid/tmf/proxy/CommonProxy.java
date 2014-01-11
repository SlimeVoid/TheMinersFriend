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
package slimevoid.tmf.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.tmf.api.ITMFCommonProxy;
import slimevoid.tmf.blocks.machines.inventory.ContainerAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.inventory.ContainerGeologicalEquipment;
import slimevoid.tmf.blocks.machines.inventory.ContainerGrinder;
import slimevoid.tmf.blocks.machines.inventory.ContainerRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.items.tools.inventory.ContainerMiningToolBelt;
import slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import slimevoidlib.IPacketHandling;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.util.helpers.SlimevoidHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements ITMFCommonProxy {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case GuiLib.TOOL_BELT_GUIID:
			InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, player, player.getHeldItem());
			return new ContainerMiningToolBelt(player.inventory, data);
		case GuiLib.REFINERY_GUIID:
			TileEntity tileRef = SlimevoidHelper.getBlockTileEntity(world,
																	x,
																	y,
																	z);
			if (tileRef instanceof TileEntityRefinery) {
				TileEntityRefinery tileRefinery = (TileEntityRefinery) tileRef;
				return new ContainerRefinery(player.inventory, tileRefinery);
			}
			return null;
		case GuiLib.GRINDER_GUIID:
			TileEntity tileGrind = SlimevoidHelper.getBlockTileEntity(	world,
																		x,
																		y,
																		z);
			if (tileGrind instanceof TileEntityGrinder) {
				TileEntityGrinder tileGrinder = (TileEntityGrinder) tileGrind;
				return new ContainerGrinder(player.inventory, tileGrinder);
			}
			return null;
		case GuiLib.GEOEQUIP_GUIID:
			TileEntity tileGeo = SlimevoidHelper.getBlockTileEntity(world,
																	x,
																	y,
																	z);
			if (tileGeo instanceof TileEntityGeologicalEquipment) {
				TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) tileGeo;
				return new ContainerGeologicalEquipment(player.inventory, tileGeoEquip);
			}
			return null;
		case GuiLib.MIXINGTABLE_GUIID:
			TileEntity tileMix = SlimevoidHelper.getBlockTileEntity(world,
																	x,
																	y,
																	z);
			if (tileMix instanceof TileEntityAutomaticMixingTable) {
				TileEntityAutomaticMixingTable tileMixTable = (TileEntityAutomaticMixingTable) tileMix;
				return new ContainerAutomaticMixingTable(player.inventory, tileMixTable);
			}
			return null;
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void preInit() {
		NetworkRegistry.instance().registerGuiHandler(	TheMinersFriend.instance,
														this);

		PacketLib.registerPacketExecutors();

		EventLib.registerCommonEvents();

		MiningMode.init(DataLib.MINING_MODE_STRENGTH);
	}

	@Override
	public void registerConfigurationProperties(File configFile) {
		SlimevoidCore.console(	CoreLib.MOD_ID,
								"Loading properties...");
		ConfigurationLib.CommonConfig(configFile);
	}

	@Override
	public void registerTickHandlers() {
		TickRegistry.registerTickHandler(	new MiningHelmetTickHandler(),
											Side.SERVER);
	}

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public String getMinecraftDir() {
		return ".";
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
	}

	@Override
	public IPacketHandling getPacketHandler() {
		return null;
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
	}

	@Override
	public void registerTESRenderers() {
	}

	@Override
	public void registerEventHandlers() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isClient(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub

	}

	@Override
	public EntityPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}
}
