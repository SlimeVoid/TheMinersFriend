package slimevoid.tmf.tools.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.ResourceLib;

public class ItemMotionSensor extends Item {

	public ItemMotionSensor(int itemID) {
		super(itemID - ConfigurationLib.ITEMID_OFFSET);
		this.setCreativeTab(CreativeTabTMF.tabTMF);
		this.setMaxStackSize(1);
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
		return ResourceLib.ITEM_SPRITE_PATH;
	}
}
