package slimevoid.tmf.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.lib.IPacketHandling;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.api.ITMFCommonProxy;
import slimevoid.tmf.client.gui.GuiGeologicalEquipment;
import slimevoid.tmf.client.gui.GuiMiningToolBelt;
import slimevoid.tmf.client.gui.GuiRefinery;
import slimevoid.tmf.client.gui.GuiGrinder;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.core.lib.ReferenceLib;
import slimevoid.tmf.machines.inventory.ContainerGeologicalEquipment;
import slimevoid.tmf.machines.inventory.ContainerGrinder;
import slimevoid.tmf.machines.inventory.ContainerRefinery;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import slimevoid.tmf.tools.inventory.ContainerMiningToolBelt;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements ITMFCommonProxy {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
			case GuiLib.TOOL_BELT_GUIID :
				MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(
						player,
						world,
						player.getHeldItem()
				);
				return new ContainerMiningToolBelt(
						player.inventory,
						data
				);
			case GuiLib.REFINERY_GUIID :
				TileEntity tile1 = world.getBlockTileEntity(x, y, z);
				if ( tile1 instanceof TileEntityRefinery ) {
					TileEntityRefinery tileRefinery = (TileEntityRefinery) tile1;
					return new ContainerRefinery(
							player.inventory,
							tileRefinery
					);
				}
				return null;
			case GuiLib.GRINDER_GUIID :
				TileEntity tile2 = world.getBlockTileEntity(x, y, z);
				if ( tile2 instanceof TileEntityGrinder ) {
					TileEntityGrinder tileGrinder = (TileEntityGrinder) tile2;
					return new ContainerGrinder(
							player.inventory,
							tileGrinder
					);
				}
				return null;
			case GuiLib.GEOEQUIP_GUIID :
				TileEntity tileGeo = world.getBlockTileEntity(x, y, z);
				if ( tileGeo instanceof TileEntityGeologicalEquipment ) {
					TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) tileGeo;
					return new ContainerGeologicalEquipment(
							player.inventory,
							tileGeoEquip
					);
				}
				return null;
			default : return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
			case GuiLib.TOOL_BELT_GUIID :
				MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(
						player,
						world,
						player.getHeldItem()
				);
				return new GuiMiningToolBelt(
						player,
						data
				);
			case GuiLib.REFINERY_GUIID :
				TileEntity tile1 = world.getBlockTileEntity(x, y, z);
				if ( tile1 instanceof TileEntityRefinery ) {
					TileEntityRefinery tileRefinery = (TileEntityRefinery) tile1;
					return new GuiRefinery(
							player,
							tileRefinery
					);
				}
				return null;
			case GuiLib.GRINDER_GUIID :
				TileEntity tile2 = world.getBlockTileEntity(x, y, z);
				if ( tile2 instanceof TileEntityGrinder ) {
					TileEntityGrinder tileGrinder = (TileEntityGrinder) tile2;
					return new GuiGrinder(
							player,
							tileGrinder
					);
				}
				return null;
			case GuiLib.GEOEQUIP_GUIID :
				TileEntity tileGeo = world.getBlockTileEntity(x, y, z);
				if ( tileGeo instanceof TileEntityGeologicalEquipment ) {
					TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) tileGeo;
					return new GuiGeologicalEquipment(
							player,
							tileGeoEquip
					);
				}
				return null;
			default : return null;
		}
	}

	@Override
	public void preInit() {		
        NetworkRegistry.instance().registerGuiHandler(TheMinersFriend.instance, this);
        
        PacketLib.registerPacketExecutors();
		
		EventLib.registerCommonEvents();
		
		MiningMode.init(DataLib.MINING_MODE_STRENGTH);
	}

	@Override
	public void registerConfigurationProperties() {
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Loading properties...");
		ConfigurationLib.CommonConfig();
	}

	@Override
	public void registerTickHandler() {
		TickRegistry.registerTickHandler(new MiningHelmetTickHandler(), Side.SERVER);
	}

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public String getMinecraftDir() {
		return "./";
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return 0;
	}

	@Override
	public int getBlockTextureFromMetadata(int meta) {
		return 0;
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
	}

	@Override
	public IPacketHandling getPacketHandler() {
		return null;
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
	}

	@Override
	public void displayTileEntityGui(EntityPlayer entityplayer,
			TileEntity tileentity) {
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public World getWorld(NetHandler handler) {
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager,
			Packet1Login login) {
	}
}
