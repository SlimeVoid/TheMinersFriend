package slimevoid.tmf.core;

import java.io.File;

import eurysmods.api.ICommonProxy;

import net.minecraftforge.common.Configuration;

public class TMFCore {
	public static File configFile;
	public static Configuration configuration;
	public static String loggerLevel = "INFO";

	public static void initialize(ICommonProxy proxy) {
		TMFInit.initialize(proxy);
	}

	public static void addItems() {
	}

	public static void addNames() {
	}

	public static void addRecipes() {
	}

	public static int configurationProperties() {
		configuration.load();
		loggerLevel = String.valueOf(configuration.get(
				Configuration.CATEGORY_GENERAL,
				"loggerLevel",
				"INFO").value);
		configuration.save();
		LoggerTMF.getInstance("LittleBlocksConfig").setFilterLevel(loggerLevel);
		return 0;
	}
}
