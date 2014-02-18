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

import slimevoid.compatibility.TMFCompatibility;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoidlib.ICommonProxy;
import slimevoidlib.core.SlimevoidCore;

public class TMFInit {
    private static boolean initialized = false;

    public static void initialize(ICommonProxy proxy) {
        if (initialized) return;
        initialized = true;
        load();
    }

    public static void load() {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering Miner's Tool Belt...");
        TMFCore.registerToolBelt();

        if (ConfigurationLib.loadItems) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Registering items...");
            TMFCore.registerItems();
        }

        if (ConfigurationLib.loadOres) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Registering ores...");
            TMFCore.registerOres();
        }

        if (ConfigurationLib.loadMachines) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Registering machines...");
            TMFCore.registerMachines();
        }

        TheMinersFriend.proxy.registerRenderInformation();

        TheMinersFriend.proxy.registerTickHandlers();

        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering Compatibility Blocks and Items...");

        TMFCompatibility.registerBlockAndItemInformation();

        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering names...");
        TMFCore.registerNames();

        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering XML variables...");

        ConfigurationLib.loadXMLVariables();

        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering recipes...");
        TMFCore.registerRecipes();

        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering fuels...");
        TMFCore.registerFuels();
    }
}
