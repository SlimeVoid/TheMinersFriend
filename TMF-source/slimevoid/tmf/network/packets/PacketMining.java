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
package slimevoid.tmf.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import slimevoid.lib.network.PacketPayload;
import slimevoid.lib.network.PacketUpdate;
import slimevoid.tmf.core.lib.ReferenceLib;

/**
 * Extend for new packets
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketMining extends PacketUpdate {

	private String command;

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeUTF(this.command);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.command = data.readUTF();
	}

	/**
	 * Constructor for Default Mining Packets
	 * 
	 * @param packetId the packet ID used to identify the type of packet data
	 *            being sent or received
	 */
	public PacketMining(int packetId) {
		super(packetId);
		this.setChannel(ReferenceLib.MOD_CHANNEL);
	}

	/**
	 * Constructor for Default Mining Packets Used to add payload data to the
	 * packet
	 * 
	 * @param packetId the packet ID used to identify the type of packet data
	 *            being sent or received
	 * @param payload the new payload to be associated with the packet
	 */
	public PacketMining(int packetId, PacketPayload payload) {
		super(packetId, payload);
		this.setChannel(ReferenceLib.MOD_CHANNEL);
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + "," + zPosition + ")";
	}

	/**
	 * Retrieves the command String corresponding to the executor
	 * 
	 * @return Returns command
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * Sets the command in the packet
	 * 
	 * @param command The command to be added
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
