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
package slimevoid.tmf.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoidlib.ICommonProxy;

public interface ITMFCommonProxy extends ICommonProxy {

	public void registerTESRenderers();

	/**
	 * Used to facade client only methods
	 * 
	 * @return
	 */
	public EntityPlayer getPlayer();

	/**
	 * Used to facade client only methods
	 * 
	 * @return
	 */
	public World getWorld();
}
