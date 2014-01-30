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
package slimevoid.tmf.items.minerals;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.items.ItemTMF;

public class ItemMineral extends ItemTMF implements IFuelHandlerTMF {
	private int	burnTime	= 1600;
	private int	burnSpeed	= 200;
	private int	burnWidth	= 1;

	public ItemMineral(int id) {
		super(id);
	}

	public Item setBurnSettings(int time, int speed, int width) {
		setBurnTime(time);
		setBurnSpeed(speed);
		setBurnWidth(width);
		return this;
	}

	@Override
	public int getBurnTime(ItemStack stack) {
		return burnTime;
	}

	public Item setBurnTime(int burnTime) {
		this.burnTime = burnTime;
		return this;
	}

	@Override
	public int getBurnSpeed(ItemStack stack) {
		return burnSpeed;
	}

	public Item setBurnSpeed(int burnSpeed) {
		this.burnSpeed = burnSpeed;
		return this;
	}

	@Override
	public int getBurnWidth(ItemStack stack) {
		return burnWidth;
	}

	public Item setBurnWidth(int burnWidth) {
		this.burnWidth = burnWidth;
		return this;
	}
}
