package slimevoid.tmf.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.lib.ItemLib;

public class ItemMotionSensor extends Item {

	public ItemMotionSensor(int itemId) {
		super(itemId);
		this.setCreativeTab(CreativeTabs.tabTools);
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		System.out.println("Being Used");
		return itemstack;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return ItemLib.ITEM_SPRITE_PATH;
	}
}
