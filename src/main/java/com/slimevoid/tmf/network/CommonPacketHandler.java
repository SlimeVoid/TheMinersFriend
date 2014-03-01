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
package com.slimevoid.tmf.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import com.slimevoid.library.data.Logger;
import com.slimevoid.library.network.handlers.SubPacketHandler;
import com.slimevoid.tmf.core.LoggerTMF;
import com.slimevoid.tmf.core.lib.CoreLib;
import com.slimevoid.tmf.core.lib.PacketLib;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class CommonPacketHandler implements IPacketHandler {

    private static Map<Integer, SubPacketHandler> commonHandlers;

    /**
     * Initializes the commonHandler Map
     */
    public static void init() {
        commonHandlers = new HashMap<Integer, SubPacketHandler>();
    }

    /**
     * Register a sub-handler with the server-side packet handler.
     * 
     * @param packetID
     *            Packet ID for the sub-handler to handle.
     * @param handler
     *            The sub-handler.
     */
    public static void registerPacketHandler(int packetID, SubPacketHandler handler) {
        if (commonHandlers.containsKey(packetID)) {
            LoggerTMF.getInstance(Logger.filterClassName(CommonPacketHandler.class.toString())).write(false,
                                                                                                      "PacketID ["
                                                                                                              + packetID
                                                                                                              + "] already registered.",
                                                                                                      Logger.LogLevel.ERROR);
            throw new RuntimeException("PacketID [" + packetID
                                       + "] already registered.");
        }
        commonHandlers.put(packetID,
                           handler);
    }

    /**
     * Retrieves the registered sub-handler from the server side list
     * 
     * @param packetID
     * @return the sub-handler
     */
    public static SubPacketHandler getPacketHandler(int packetID) {
        if (!commonHandlers.containsKey(packetID)) {
            LoggerTMF.getInstance(Logger.filterClassName(CommonPacketHandler.class.toString())).write(false,
                                                                                                      "Tried to get a Packet Handler for ID: "
                                                                                                              + packetID
                                                                                                              + " that has not been registered.",
                                                                                                      Logger.LogLevel.WARNING);
            throw new RuntimeException("Tried to get a Packet Handler for ID: "
                                       + packetID
                                       + " that has not been registered.");
        }
        return commonHandlers.get(packetID);
    }

    /**
     * The server-side packet handler receives a packet.<br>
     * Fetches the packet ID and routes it on to sub-handlers.
     */
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            if (packet.channel.equals(CoreLib.MOD_CHANNEL)) {
                int packetID = data.read();
                getPacketHandler(packetID).onPacketData(manager,
                                                        packet,
                                                        player);
            } else {
                PacketLib.tryAlternativeHandling(manager,
                                                 packet,
                                                 player);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
