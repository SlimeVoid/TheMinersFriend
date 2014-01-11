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

import net.minecraft.world.World;
import slimevoid.tmf.core.lib.PacketLib;

public class PacketMiningToolBelt extends PacketMining {

	public PacketMiningToolBelt() {
		super(PacketLib.MINING_TOOL_BELT);
	}

	public PacketMiningToolBelt(String command) {
		this();
		this.setCommand(command);
		// this.payload = new PacketPayload(2, 0, 0, 0);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

}
