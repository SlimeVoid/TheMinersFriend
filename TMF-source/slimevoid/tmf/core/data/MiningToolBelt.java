package slimevoid.tmf.core.data;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import slimevoid.tmf.core.lib.CommandLib;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.NamingLib;
import slimevoid.tmf.network.packets.PacketMiningToolBelt;
import cpw.mods.fml.common.network.PacketDispatcher;

public class MiningToolBelt extends WorldSavedData implements IInventory {
	private ItemStack[] miningTools;
	private int toolBeltId;
	private int selectedTool;

	public MiningToolBelt(String dataString) {
		super(dataString);
		miningTools = new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
		selectedTool = 0;
	}
	
	/**
	 * Set the Tool Belt ID
	 * 
	 * @param Id the ID to set
	 */
	public void setToolBeltId(int Id) {
		this.toolBeltId = Id;
	}
	
	/**
	 * Set the selected tool and return for convenience
	 * 
	 * @param slot the Tool Slot to select
	 * @return the Selected Tool
	 */
	public ItemStack selectTool(int slot) {
		this.selectedTool = slot;
		return this.getSelectedTool();
	}
	
	/**
	 * Select the next Tool
	 * 
	 * @return the new Selected Tool
	 */
	public ItemStack selectTool() {
		this.selectedTool++;
		if (this.selectedTool >= DataLib.TOOL_BELT_SELECTED_MAX) {
			this.selectedTool = 0;
		}
		return this.getStackInSlot(this.selectedTool) != null ? 
				this.getStackInSlot(this.selectedTool) : this.tryToSelectTool();
	}
	
	/**
	 * Rotate through the inventory until we select a Successful Tool
	 * 
	 * @return new Selected Tool or null if no Tool was successfully Selected 
	 */
	private ItemStack tryToSelectTool() {
		if (this.getSelectedTool() == null) {
			for (int i = 0; i < DataLib.TOOL_BELT_SELECTED_MAX; i++) {
				ItemStack itemstack = this.getStackInSlot(i);
				if (itemstack != null) {
					return itemstack;
				}
			}
		}
		return null;
	}
	
	/**
	 * Selects the best tool for the job out of tools within TOOL_BELT_SELECTED_MAX slots 
	 * 
	 * @param block the Block the player is attempting to mine
	 * @param currentBreakSpeed the break speed with the current tool
	 * @return the new Selected Tool
	 */
	public ItemStack selectToolForBlock(Block block, float currentBreakSpeed) {
		float fastestSpeed = currentBreakSpeed;
		for (int i = 0; i < DataLib.TOOL_BELT_SELECTED_MAX; i++) {
			ItemStack itemstack = this.getStackInSlot(i);
			if (itemstack != null) {
				float breakSpeed = itemstack.getStrVsBlock(block);
				if (breakSpeed > fastestSpeed) {
					this.selectedTool = i;
				}
			}
		}
		return this.getSelectedTool();
	}
	
	/**
	 * Retrieve the Tool Belt ID for the world save data
	 * 
	 * @return the ID
	 */
	public int getToolBeltId() {
		return this.toolBeltId;
	}

	/**
	 * Retrieve the Selected Tool in this Tool Belt
	 * 
	 * @return the Selected Tool
	 */
	public ItemStack getSelectedTool() {
		return this.miningTools[this.selectedTool];
	}

	/**
	 * Retrieve the Selected Tool Slot in this Tool Belt
	 * @return
	 */
	public int getSelectedSlot() {
		return this.selectedTool;
	}

	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
		NBTTagList toolsTag = nbttagcompound.getTagList("Tools");
		this.miningTools = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < toolsTag.tagCount(); i++) {
			NBTTagCompound tagCompound = (NBTTagCompound) toolsTag.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < this.miningTools.length) {
				this.miningTools[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
		this.toolBeltId = nbttagcompound.getInteger("id");
		this.selectedTool = nbttagcompound.getInteger("tool");
	}

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
    	NBTTagList toolsTag = new NBTTagList();
    	for (int i = 0; i < this.miningTools.length; i++) {
    		if (miningTools[i] != null) {
    			NBTTagCompound tagCompound = new NBTTagCompound();
    			tagCompound.setByte("Slot", (byte) i);
    			this.miningTools[i].writeToNBT(tagCompound);
        		toolsTag.appendTag(tagCompound);
    		}
    	}
		nbttagcompound.setTag("Tools", toolsTag);
		nbttagcompound.setInteger("id", this.toolBeltId);
		nbttagcompound.setInteger("tool", this.selectedTool);
    }

	@Override
	public int getSizeInventory() {
		return this.miningTools.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.miningTools[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int stacksize) {
		if (this.miningTools[slot] != null) {
			ItemStack stackInSlot;
			
			if (this.miningTools[slot].stackSize <= stacksize) {
				stackInSlot = this.miningTools[slot];
				this.miningTools[slot] = null;
				this.onInventoryChanged();
				return stackInSlot;
			} else {
				stackInSlot = this.miningTools[slot].splitStack(stacksize);
				
				if (this.miningTools[slot].stackSize <= 0) {
					this.miningTools[slot] = null;
				}
				
				this.onInventoryChanged(true);
				return stackInSlot;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.miningTools[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
        	itemstack.stackSize = this.getInventoryStackLimit();
        }
        
        this.miningTools[slot] = itemstack;

        this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return NamingLib.MINING_TOOL_BELT;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * Notify this Tool Belt that changes have happened
	 *  
	 * @param sendUpdate whether or not we should send updates to connected Players 
	 */
	public void onInventoryChanged(boolean sendUpdate) {
		this.onInventoryChanged();
		if (sendUpdate) this.sendUpdate();
	}

	/**
	 * Send the updated Tool Belt data to players connected
	 * Kind of Hammer to the fly for the moment
	 */
	private void sendUpdate() {
		PacketDispatcher.sendPacketToAllPlayers(this.createPacket().getPacket());
	}

	@Override
	public void onInventoryChanged() {
		this.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	/**
	 * Retrieve Tool Belt data from an ItemStack (usually held by a player)
	 * 
	 * @param entityliving the Entity holding the Tool Belt (usually the player)
	 * @param world the World to which the Tool Belt belongs (as above)
	 * @param itemstack the (usually held) Tool Belt ItemStack
	 * @return the Tool Belt data
	 */
	public static MiningToolBelt getToolBeltDataFromItemStack(EntityLiving entityliving, World world, ItemStack heldItem) {
		MiningToolBelt data = (MiningToolBelt)world.loadItemData(MiningToolBelt.class, getWorldIndexFromItemStack(heldItem));// Check if the data is null
		if (data == null) {
			// Retrieve a new data set for the current Tool Belt
			data = MiningToolBelt.getNewToolBeltData(entityliving, world, heldItem);
			// Check if the data creation was successfull
			if (data != null) {
				// Save the item data first
				world.setItemData(data.mapName, data);
				// Set the tool belt ID for ease of use later
				data.setToolBeltId(heldItem.getItemDamage());
				// Mark the data for an update
				data.onInventoryChanged();
			}
		}
		return data;
	}

	/**
	 * Retrieves Tool Belt Data from the ID
	 * Usually accessed from Packet Execution
	 * 
	 * @param entityliving the Entity with the Tool Belt
	 * @param world the World in which the Tool Belt Resides
	 * @param toolBeltId the Tool Belt ID
	 * @return Tool Belt data
	 */
	public static MiningToolBelt getToolBeltDataFromId(EntityLiving entityliving, World world, int toolBeltId) {
		MiningToolBelt data = (MiningToolBelt)world.loadItemData(MiningToolBelt.class, getWorldIndexFromId(toolBeltId));
		return data;
	}

	/**
	 * Generates the world data String from an ItemStack
	 * 
	 * @param heldItem the ItemStack (usually held)
	 * @return the generated String from ItemStack damage
	 */
	public static String getWorldIndexFromItemStack(ItemStack heldItem) {
		return getWorldIndexFromId(heldItem.getItemDamage());
	}

	/**
	 * Generates the world data String from a unique value
	 * 
	 * @param Id the unique ID
	 * @return the generated String
	 */
	public static String getWorldIndexFromId(int Id) {
		return DataLib.TOOL_BELT_INDEX.replaceAll("#", Integer.toString(Id));
	}

	/**
	 * Creates a new Instance of a Mining Tool Belt
	 * 
	 * @param entityliving the Entity holding the Tool Belt (usually the player)
	 * @param world the World to which the Tool Belt belongs (as above)
	 * @param itemstack the (usually held) Tool Belt ItemStack
	 * @return the new instance
	 */
	public static MiningToolBelt getNewToolBeltData(
			EntityLiving entityliving, World world, ItemStack itemstack) {
		// Creates a new instance of MiningToolBelt
		return new MiningToolBelt(getWorldIndexFromItemStack(itemstack));
	}

	public PacketMiningToolBelt createPacket() {
		PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.UPDATE_TOOL_BELT_CONTENTS);
		packet.setToolBeltId(this.toolBeltId);
		packet.setToolSlots(this.miningTools);
		packet.setSelectedTool(this.selectedTool);
		return packet;
	}
}
