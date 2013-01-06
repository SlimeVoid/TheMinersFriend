package slimevoid.tmf.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import eurysmods.api.ICommonProxy;
import eurysmods.api.ICore;
import eurysmods.core.Core;
import eurysmods.core.EurysCore;

public class TMFInit {
	public static ICore TMF;
	private static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		TMF = new Core(proxy);
		TMF.setModName("TheMinersFriend");
		TMF.setModChannel("THEMINERSFRIEND");
		TMFCore.configFile = new File(
				TMF.getProxy().getMinecraftDir(),
					"config/TheMinersFriend.cfg");
		TMFCore.configuration = new Configuration(TMFCore.configFile);
		load();
	}

	public static void load() {
		TMF.getProxy().preInit();
		EurysCore.console(TMF.getModName(), "Registering items...");
		TMFCore.addItems();
		TMF.getProxy().registerRenderInformation();
		TMF.getProxy().registerTickHandler();
		EurysCore.console(TMF.getModName(), "Naming items...");
		TMFCore.addNames();
		EurysCore.console(TMF.getModName(), "Registering recipes...");
		TMFCore.addRecipes();
		EurysCore.console(TMF.getModName(), "Registering handlers...");
		TMFCore.registerHandlers();
		
	}
}
