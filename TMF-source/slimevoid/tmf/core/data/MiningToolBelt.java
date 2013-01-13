package slimevoid.tmf.core.data;
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

public class MiningToolBelt extends WorldSavedData implements IInventory {
	private ItemStack[] miningTools;
	private int toolBeltId;

	public MiningToolBelt(String dataString) {
		super(dataString);
		miningTools = new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
	}
	
	public void setToolBeltId(int Id) {
		this.toolBeltId = Id;
	}
	
	public int getToolBeltId() {
		return this.toolBeltId;
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
				
				if (this.miningTools[slot].stackSize == 0) {
					this.miningTools[slot] = null;
				}
				
				this.onInventoryChanged();
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
		return 1;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	public static MiningToolBelt getToolBeltDataFromItemStack(EntityPlayer player, World world, ItemStack heldItem) {
		MiningToolBelt data = (MiningToolBelt)world.loadItemData(MiningToolBelt.class, getWorldIndexFromItemStack(heldItem));
		return data;
	}

	public static MiningToolBelt getToolBeltDataFromId(EntityPlayer player, World world, int toolBeltId) {
		MiningToolBelt data = (MiningToolBelt)world.loadItemData(MiningToolBelt.class, getWorldIndexFromId(toolBeltId));
		return data;
	}

	public static String getWorldIndexFromItemStack(ItemStack heldItem) {
		return getWorldIndexFromId(heldItem.getItemDamage());
	}

	public static String getWorldIndexFromId(int Id) {
		return DataLib.TOOL_BELT_INDEX.replaceAll("#", Integer.toString(Id));
	}

	public static MiningToolBelt getNewToolBeltData(
			EntityPlayer entityplayer, World world, ItemStack itemstack) {
		return new MiningToolBelt(getWorldIndexFromItemStack(itemstack));
	}

	public PacketMiningToolBelt createPacket() {
		PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.UPDATE_TOOL_BELT_CONTENTS);
		packet.setToolBeltId(this.toolBeltId);
		packet.setToolSlots(this.miningTools);
		return packet;
	}
}
