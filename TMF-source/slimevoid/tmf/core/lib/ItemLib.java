package slimevoid.tmf.core.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.items.ItemMiningToolbelt;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

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

	private static List<ItemStack> playerHasTools(EntityPlayer entityplayer,
			World world, Class<? extends Item> itemClass) {
		List<ItemStack> tools = new ArrayList();
		IInventory playerInventory = entityplayer.inventory;
		for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
			ItemStack itemstack = playerInventory.getStackInSlot(slot);
			if (itemstack != null && itemstack.getItem() != null && itemClass.isInstance(itemstack.getItem())) {
				tools.add(itemstack);
			}
		}
		return tools;
	}
	
	public static List<ItemStack> getToolBelts(EntityPlayer entityplayer, World world) {
		return playerHasTools(entityplayer, world, ItemMiningToolbelt.class);
	}
	
	public static ItemStack getToolBelt(EntityPlayer entityplayer, World world, boolean isHeld) {
		return isHeld ?
				playerIsHoldingOrUsingTool(entityplayer, world, ItemMiningToolbelt.class) :
				null;
	}

	public static void checkForToolBelt(Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		for (ItemStack toolBelt : getToolBelts(entityplayer, entityplayer.worldObj)) {
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, entityplayer.worldObj, toolBelt);
			if (data != null) {
				PacketDispatcher.sendPacketToPlayer(data.createPacket().getPacket(), player);
			}
		}
	}
}
