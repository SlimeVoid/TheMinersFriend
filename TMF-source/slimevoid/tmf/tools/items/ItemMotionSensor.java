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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.ResourceLib;

public class ItemMotionSensor extends Item {

	public ItemMotionSensor(int itemID) {
		super(itemID - ConfigurationLib.ITEMID_OFFSET);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		return itemstack;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return ResourceLib.ITEM_SPRITE_PATH;
	}
}
