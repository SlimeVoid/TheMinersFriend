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
import slimevoid.tmf.blocks.machines.inventory.ContainerRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.client.gui.GuiMiningToolBelt;
import slimevoid.tmf.client.gui.GuiRefinery;
import slimevoid.tmf.core.TMFInit;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.items.inventory.ContainerMiningToolBelt;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
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
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if ( tile instanceof TileEntityRefinery ) {
					TileEntityRefinery tileRefinery = (TileEntityRefinery) tile;
					return new ContainerRefinery(
							player.inventory,
							tileRefinery
					);
				}
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
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if ( tile instanceof TileEntityRefinery ) {
					TileEntityRefinery tileRefinery = (TileEntityRefinery) tile;
					return new GuiRefinery(
							player,
							tileRefinery
					);
				}
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
		SlimevoidCore.console(TMFInit.TMF.getModName(), "Loading properties...");
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
