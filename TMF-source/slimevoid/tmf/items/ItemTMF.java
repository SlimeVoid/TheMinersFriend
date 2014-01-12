package slimevoid.tmf.items;

import net.minecraft.item.Item;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;

public class ItemTMF extends Item {

	public ItemTMF(int id) {
		super(id);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

}
