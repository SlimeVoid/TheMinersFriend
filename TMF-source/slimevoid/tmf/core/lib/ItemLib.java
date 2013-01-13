package slimevoid.tmf.core.lib;

import slimevoid.tmf.items.ItemMiningToolbelt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLib {
	public final static String ITEM_SPRITE_PATH = "/TheMinersFriend/gui/items.png";

	private static ItemStack playerIsHoldingOrUsingTool(
			EntityPlayer entityplayer, World world,
			Class<? extends Item> itemClass) {
		if (entityplayer.getHeldItem() != null && entityplayer.getHeldItem().getItem() != null && itemClass.isInstance(entityplayer.getHeldItem().getItem())) {
			return entityplayer.getHeldItem();
		}
		return null;
	}
	
	public static ItemStack getToolBelt(EntityPlayer entityplayer, World world) {
		return playerIsHoldingOrUsingTool(entityplayer, world, ItemMiningToolbelt.class);
	}
}
