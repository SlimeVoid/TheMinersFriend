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
package slimevoid.tmf.tools.items;

import net.minecraft.item.Item;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.ResourceLib;

public class ItemMiningLamp extends Item {

	public ItemMiningLamp(int itemID) {
		super(itemID - ConfigurationLib.ITEMID_OFFSET);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

}
