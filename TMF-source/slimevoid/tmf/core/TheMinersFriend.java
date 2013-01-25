package slimevoid.tmf.core;

import slimevoid.tmf.api.ITMFCommonProxy;
import slimevoid.tmf.core.lib.ReferenceLib;
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
		modid = ReferenceLib.MOD_ID,
		name = ReferenceLib.MOD_NAME,
		version = ReferenceLib.MOD_VERSION,
		dependencies = ReferenceLib.MOD_DEPENDENCIES)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		clientPacketHandlerSpec = @SidedPacketHandler(
				channels = { ReferenceLib.MOD_CHANNEL },
				packetHandler = ClientPacketHandler.class),
		serverPacketHandlerSpec = @SidedPacketHandler(
				channels = { ReferenceLib.MOD_CHANNEL },
				packetHandler = CommonPacketHandler.class),
		connectionHandler = TMFConnectionHandler.class)
public class TheMinersFriend {
	@SidedProxy(
			clientSide = ReferenceLib.PROXY_CLIENT,
			serverSide = ReferenceLib.PROXY_COMMON)
	public static ITMFCommonProxy proxy;
	
	@Instance(ReferenceLib.MOD_ID)
	public static TheMinersFriend instance;

	@PreInit
	public void TheMinersFriendPreInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}

	@Init
	public void TheMinersFriendInit(FMLInitializationEvent event) {
		TMFInit.initialize(proxy);
	}

	@PostInit
	public void TheMinersFriendPostInit(FMLPostInitializationEvent event) {
	}
}