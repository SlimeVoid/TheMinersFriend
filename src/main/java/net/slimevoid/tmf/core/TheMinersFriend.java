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

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.slimevoid.library.ICommonProxy;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.tmf.core.lib.CoreLib;
import net.slimevoid.tmf.core.lib.PacketLib;

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

    @Mod.Instance(CoreLib.MOD_ID)
    public static TheMinersFriend instance;

    @Mod.EventHandler
    public void TheMinersFriendPreInit(FMLPreInitializationEvent event) {
        proxy.registerConfigurationProperties(event.getSuggestedConfigurationFile());
        TMFCore.preInitialize();
    }

    @Mod.EventHandler
    public void TheMinersFriendInit(FMLInitializationEvent event) {
        TMFCore.initialize();
        proxy.registerPacketHandlers();
    }

    @Mod.EventHandler
    public void TheMinersFriendPostInit(FMLPostInitializationEvent event) {
        TMFCore.postInitialize();
    }
}