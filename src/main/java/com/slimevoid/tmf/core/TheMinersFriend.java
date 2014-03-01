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
package com.slimevoid.tmf.core;

import com.slimevoid.compatibility.thaumcraft.Thaumcraft;
import com.slimevoid.library.ICommonProxy;
import com.slimevoid.tmf.client.network.ClientPacketHandler;
import com.slimevoid.tmf.core.lib.CoreLib;
import com.slimevoid.tmf.network.CommonPacketHandler;
import com.slimevoid.tmf.network.TMFConnectionHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(
        modid = CoreLib.MOD_ID,
        name = CoreLib.MOD_NAME,
        version = CoreLib.MOD_VERSION,
        dependencies = CoreLib.MOD_DEPENDENCIES)
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = false,
        clientPacketHandlerSpec = @SidedPacketHandler(
                channels = { CoreLib.MOD_CHANNEL },
                packetHandler = ClientPacketHandler.class),
        serverPacketHandlerSpec = @SidedPacketHandler(
                channels = { CoreLib.MOD_CHANNEL, Thaumcraft.MOD_CHANNEL },
                packetHandler = CommonPacketHandler.class),
        connectionHandler = TMFConnectionHandler.class)
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
    }

    @EventHandler
    public void TheMinersFriendInit(FMLInitializationEvent event) {
        TMFInit.initialize(proxy);
        proxy.registerEventHandlers();
    }

    @EventHandler
    public void TheMinersFriendPostInit(FMLPostInitializationEvent event) {
    }
}