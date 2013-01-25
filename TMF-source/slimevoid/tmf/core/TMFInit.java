package slimevoid.tmf.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.ICore;
import slimevoid.lib.core.Core;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.api.ITMFCommonProxy;

public class TMFInit {
	public static ICore TMF;
	private static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		TMF = new Core(proxy);
		TMF.setModName("TheMinersFriend");
		TMF.setModChannel(TMFCore.packetChannel);
		TMFCore.configFile = new File(
				TMF.getProxy().getMinecraftDir(),
					"config/TheMinersFriend.cfg");
		TMFCore.configuration = new Configuration(TMFCore.configFile);
		load();
	}

	public static void load() {
		((ITMFCommonProxy)TMF.getProxy()).registerConfigurationProperties();
		
		SlimevoidCore.console(TMF.getModName(), "Registering items...");
		TMFCore.registerItems();

		SlimevoidCore.console(TMF.getModName(), "Registering blocks...");
		TMFCore.registerBlocks();
		
		TMF.getProxy().registerRenderInformation();
		
		TMF.getProxy().registerTickHandler();
		
		SlimevoidCore.console(TMF.getModName(), "Registering names...");
		TMFCore.registerNames();
		
		SlimevoidCore.console(TMF.getModName(), "Registering recipes...");
		TMFCore.registerRecipes();

		SlimevoidCore.console(TMF.getModName(), "Registering fuels...");
		TMFCore.registerFuels();
	}
}
