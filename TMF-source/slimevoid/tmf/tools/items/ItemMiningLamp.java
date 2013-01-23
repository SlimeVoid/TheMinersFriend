package slimevoid.tmf.tools.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import slimevoid.tmf.core.lib.SpriteLib;

public class ItemMiningLamp extends Item {

	public ItemMiningLamp(int itemId) {
		super(itemId);
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public String getTextureFile() {
		return SpriteLib.ITEM_SPRITE_PATH;
	}

}
