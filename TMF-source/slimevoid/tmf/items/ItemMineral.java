package slimevoid.tmf.items;

import slimevoid.tmf.core.lib.SpriteLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMineral extends Item {
	private int burnTime;

	public ItemMineral(int id) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return SpriteLib.RESOURCE_SPRITE_PATH;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public Item setBurnTime(int burnTime) {
		this.burnTime = burnTime;
		return this;
	}
}
