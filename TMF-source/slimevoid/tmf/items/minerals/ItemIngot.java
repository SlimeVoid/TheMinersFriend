package slimevoid.tmf.items.minerals;

import net.minecraft.item.Item;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;

public class ItemIngot extends Item {

	public ItemIngot(int id) {
		super(id);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

}
