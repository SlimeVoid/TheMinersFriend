package slimevoid.tmf.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class MiningToolBeltData extends WorldSavedData implements IInventory {
	private static final int TOOL_BELT_MAX_SIZE = 4; 
	private ItemStack[] slots;

	public MiningToolBeltData(String dataString) {
		super(dataString);
		slots = new ItemStack[this.getSizeInventory()];
	}

	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.slots = new ItemStack[this.getSizeInventory()];
		NBTTagList toolsTag = nbttagcompound.getTagList("Tools");
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (nbttagcompound.getBoolean("slot["+i+"]")) {
				this.slots[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound)toolsTag.tagAt(i));
			}
		}
	}

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
    	NBTTagList toolsTag = new NBTTagList();
    	for (int i = 0; i < this.getSizeInventory(); i++) {
    		NBTTagCompound itemstackTag = new NBTTagCompound();
    		if (slots[i] != null) {
    			nbttagcompound.setBoolean("slot["+i+"]", true);
    			slots[i].writeToNBT(itemstackTag);
    		} else {
    			nbttagcompound.setBoolean("slot["+i+"]", false);
    		}
    		toolsTag.appendTag(itemstackTag);
    	}
		nbttagcompound.setTag("Tools", toolsTag);
    }

	@Override
	public int getSizeInventory() {
		return TOOL_BELT_MAX_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.slots[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (slot < this.getSizeInventory()) {
			this.slots[slot] = itemstack;
		}
	}

	@Override
	public String getInvName() {
		return "Miner's Tool Belt";
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onInventoryChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
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

}
