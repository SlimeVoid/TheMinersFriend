package slimevoid.tmf.items.minerals;

import net.minecraft.item.Item;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;

public class ItemMineralIngot extends Item {

	public ItemMineralIngot(int id) {
		super(id);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

}
