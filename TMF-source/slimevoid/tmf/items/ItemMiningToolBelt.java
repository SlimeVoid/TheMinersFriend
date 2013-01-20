package slimevoid.tmf.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.ItemLib;
import slimevoid.tmf.core.lib.SpriteLib;

public class ItemMiningToolBelt extends Item {

	public ItemMiningToolBelt(int itemID) {
		super(itemID);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
		if (data != null) {
			if (entityplayer.isSneaking()) {
				ItemStack tool = data.getSelectedTool();
				if (tool != null) {
					if (!world.isRemote && this.tryUseItem(data, entityplayer, world, tool)) {
						return itemstack;
					}
					//tool.useItemRightClick(world, entityplayer);
				}
			} else {
				// If Tool Belt data exists then Open the Tool Belt GUI
				entityplayer.openGui(
						TheMinersFriend.instance,
						GuiLib.TOOL_BELT_GUIID,
						world,
						(int)entityplayer.posX,
						(int)entityplayer.posY,
						(int)entityplayer.posZ);
			}
		}
		return itemstack;
	}

	private boolean tryUseItem(MiningToolBelt data, EntityPlayer entityplayer, World world,
			ItemStack itemstack) {
		EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
		int stacksize = itemstack.stackSize;
		int itemdamage = itemstack.getItemDamage();
		ItemStack itemRightClicked = itemstack.useItemRightClick(world,
				entityplayer);

		if (itemRightClicked == itemstack
				&& (itemRightClicked == null || itemRightClicked.stackSize == stacksize
						&& itemRightClicked.getMaxItemUseDuration() <= 0
						&& itemRightClicked.getItemDamage() == itemdamage)) {
			return false;
		} else {
			if (entityplayermp.theItemInWorldManager.isCreative()) {
				itemRightClicked.stackSize = stacksize;

				if (itemRightClicked.isItemStackDamageable()) {
					itemRightClicked.setItemDamage(itemdamage);
				}
			}

			if (itemRightClicked.stackSize == 0) {
				data.setInventorySlotContents(data.getSelectedSlot(), null);
			}

			if (!entityplayer.isUsingItem()) {
				entityplayermp.sendContainerToPlayer(entityplayer.inventoryContainer);
			}

			return true;
		}
	}
	
/*	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
			if (data != null) {
				if (entityplayer.isSneaking()) {
					ItemStack tool = data.getSelectedTool();
					if (tool != null) {
						if (entityplayer instanceof EntityPlayerMP) {
							EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
							ItemInWorldManager itemManager = entityplayermp.theItemInWorldManager;
							if (itemManager.activateBlockOrUseItem(entityplayer, world, tool, x, y, z, side, hitX, hitY, hitZ)) {
								return true;
							} else { 
								itemManager.tryUseItem(entityplayer, world, tool);
								return true;
							}
						}
					}
				} else {
					this.onItemRightClick(itemstack, world, entityplayer);
				}
			}
		}
		return true;
	}*/

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving) {
		return doDestroyBlock(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving, super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving));
	}
	
	@Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer entityplayer) {
		return doStartBreakBlock(itemstack, x, y, z, entityplayer, super.onBlockStartBreak(itemstack, x, y, z, entityplayer));
	}

	private boolean doStartBreakBlock(
			ItemStack itemstack,
			int x,
			int y,
			int z,
			EntityPlayer entityplayer,
			boolean onBlockStartBreak) {
		// Check that the current itemstack is a Tool Belt
		if (ItemLib.isToolBelt(itemstack)) {
			// Retrieve the data for the itemstack
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, entityplayer.worldObj, itemstack);
			// Retrieve the selected tool
			ItemStack selectedTool = data.getSelectedTool();
			// If there is a tool in the selected slot
			if (selectedTool != null) {
				System.out.println("doStartBreakBlock");
				// Perform the onBlockDestroyed using that Tool
				return selectedTool.getItem().onBlockStartBreak(selectedTool, x, y, z, entityplayer);
			}
		}
		return onBlockStartBreak;
	}

	/**
	 * Attempts to destroy the block using the selected item in the Tool Belt
	 * 
	 * @param itemstack the Held Item
	 * @param world the World
	 * @param x
	 * @param y
	 * @param z
	 * @param side
	 * @param entityliving (Usually the player)
	 * @param onBlockDestroyed the current result
	 * @return
	 */
	public static boolean doDestroyBlock(
			ItemStack itemstack,
			World world,
			int x,
			int y,
			int z,
			int side,
			EntityLiving entityliving,
			boolean onBlockDestroyed) {
		// Check that the current itemstack is a Tool Belt
		if (ItemLib.isToolBelt(itemstack)) {
			// Retrieve the data for the itemstack
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityliving, world, itemstack);
			// Retrieve the selected tool
			ItemStack selectedTool = data.getSelectedTool();
			// If there is a tool in the selected slot
			if (selectedTool != null) {
				System.out.println("doDestroyBlock");
				// Perform the onBlockDestroyed using that Tool
				return selectedTool.getItem().onBlockDestroyed(selectedTool, world, x, y, z, side, entityliving);
			}
		}
		return onBlockDestroyed;
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		// Retrieves a unique data ID from the world and sets the ItemStack to that ID
		// This Unique ID is used to store world data for a Tool Belt
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		// Attempt to get existing data (this should theoretically not exist
		if (MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, world, itemstack) == null) {
			// If we end with no data then Log an error
			LoggerTMF.getInstance("ItemMiningToolBelt"
					).write(
							world.isRemote,
							"Tool Belt Data Creation Failed",
							Logger.LogLevel.DEBUG
					);
		}
	}
	
	@Override
	public String getTextureFile() {
		return SpriteLib.ITEM_SPRITE_PATH;
	}

	/**
	 * This performs the interrupted breakSpeed event from Forge
	 * Only activates if the player is holding a tool belt
	 * Used to retrieve a new speed based on Tool Belt tool in use
	 * 
	 * @param event The event to use
	 */
	public static void doBreakSpeed(BreakSpeed event) {
		// Retrieves the Held Tool Belt
		ItemStack toolBelt = ItemLib.getToolBelt(event.entityPlayer, event.entityPlayer.worldObj, true);
		// If the player is still holding the tool belt 
		if (toolBelt != null) {
			// Retrieves the Tool Belt data
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(event.entityPlayer, event.entityPlayer.worldObj, toolBelt);
			ItemStack selectedStack =
					// Checks if the player is in Mining Mode
					// If true then auto select tool for the best STR vs Block
					// Otherwise return our selected tool
					MiningMode.isPlayerInMiningMode(event.entityPlayer)
					? data.selectToolForBlock(event.block, event.originalSpeed)
					: data.getSelectedTool();
			// If an item exists in the selected slot of the Tool Belt
			if (selectedStack != null) {
				// Generate break speed for that Tool vs. Block
				float newSpeed = (selectedStack.getStrVsBlock(event.block)) + MiningMode.getPlayerStrength(event.entityPlayer, toolBelt, data);
				// If the new speed is greater than the speed being parsed in the event then set the new speed
				event.newSpeed =  newSpeed > event.originalSpeed ? newSpeed : event.originalSpeed;
			}
		}
	}

	/**
	 * This performs the harvesting check event from Forge
	 * Activates when the player has successfully mined a block
	 * Used to correctly determine whether or not the tool in slot will harvest the block broken
	 * 
	 * @param event The harvesting event
	 */
	public static void doHarvestCheck(HarvestCheck event) {
		// Retrieves the Held Tool Belt
		ItemStack toolBelt = ItemLib.getToolBelt(event.entityPlayer, event.entityPlayer.worldObj, true);
		// If the player is still holding the Tool Belt
		if (toolBelt != null) {
			// Retrieves the Tool Belt data
			MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(event.entityPlayer, event.entityPlayer.worldObj, toolBelt);
			// Retrieves the selected Tool
			ItemStack selectedStack = data.getSelectedTool();
			// If a Tool exists in the selected slot
			if (selectedStack != null) {
				// Run a harvest check on that Tool and set the result
				event.success = selectedStack.canHarvestBlock(event.block);
			}
		}
	}

	/**
	 * This performs the entity interact event from Forge
	 * Activates when the player interacts (Right clicks) with an entity
	 * Used to correctly determine whether or not the tool in the slot can interact with the entity
	 * (Should only allow use when the player is sneaking)
	 * 
	 * @param event The Interaction Event
	 * 
	 * @return whether or not we interacted with the entity
	 * 		Thus whether or not to continue processing the 'normal' interaction 
	 */
	public static boolean doEntityInteract(EntityInteractEvent event) {
		// First checks if the player is sneaking
		if (event.entityPlayer.isSneaking()) {
			// Retrieves the Held Tool Belt
			ItemStack toolBelt = ItemLib.getToolBelt(event.entityPlayer, event.entityPlayer.worldObj, true);
			// If the player is still holding the Tool Belt
			if (toolBelt != null) {
				// Retrieves the Tool Belt data
				MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(event.entityPlayer, event.entityPlayer.worldObj, toolBelt);
				// Retrieves the selected Tool
				ItemStack selectedStack = data.getSelectedTool();
				// If a Tool exists in the selected slot
				if (selectedStack != null) {
					// Run the interactWith on the tool
					return selectedStack.interactWith((EntityLiving) event.target);
				}
			}
		}
		return false;
	}

	public static boolean doLeftClickBlock(PlayerInteractEvent event) {
		if (event.entityPlayer.isSneaking()) {
			System.out.println("Left Clicked Block");
			return true;
		}
		return false;
	}

	public static boolean doRightClickBlock(PlayerInteractEvent event) {
		if (event.entityPlayer.isSneaking()) {
			System.out.println("Right Clicked Block");
			return true;
		}
		return false;
	}

	public static boolean doRightClickAir(PlayerInteractEvent event) {
		if (event.entityPlayer.isSneaking()) {
			System.out.println("Right Clicked Air");
			return true;
		}
		return false;
	}
}
