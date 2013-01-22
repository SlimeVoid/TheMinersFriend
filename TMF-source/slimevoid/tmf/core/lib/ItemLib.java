package slimevoid.tmf.core.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.items.ItemMiningToolBelt;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ItemLib {
	/**
	 * Check if a player is holding or using a parsed tool class and return for convenience
	 * 
	 * @param entityplayer the Player to check
	 * @param world the World of the Player
	 * @param itemClass the Class of Item to check for
	 * @return the Held Item or null if the check was unsuccessfull
	 */
	private static ItemStack playerIsHoldingOrUsingTool(
			EntityPlayer entityplayer, World world,
			Class<? extends Item> itemClass) {
		if (entityplayer.getHeldItem() != null && entityplayer.getHeldItem().getItem() != null && itemClass.isInstance(entityplayer.getHeldItem().getItem())) {
			return entityplayer.getHeldItem();
		}
		return null;
	}

	/**
	 * Check if a player is carrying a certain tool type and return the list for convenience
	 * 
	 * @param entityplayer the Player to check
	 * @param world the World of the Player
	 * @param itemClass the Class of Item to check for
	 * @return the List of tools
	 */
	private static List<ItemStack> playerHasTools(EntityPlayer entityplayer,
			World world, Class<? extends Item> itemClass) {
		List<ItemStack> tools = new ArrayList<ItemStack>();
		IInventory playerInventory = entityplayer.inventory;
		for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
			ItemStack itemstack = playerInventory.getStackInSlot(slot);
			if (itemstack != null && itemstack.getItem() != null && itemClass.isInstance(itemstack.getItem())) {
				tools.add(itemstack);
			}
		}
		return tools;
	}
	
	/**
	 * Check if a player has any Tool Belts in their inventory and return the List for convenience
	 * 
	 * @param entityplayer the Player to check
	 * @param world the World of the Player
	 * @return the List of Tool Belts (if any)
	 */
	public static List<ItemStack> getToolBelts(EntityPlayer entityplayer, World world) {
		return playerHasTools(entityplayer, world, ItemMiningToolBelt.class);
	}
	
	/**
	 * Check if a Player has a Tool Belt and return for convenience
	 * 
	 * @param entityplayer the Player to check
	 * @param world the World of the Player
	 * @param isHeld whether we're checking for a held Tool Belt (Should be true)
	 * @return
	 */
	public static ItemStack getToolBelt(EntityPlayer entityplayer, World world, boolean isHeld) {
		return isHeld ?
				playerIsHoldingOrUsingTool(entityplayer, world, ItemMiningToolBelt.class) :
				null;
	}

	/**
	 * Perform the check for Tool Belts (on player login)
	 * 
	 * @param player the Player to check
	 */
	public static void checkForToolBelts(Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		// For every Tool Belt the Player has in their inventory 
		for (ItemStack toolBelt : getToolBelts(entityplayer, entityplayer.worldObj)) {
			// Retrieve the Tool Belt Data
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, entityplayer.worldObj, toolBelt);
			if (data != null) {
				// If Data Exists Send the Data to the Player to Update their inventory
				PacketDispatcher.sendPacketToPlayer(data.createPacket().getPacket(), player);
			}
		}
	}

	/**
	 * Checks whether a given ItemStack is a Tool belt
	 * 
	 * @param itemstack the ItemStack to check
	 * @return true or false
	 */
	public static boolean isToolBelt(ItemStack itemstack) {
		return (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemMiningToolBelt);
	}

	public static ItemStack getSelectedTool(EntityLiving entityliving,
			World worldObj, ItemStack itemstack) {
		// Check that the current itemstack is a Tool Belt
		if (isToolBelt(itemstack)) {
			// Retrieve the data for the itemstack
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityliving, entityliving.worldObj, itemstack);
			// Retrieve the selected tool
			ItemStack selectedTool = data.getSelectedTool();
			// If there is a tool in the selected slot
			if (selectedTool != null) {
				// Perform the onBlockDestroyed using that Tool
				return selectedTool;
			}
		}
		return null;
	}
}
