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
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.NBTLib;
import slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;
import slimevoidlib.nbt.NBTHelper;

public class ItemMiningToolBelt extends Item {

	public ItemMiningToolBelt(int itemID) {
		super(itemID);
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setNoRepair();
		this.setCreativeTab(CreativeTabTMF.tabTMF);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return super.getRenderPasses(metadata);
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
									GuiLib.GUIID_TOOL_BELT,
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
		boolean flag = false;
		if (tool != null) {
			flag = tool.getItem().onItemUse(tool,
											entityplayer,
											world,
											x,
											y,
											z,
											side,
											hitX,
											hitY,
											hitZ);
			updateToolBelt(	world,
							entityplayer,
							itemstack,
							tool);
		}
		return flag;
	}

	public static boolean doItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		boolean flag = false;
		if (tool != null) {
			flag = tool.getItem().onItemUseFirst(	tool,
													entityplayer,
													world,
													x,
													y,
													z,
													side,
													hitX,
													hitY,
													hitZ);
			updateToolBelt(	world,
							entityplayer,
							itemstack,
							tool);
		}
		return false;
	}

	public static void doFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		if (tool != null) {
			tool = tool.getItem().onEaten(	tool,
											world,
											entityplayer);

			updateToolBelt(	world,
							entityplayer,
							itemstack,
							tool);
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer entityplayer, Entity entity) {
		return this.doLeftClickEntity(	itemstack,
										entityplayer,
										entity);
	}

	private boolean doLeftClickEntity(ItemStack itemstack, EntityPlayer entityplayer, Entity entity) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		boolean onLeftClickEntity = false;
		if (tool != null) {
			// Perform the onLeftClickEntity method for the itemstack
			onLeftClickEntity = tool.getItem().onLeftClickEntity(	tool,
																	entityplayer,
																	entity);
			updateToolBelt(	entityplayer.worldObj,
							entityplayer,
							itemstack,
							tool);
		}
		// Otherwise return the original value
		return onLeftClickEntity;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase mob, EntityLivingBase player) {
		return this.doHitEntity(stack,
								mob,
								player);
	}

	private boolean doHitEntity(ItemStack itemstack, EntityLivingBase mob, EntityLivingBase entityplayer) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		boolean hitEntity = false;
		if (tool != null) {
			// Perform the hitEntity method for the itemstack
			hitEntity = tool.getItem().hitEntity(	tool,
													mob,
													entityplayer);
			updateToolBelt(	entityplayer.worldObj,
							entityplayer,
							itemstack,
							tool);
		}
		// Otherwise return the original value
		return hitEntity;
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
			onBlockStartBreak = tool.getItem().onBlockStartBreak(	tool,
																	x,
																	y,
																	z,
																	entityplayer);
			updateToolBelt(	entityplayer.worldObj,
							entityplayer,
							itemstack,
							tool);
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
			onBlockDestroyed = tool.getItem().onBlockDestroyed(	tool,
																world,
																x,
																y,
																z,
																side,
																entityliving);
			updateToolBelt(	world,
							entityliving,
							itemstack,
							tool);
		}
		// Otherwise return the original value
		return onBlockDestroyed;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block, int meta) {
		// Retrieves the Selected Tool within the held Tool Belt
		ItemStack tool = ItemHelper.getSelectedTool(itemstack);
		float strVsBlock = 1.0f;
		if (tool != null) {
			// Perform the onBlockStartBreak method for the itemstack
			strVsBlock = tool.getItem().getStrVsBlock(	tool,
														block,
														meta);
		}
		// Otherwise return the original value
		return strVsBlock;
	}

	private static void updateToolBelt(World world, EntityLivingBase entityliving, ItemStack toolBelt, ItemStack tool) {
		InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, entityliving, toolBelt);
		data.setInventorySlotContents(	data.getSelectedSlot(),
										tool);
		updateToolBeltData(	toolBelt,
							data);
	}

	private static void updateToolBelt(World world, EntityLivingBase entityliving, ItemStack toolBelt) {
		InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, entityliving, toolBelt);
		updateToolBeltData(	toolBelt,
							data);
	}

	private static void updateToolBeltData(ItemStack toolBelt, InventoryMiningToolBelt data) {
		toolBelt.stackTagCompound = data.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// Retrieves a unique data ID from the world and sets the ItemStack to
		// that ID
		// This Unique ID is used to store world data for a Tool Belt
	}

	public static void toggleMiningMode(World world, EntityLivingBase entitylivingbase) {
		ItemStack toolBelt = ItemHelper.getToolBelt(entitylivingbase,
													world,
													true);
		if (toolBelt != null) {
			InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, entitylivingbase, toolBelt);
			data.toggleMiningMode();
			updateToolBeltData(	toolBelt,
								data);
		}
	}

	private static boolean isMiningModeEnabled(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			if (itemstack.stackTagCompound.hasKey(NBTLib.MINING_MODE)) {
				return itemstack.stackTagCompound.getBoolean(NBTLib.MINING_MODE);
			}
		}
		return false;
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
			ItemStack selectedStack = null;
			// Checks if the player is in Mining Mode
			// If true then auto select tool for the best STR vs Block
			// Otherwise return our selected tool
			if (isMiningModeEnabled(toolBelt)) {
				selectedStack = selectToolForBlock(	event.entityLiving.worldObj,
													event.entityLiving,
													toolBelt,
													event.block,
													event.metadata,
													event.originalSpeed);
			} else {
				selectedStack = getSelectedTool(toolBelt);
			}
			// If an item exists in the selected slot of the Tool Belt
			if (selectedStack != null && selectedStack.getItem() != null) {
				// Generate break speed for that Tool vs. Block
				float newSpeed = (selectedStack.getItem().getStrVsBlock(selectedStack,
																		event.block,
																		event.metadata))
									* DataLib.MINING_MODE_STRENGTH;
				// If the new speed is greater than the speed being parsed in
				// the event then set the new speed
				event.newSpeed = newSpeed > event.originalSpeed ? newSpeed : event.originalSpeed;
			}
		}
	}

	public static ItemStack selectToolForBlock(World world, EntityLivingBase entitylivingbase, ItemStack toolBelt, Block block, int metadata, float currentBreakSpeed) {
		float fastestSpeed = currentBreakSpeed;
		int selection = getSelectedSlot(toolBelt);
		InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, entitylivingbase, toolBelt);
		ItemStack[] tools = data.getTools();
		if (tools != null) {
			for (int i = 0; i < DataLib.TOOL_BELT_SELECTED_MAX; i++) {
				ItemStack itemstack = tools[i];
				if (itemstack != null && itemstack.getItem() != null) {
					float breakSpeed = itemstack.getItem().getStrVsBlock(	itemstack,
																			block,
																			metadata);
					if (breakSpeed > fastestSpeed) {
						fastestSpeed = breakSpeed;
						selection = i;
					}
				}
			}
		}
		return setSelectedTool(	toolBelt,
								selection);
	}

	private static void selectTool(World world, EntityLivingBase entityliving, int i) {
		ItemStack toolBelt = entityliving.getHeldItem();
		setSelectedTool(toolBelt,
						i);
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
		boolean flag = false;
		// First checks if the player is sneaking
		if (event.entityPlayer.isSneaking()) {
			ItemStack toolBelt = event.entityPlayer.getHeldItem();
			// Retrieves the Selected Tool within the held Tool Belt
			ItemStack tool = ItemHelper.getSelectedTool(toolBelt);
			if (tool != null) {
				flag = tool.func_111282_a(	event.entityPlayer,
											(EntityLivingBase) event.target);// .interactWith(event.target);
				updateToolBelt(	event.entityPlayer.worldObj,
								event.entityPlayer,
								toolBelt,
								tool);
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
				ItemStack[] miningTools = ItemHelper.getTools(nbttagcompound);
				return miningTools[selectedTool];
			}
		}
		return null;
	}

	private static int getSelectedSlot(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
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

	private static ItemStack setSelectedTool(ItemStack toolBelt, int selectedTool) {
		if (toolBelt.hasTagCompound()) {
			toolBelt.stackTagCompound.setInteger(	NBTLib.SELECTED_TOOL,
													selectedTool);
		}
		return getSelectedTool(toolBelt);
	}

	public static ItemStack cycleTool(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			int selectedTool = getSelectedSlot(itemstack);
			selectedTool++;
			if (selectedTool >= DataLib.TOOL_BELT_SELECTED_MAX) {
				selectedTool = 0;
			}
			return setSelectedTool(	itemstack,
									selectedTool);
		}
		return null;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) {
		return this.getToolDisplayName(itemstack);
	}

	private String getToolDisplayName(ItemStack itemstack) {
		int selectedSlot = getSelectedSlot(itemstack);
		ItemStack tool = getSelectedTool(itemstack);
		String name = tool != null ? tool.getDisplayName() : "Slot "
																+ selectedSlot
																+ " - Empty";
		return "ToolBelt : " + name;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List lines, boolean par4) {
		if (itemstack.hasTagCompound()) {
			ItemStack[] tools = ItemHelper.getTools(itemstack.getTagCompound());
			for (int i = 0; i < DataLib.TOOL_BELT_MAX_SIZE; i++) {
				ItemStack tool = tools[i];
				String toolString;
				if (tool == null) {
					toolString = "None Equipped";
				} else {
					toolString = tool.getDisplayName() + " x" + tool.stackSize;
				}
				lines.add("Slot [" + i + "]: " + toolString);
			}
		}
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
}
