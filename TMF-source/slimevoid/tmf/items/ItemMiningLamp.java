package slimevoid.tmf.items;

import slimevoid.tmf.core.lib.ItemLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMiningLamp extends Item {

	public ItemMiningLamp(int itemId) {
		super(itemId);
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public String getTextureFile() {
		return ItemLib.ITEM_SPRITE_PATH;
	}

}
