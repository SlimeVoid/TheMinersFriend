package slimevoid.tmf.core;

import slimevoid.lib.ICommonProxy;
import slimevoid.tmf.client.network.ClientPacketHandler;
import slimevoid.tmf.network.CommonPacketHandler;
import slimevoid.tmf.network.TMFConnectionHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(
		modid = "TheMinersFriend",
		name = "The Miner's Friend",
		version = "1.0.0.0",
		dependencies = "after:SlimevoidLib")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		clientPacketHandlerSpec = @SidedPacketHandler(
				channels = { TMFCore.packetChannel },
				packetHandler = ClientPacketHandler.class),
		serverPacketHandlerSpec = @SidedPacketHandler(
				channels = { TMFCore.packetChannel },
				packetHandler = CommonPacketHandler.class),
		connectionHandler = TMFConnectionHandler.class)
public class TheMinersFriend {
	@SidedProxy(
			clientSide = "slimevoid.tmf.client.proxy.ClientProxy",
			serverSide = "slimevoid.tmf.proxy.CommonProxy")
	public static ICommonProxy proxy;
	
	@Instance("TheMinersFriend")
	public static TheMinersFriend instance;

	@PreInit
	public void TheMinersFriendPreInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}

	@Init
	public void TheMinersFriendInit(FMLInitializationEvent event) {
		TMFCore.initialize(proxy);
	}

	@PostInit
	public void TheMinersFriendPostInit(FMLPostInitializationEvent event) {
	}
}