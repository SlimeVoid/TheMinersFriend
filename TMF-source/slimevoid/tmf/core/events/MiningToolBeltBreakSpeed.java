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
package slimevoid.tmf.core.events;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;

public class MiningToolBeltBreakSpeed {

	@ForgeSubscribe
	public void onBreakSpeed(BreakSpeed event) {
		// if (!event.entityPlayer.worldObj.isRemote) {
		ItemMiningToolBelt.doBreakSpeed(event);
		// }
	}
}
