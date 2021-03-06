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
package net.slimevoid.tmf.core;

import net.slimevoid.library.ICommonProxy;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.tmf.core.lib.CoreLib;
import net.slimevoid.tmf.core.lib.PacketLib;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = CoreLib.MOD_ID,
        name = CoreLib.MOD_NAME,
        version = CoreLib.MOD_VERSION,
        dependencies = CoreLib.MOD_DEPENDENCIES)
public class TheMinersFriend {
    @SidedProxy(
            clientSide = CoreLib.PROXY_CLIENT,
            serverSide = CoreLib.PROXY_COMMON)
    public static ICommonProxy    proxy;

    @Instance(CoreLib.MOD_ID)
    public static TheMinersFriend instance;

    @EventHandler
    public void TheMinersFriendPreInit(FMLPreInitializationEvent event) {
        proxy.registerConfigurationProperties(event.getSuggestedConfigurationFile());
        proxy.preInit();
        TMFInit.preInitialize();
    }

    @EventHandler
    public void TheMinersFriendInit(FMLInitializationEvent event) {
        proxy.init();

        PacketHelper.registerHandler(CoreLib.MOD_CHANNEL,
                                     PacketLib.handler);
        TMFInit.initialize();
    }

    @EventHandler
    public void TheMinersFriendPostInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        TMFInit.postInitialize();
    }
}