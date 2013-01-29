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
