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
package slimevoid.tmf.client.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import slimevoid.tmf.core.LoggerTMF;
import slimevoidlib.data.Logger;
import slimevoidlib.network.handlers.SubPacketHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {

	private static Map<Integer, SubPacketHandler>	clientHandlers;

	public static void init() {
		clientHandlers = new HashMap<Integer, SubPacketHandler>();
	}

	public static void registerPacketHandler(int packetID, SubPacketHandler handler) {
		if (clientHandlers.containsKey(packetID)) {
			LoggerTMF.getInstance(Logger.filterClassName(ClientPacketHandler.class.toString())).write(	false,
																										"PacketID ["
																												+ packetID
																												+ "] already registered.",
																										Logger.LogLevel.ERROR);
			throw new RuntimeException("PacketID [" + packetID
										+ "] already registered.");
		}
		clientHandlers.put(	packetID,
							handler);
	}

	/**
	 * Retrieves the registered sub-handler from the server side list
	 * 
	 * @param packetID
	 * @return the sub-handler
	 */
	public static SubPacketHandler getPacketHandler(int packetID) {
		if (!clientHandlers.containsKey(packetID)) {
			LoggerTMF.getInstance(Logger.filterClassName(ClientPacketHandler.class.toString())).write(	false,
																										"Tried to get a Packet Handler for ID: "
																												+ packetID
																												+ " that has not been registered.",
																										Logger.LogLevel.WARNING);
			throw new RuntimeException("Tried to get a Packet Handler for ID: "
										+ packetID
										+ " that has not been registered.");
		}
		return clientHandlers.get(packetID);
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			getPacketHandler(packetID).onPacketData(manager,
													packet,
													player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
