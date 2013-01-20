package slimevoid.tmf.items;

import slimevoid.tmf.core.lib.ItemLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMineral extends Item {

	public ItemMineral(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return ItemLib.RESOURCE_SPRITE_PATH;
	}
}
