/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.tmf.items.tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.data.MiningMode;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.NBTLib;
import slimevoidlib.nbt.NBTHelper;

public class ItemMiningToolBelt extends Item {

	public ItemMiningToolBelt(int itemID) {
		super(itemID);
		this.setFull3D();
		this.setNoRepair();
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean isDamaged(ItemStack itemstack) {
		return this.isToolDamaged(itemstack);
	}

	private boolean isToolDamaged(ItemStack itemstack) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			return tool.isItemDamaged();
		}
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack itemstack) {
		return this.getMaxToolDamage(itemstack);
	}

	private int getMaxToolDamage(ItemStack itemstack) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			return tool.getMaxDamage();
		}
		return 0;
	}

	@Override
	public int getDisplayDamage(ItemStack itemstack) {
		return this.getToolDisplayDamage(itemstack);
	}

	private int getToolDisplayDamage(ItemStack itemstack) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			return tool.getItemDamage();
		}
		return 0;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int tick, boolean isHeld) {
		if (!world.isRemote && isHeld && entity instanceof EntityLivingBase) {
			if (!itemstack.hasTagCompound()) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setInteger(	NBTLib.SELECTED_TOOL,
											0);
				itemstack.setTagCompound(nbttagcompound);
			} else {
				NBTTagCompound nbttagcompound = itemstack.getTagCompound();
				// System.out.println(nbttagcompound);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()) {
			doItemRightClick(	itemstack,
								world,
								entityplayer);
		} else {
			entityplayer.openGui(	TheMinersFriend.instance,
									GuiLib.TOOL_BELT_GUIID,
									world,
									(int) entityplayer.posX,
									(int) entityplayer.posY,
									(int) entityplayer.posZ);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (entityplayer.isSneaking()) {
			return doItemUse(	itemstack,
								entityplayer,
								world,
								x,
								y,
								z,
								side,
								hitX,
								hitY,
								hitZ);
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (entityplayer.isSneaking()) {
			return doItemUseFirst(	itemstack,
									entityplayer,
									world,
									x,
									y,
									z,
									side,
									hitX,
									hitY,
									hitZ);
		}
		return false;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()) {
			doFoodEaten(itemstack,
						world,
						entityplayer);
		}
		return itemstack;
	}

	public static void doItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			tool.getItem().onItemRightClick(tool,
											world,
											entityplayer);
		}
	}

	public static boolean doItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			return tool.getItem().onItemUse(tool,
											entityplayer,
											world,
											x,
											y,
											z,
											side,
											hitX,
											hitY,
											hitZ);
		}
		return false;
	}

	public static boolean doItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			return tool.getItem().onItemUseFirst(	tool,
													entityplayer,
													world,
													x,
													y,
													z,
													side,
													hitX,
													hitY,
													hitZ);
		}
		return false;
	}

	public static void doFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			tool.getItem().onEaten(	tool,
									world,
									entityplayer);
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int x, int y, int z, int side, EntityLivingBase entityliving) {
		return doDestroyBlock(	itemstack,
								world,
								x,
								y,
								z,
								side,
								entityliving,
								super.onBlockDestroyed(	itemstack,
														world,
														x,
														y,
														z,
														side,
														entityliving));
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer entityplayer) {
		return doStartBreakBlock(	itemstack,
									x,
									y,
									z,
									entityplayer,
									super.onBlockStartBreak(itemstack,
															x,
															y,
															z,
															entityplayer));
	}

	private boolean doStartBreakBlock(ItemStack itemstack, int x, int y, int z, EntityPlayer entityplayer, boolean onBlockStartBreak) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			// Perform the onBlockStartBreak method for the itemstack
			return tool.getItem().onBlockStartBreak(tool,
													x,
													y,
													z,
													entityplayer);
		}
		// Otherwise return the original value
		return onBlockStartBreak;
	}

	/**
	 * Attempts to destroy the block using the selected item in the Tool Belt
	 * 
	 * @param itemstack
	 *            the Held Item
	 * @param world
	 *            the World
	 * @param x
	 * @param y
	 * @param z
	 * @param side
	 * @param entityliving
	 *            (Usually the player)
	 * @param onBlockDestroyed
	 *            the current result
	 * @return
	 */
	public static boolean doDestroyBlock(ItemStack itemstack, World world, int x, int y, int z, int side, EntityLivingBase entityliving, boolean onBlockDestroyed) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			// Perform the onBlockDestroyed method for the itemstack
			return tool.getItem().onBlockDestroyed(	tool,
													world,
													x,
													y,
													z,
													side,
													entityliving);
		}
		// Otherwise return the original value
		return onBlockDestroyed;
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// Retrieves a unique data ID from the world and sets the ItemStack to
		// that ID
		// This Unique ID is used to store world data for a Tool Belt
	}

	/**
	 * This performs the interrupted breakSpeed event from Forge Only activates
	 * if the player is holding a tool belt Used to retrieve a new speed based
	 * on Tool Belt tool in use
	 * 
	 * @param event
	 *            The event to use
	 */
	public static void doBreakSpeed(BreakSpeed event) {
		// Retrieves the Held Tool Belt
		ItemStack toolBelt = ItemHelper.getToolBelt(event.entityPlayer,
													event.entityPlayer.worldObj,
													true);
		// If the player is still holding the tool belt
		if (toolBelt != null) {
			// Retrieves the Tool Belt data
			ItemStack selectedStack =
			// Checks if the player is in Mining Mode
			// If true then auto select tool for the best STR vs Block
			// Otherwise return our selected tool
			MiningMode.isPlayerInMiningMode(event.entityPlayer) ? selectToolForBlock(	event.entityPlayer.worldObj,
																						event.entityLiving,
																						event.block,
																						event.originalSpeed) : getSelectedTool(event.entityLiving.getHeldItem());
			// If an item exists in the selected slot of the Tool Belt
			if (selectedStack != null) {
				// Generate break speed for that Tool vs. Block
				float newSpeed = (selectedStack.getStrVsBlock(event.block))
									* MiningMode.getPlayerStrength(	event.entityPlayer,
																	toolBelt);
				// If the new speed is greater than the speed being parsed in
				// the event then set the new speed
				event.newSpeed = newSpeed > event.originalSpeed ? newSpeed : event.originalSpeed;
			}
		}
	}

	public static ItemStack selectToolForBlock(World world, EntityLivingBase entityliving, Block block, float currentBreakSpeed) {
		float fastestSpeed = currentBreakSpeed;
		for (int i = 0; i < DataLib.TOOL_BELT_SELECTED_MAX; i++) {
			ItemStack itemstack = getToolInSlot(entityliving.getHeldItem(),
												i);
			if (itemstack != null) {
				float breakSpeed = itemstack.getStrVsBlock(block);
				if (breakSpeed > fastestSpeed) {
					selectTool(	world,
								entityliving,
								i);
				}
			}
		}
		return getSelectedTool(entityliving.getHeldItem());
	}

	private static void selectTool(World world, EntityLivingBase entityliving, int i) {
		ItemStack toolBelt = entityliving.getHeldItem();
		if (toolBelt.hasTagCompound()) {
			NBTTagCompound nbttagcompound = toolBelt.getTagCompound();
			nbttagcompound.setInteger(	NBTLib.SELECTED_TOOL,
										i);
		}
	}

	/**
	 * This performs the harvesting check event from Forge Activates when the
	 * player has successfully mined a block Used to correctly determine whether
	 * or not the tool in slot will harvest the block broken
	 * 
	 * @param event
	 *            The harvesting event
	 */
	public static void doHarvestCheck(HarvestCheck event) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(event.entityPlayer.getHeldItem());
		if (tool != null) {
			// Run a harvest check on that Tool and set the result
			event.success = tool.canHarvestBlock(event.block);
		}
	}

	/**
	 * This performs the entity interact event from Forge Activates when the
	 * player interacts (Right clicks) with an entity Used to correctly
	 * determine whether or not the tool in the slot can interact with the
	 * entity (Should only allow use when the player is sneaking)
	 * 
	 * @param event
	 *            The Interaction Event
	 * 
	 * @return whether or not we interacted with the entity Thus whether or not
	 *         to continue processing the 'normal' interaction
	 */
	public static boolean doEntityInteract(EntityInteractEvent event) {
		// First checks if the player is sneaking
		if (event.entityPlayer.isSneaking()) {
			// Retrieves the Selected Tool within the held Tool Belt
			ItemStack tool = ItemHelper.getSelectedTool(event.entityPlayer.getHeldItem());
			if (tool != null) {
				return tool.func_111282_a(	event.entityPlayer,
											(EntityLivingBase) event.target);// .interactWith(event.target);
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

	public static ItemStack getToolInSlot(ItemStack toolBelt, int selectedTool) {
		if (toolBelt.hasTagCompound()) {
			NBTTagCompound nbttagcompound = toolBelt.getTagCompound();
			if (selectedTool >= 0 && selectedTool < DataLib.TOOL_BELT_MAX_SIZE) {
				String toolKey = NBTLib.getToolKey(selectedTool);
				if (nbttagcompound.hasKey(toolKey)) {
					NBTTagCompound tagCompound = nbttagcompound.getCompoundTag(toolKey);
					return ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
		}
		return null;
	}

	public static ItemStack setToolInSlot(ItemStack toolBelt, ItemStack itemstack, int slot) {
		if (itemstack != null) {
			if (toolBelt.hasTagCompound()) {
				NBTTagCompound nbttagcompound = toolBelt.getTagCompound();
				if (slot >= 0 && slot < DataLib.TOOL_BELT_MAX_SIZE) {
					String toolKey = NBTLib.getToolKey(slot);
					System.out.println("setToolInSlot");
					System.out.println(nbttagcompound);
					if (nbttagcompound.hasKey(toolKey)) {
						nbttagcompound.removeTag(toolKey);
						System.out.println("removeTag(" + toolKey + ")");
					}
					NBTTagCompound tagCompound = new NBTTagCompound();
					itemstack.writeToNBT(tagCompound);
					nbttagcompound.setCompoundTag(	toolKey,
													tagCompound);
				}
			}
		}
		return toolBelt;
	}

	public static int getSelectedSlot(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = itemstack.getTagCompound();
			int selectedTool = NBTHelper.getTagInteger(	itemstack,
														NBTLib.SELECTED_TOOL,
														0);
			return selectedTool;
		}
		return 0;
	}

	public static ItemStack getSelectedTool(ItemStack itemstack) {
		return getToolInSlot(	itemstack,
								getSelectedSlot(itemstack));
	}

	public static ItemStack[] getTools(ItemStack itemstack) {
		ItemStack[] tools = new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = itemstack.getTagCompound();
			for (int i = 0; i < DataLib.TOOL_BELT_MAX_SIZE; i++) {
				String toolKey = NBTLib.getToolKey(i);
				if (nbttagcompound.hasKey(toolKey)) {
					NBTTagCompound tagCompound = nbttagcompound.getCompoundTag(toolKey);
					tools[i] = ItemStack.loadItemStackFromNBT(tagCompound);
				} else {
					tools[i] = null;
				}
			}
		}
		return tools;
	}

	private static void setSelectedTool(ItemStack toolBelt, int selectedTool) {
		if (toolBelt.hasTagCompound()) {
			toolBelt.stackTagCompound.setInteger(	NBTLib.SELECTED_TOOL,
													selectedTool);
		}
	}

	public static ItemStack cycleTool(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			int selectedTool = getSelectedSlot(itemstack);
			selectedTool++;
			if (selectedTool >= DataLib.TOOL_BELT_SELECTED_MAX) {
				selectedTool = 0;
			}
			setSelectedTool(itemstack,
							selectedTool);
			return getToolInSlot(	itemstack,
									selectedTool) != null ? getToolInSlot(	itemstack,
																			selectedTool) : tryToSelectTool(itemstack);
		}
		return null;
	}

	private static ItemStack tryToSelectTool(ItemStack itemstack) {
		if (getSelectedTool(itemstack) == null) {
			for (int i = 0; i < DataLib.TOOL_BELT_SELECTED_MAX; i++) {
				ItemStack tool = getToolInSlot(	itemstack,
												i);
				if (tool != null) {
					setSelectedTool(itemstack,
									i);
					return getToolInSlot(	itemstack,
											i);
				}
			}
		}
		return null;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) {
		return this.getToolDisplayName(itemstack);
	}

	private String getToolDisplayName(ItemStack itemstack) {
		return super.getItemDisplayName(itemstack);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List lines, boolean par4) {

	}

	@Override
	public boolean getShareTag() {
		return true;
	}
}
