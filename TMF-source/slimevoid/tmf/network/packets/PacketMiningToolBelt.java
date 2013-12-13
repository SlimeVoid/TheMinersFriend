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
package slimevoid.tmf.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import slimevoidlib.nbt.NBTHelper;
import slimevoidlib.network.PacketPayload;
import slimevoid.tmf.core.lib.DataLib;
import slimevoid.tmf.core.lib.PacketLib;

public class PacketMiningToolBelt extends PacketMining {
	
	ItemStack[] miningTools;

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		this.writeToolBeltData(data);
	}
	
	private void writeToolBeltData(DataOutputStream data) throws IOException {
		NBTTagCompound nbttagcompound = null;
		if (this.miningTools != null) {
			nbttagcompound = new NBTTagCompound();
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
		}
		NBTHelper.writeNBTTagCompound(nbttagcompound, data);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.readToolBeltData(data);
	}

	private void readToolBeltData(DataInputStream data) throws IOException {
		NBTTagCompound nbttagcompound = NBTHelper.readNBTTagCompound(data);
		if (nbttagcompound != null) {
			NBTTagList toolsTag = nbttagcompound.getTagList("Tools");
			this.miningTools = new ItemStack[DataLib.TOOL_BELT_MAX_SIZE];
			for (int i = 0; i < toolsTag.tagCount(); i++) {
				NBTTagCompound tagCompound = (NBTTagCompound) toolsTag.tagAt(i);
				byte slot = tagCompound.getByte("Slot");
				if (slot >= 0 && slot < this.miningTools.length) {
					this.miningTools[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
		}
	}

	public PacketMiningToolBelt() {
		super(PacketLib.MINING_TOOL_BELT);
	}
	
	public PacketMiningToolBelt(String command) {
		this();
		this.setCommand(command);
		this.payload = new PacketPayload(2, 0, 0, 0);
	}
	
	public void setToolBeltId(int toolBeltId) {
		this.payload.setIntPayload(0, toolBeltId);
	}

	public void setSelectedTool(int selectedTool) {
		this.payload.setIntPayload(1, selectedTool);
	}
	
	public void setToolSlots(ItemStack[] tools) {
		this.miningTools = tools;
	}
	
	public void setToolInSlot(ItemStack tool, int slot) {
		if (slot >= 0 && slot < DataLib.TOOL_BELT_MAX_SIZE && tool != null) {
			miningTools[slot] = tool;
		}
	}
	
	public int getToolBeltId() {
		return this.payload.getIntPayload(0);
	}
	
	public int getSelectedTool() {
		return this.payload.getIntPayload(1);
	}
	
	public ItemStack[] getToolSlots() {
		return this.miningTools;
	}
	
	public ItemStack getToolInSlot(int slot) {
		if (slot >= 0 && slot < DataLib.TOOL_BELT_MAX_SIZE) {
			return this.miningTools[slot];
		}
		return null;
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

}
