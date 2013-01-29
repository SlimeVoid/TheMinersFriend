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

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ArmorLib;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.ResourceLib;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMiningHelmet extends ItemArmor implements IArmorTextureProvider {

	public ItemMiningHelmet(int itemID, EnumArmorMaterial material,
			int renderIndex, int armorType) {
		super(itemID - ConfigurationLib.ITEMID_OFFSET, material, renderIndex, armorType);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getIconFromDamageForRenderPass(int damage, int renderPass) {
		return renderPass == 1 ? this.iconIndex : super
				.getIconFromDamageForRenderPass(damage, renderPass);
	}

	@Override
	public int getIconFromDamage(int par1) {
		return this.iconIndex;
	}
	
	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return ArmorLib.getArmorTextureFromItemStack(itemstack);
	}

	@Override
	public String getTextureFile() {
		return ResourceLib.ITEM_SPRITE_PATH;
	}
}
