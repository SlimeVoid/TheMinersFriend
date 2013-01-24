package slimevoid.tmf.tools.items;

import net.minecraft.item.Item;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.SpriteLib;

public class ItemMiningLamp extends Item {

	public ItemMiningLamp(int itemID) {
		super(itemID - ConfigurationLib.ITEMID_OFFSET);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}
	
	@Override
	public String getTextureFile() {
		return SpriteLib.ITEM_SPRITE_PATH;
	}

}
