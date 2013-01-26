package slimevoid.tmf.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.core.SlimevoidCore;
import slimevoid.tmf.api.ITMFCommonProxy;
import slimevoid.tmf.core.lib.ReferenceLib;

public class TMFInit {
	private static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		TMFCore.configFile = new File(
				TheMinersFriend.proxy.getMinecraftDir(),
					"config/TheMinersFriend.cfg");
		TMFCore.configuration = new Configuration(TMFCore.configFile);
		load();
	}

	public static void load() {
		((ITMFCommonProxy)TheMinersFriend.proxy).registerConfigurationProperties();
		
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering items...");
		TMFCore.registerItems();

		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering blocks...");
		TMFCore.registerBlocks();
		
		TheMinersFriend.proxy.registerRenderInformation();
		
		TheMinersFriend.proxy.registerTickHandler();
		
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering names...");
		TMFCore.registerNames();
		
		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering recipes...");
		TMFCore.registerRecipes();

		SlimevoidCore.console(ReferenceLib.MOD_ID, "Registering fuels...");
		TMFCore.registerFuels();
		
		TheMinersFriend.proxy.registerTESRenderers();
	}
}
