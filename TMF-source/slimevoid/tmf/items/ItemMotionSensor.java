package slimevoid.tmf.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMotionSensor extends Item {

	public ItemMotionSensor(int itemId) {
		super(itemId);
		this.setCreativeTab(CreativeTabs.tabTools);
		maxStackSize = 1;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return "/TheMinersFriend/gui/items.png";
	}
}
