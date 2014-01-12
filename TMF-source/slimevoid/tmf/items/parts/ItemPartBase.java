package slimevoid.tmf.items.parts;

import slimevoid.tmf.items.ItemTMF;

public class ItemPartBase extends ItemTMF {

	public ItemPartBase(int itemId) {
		super(itemId);
		this.setNoRepair();
	}

}
