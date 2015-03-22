package net.slimevoid.tmf.items.tools.inventory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.slimevoid.compatibility.tinkersconstruct.TinkersConstructStatic;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import net.slimevoid.tmf.core.lib.DataLib;
import net.slimevoid.tmf.core.lib.ItemLib;
import net.slimevoid.tmf.core.lib.NBTLib;
import net.slimevoid.tmf.core.lib.PacketLib;

public class InventoryMiningToolBelt implements IInventory {
    World            world;
    EntityLivingBase entityliving;
    int              selectedTool;
    ItemStack[]      miningTools;
    boolean          mode;

    public InventoryMiningToolBelt(ItemStack itemstack) {
        this((World) null, (EntityLivingBase) null, itemstack);
    }

    public InventoryMiningToolBelt(World world, EntityLivingBase entityliving, ItemStack itemstack) {
        this.selectedTool = 0;
        this.miningTools = new ItemStack[NBTLib.MAX_TOOLS];
        this.mode = false;
        this.world = world;
        this.entityliving = entityliving;

        if (itemstack != null && itemstack.hasTagCompound()) {
            this.readFromNBT(itemstack.stackTagCompound);
        }
    }

    public int getSelectedSlot() {
        return this.selectedTool;
    }

    public boolean getMiningMode() {
        return this.mode;
    }

    public ItemStack[] getTools() {
        return this.miningTools;
    }

    @Override
    public int getSizeInventory() {
        return DataLib.TOOL_BELT_MAX_SIZE;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot >= 0 && slot < this.miningTools.length ? this.miningTools[slot] : null;
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
        if (itemstack != null
            && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.miningTools[slot] = itemstack;
        this.onInventoryChanged();
    }

    @Override
    public String getInventoryName() {
        return ItemLib.MINING_TOOLBELT;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public void onExternalChange() {
        ItemStack heldItem = this.entityliving.getHeldItem();
        this.readFromNBT(heldItem.getTagCompound());
    }

    private void onInventoryChanged() {
        this.markDirty();
    }

    @Override
    public void markDirty() {
        ItemStack heldItem = this.entityliving.getHeldItem();

        if (ItemHelper.isToolBelt(heldItem)) {
            heldItem.stackTagCompound = new NBTTagCompound();
            this.writeToNBT(heldItem.stackTagCompound);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    public void toggleMiningMode() {
        this.mode = !this.mode;
        PacketLib.sendMiningModeMessage(this.world,
                                        this.entityliving,
                                        this.mode);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return !ItemHelper.isItemBlock(itemstack)
               && !ItemHelper.isToolBelt(itemstack);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        NBTTagList toolsTag = nbttagcompound.getTagList(NBTLib.TOOLS,
                                                        10);
        this.miningTools = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < toolsTag.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) toolsTag.getCompoundTagAt(i);
            byte slot = tagCompound.getByte(NBTLib.SLOT);

            if (slot >= 0 && slot < this.miningTools.length) {
                this.miningTools[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        this.selectedTool = nbttagcompound.getInteger(NBTLib.SELECTED_TOOL);
        this.mode = nbttagcompound.getBoolean(NBTLib.MINING_MODE);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        ItemStack tool = this.miningTools[this.getSelectedSlot()];
        this.updateMirroredTags(nbttagcompound,
                                tool);
        this.updateToolListTags(nbttagcompound,
                                this.miningTools);
        nbttagcompound.setInteger(NBTLib.SELECTED_TOOL,
                                  this.selectedTool);
        nbttagcompound.setBoolean(NBTLib.MINING_MODE,
                                  this.mode);
    }

    public void updateMirroredTags(NBTTagCompound nbttagcompound, ItemStack tool) {
        NBTTagList enchantments = null;

        if (tool != null && tool.hasTagCompound()
            && tool.getEnchantmentTagList() != null) {
            enchantments = tool.getEnchantmentTagList();
        }

        if (enchantments != null) {
            nbttagcompound.setTag(NBTLib.ENCHANTMENTS,
                                  enchantments);
        } else if (nbttagcompound.hasKey(NBTLib.ENCHANTMENTS)) {
            nbttagcompound.removeTag(NBTLib.ENCHANTMENTS);
        }

        if (ItemHelper.isItemInfiTool(tool)) {
            NBTTagCompound tag = tool.getTagCompound().getCompoundTag(TinkersConstructStatic.INFI_TOOL);
            nbttagcompound.setTag(TinkersConstructStatic.INFI_TOOL,
                                  tag);
        } else if (nbttagcompound.hasKey(TinkersConstructStatic.INFI_TOOL)) {
            nbttagcompound.removeTag(TinkersConstructStatic.INFI_TOOL);
        }
    }

    public void updateToolListTags(NBTTagCompound nbttagcompound, ItemStack[] tools) {
        NBTTagList toolsTag = new NBTTagList();

        for (int i = 0; i < tools.length; ++i) {
            if (tools[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte(NBTLib.SLOT,
                                    (byte) i);
                tools[i].writeToNBT(tagCompound);
                toolsTag.appendTag(tagCompound);
            }
        }

        nbttagcompound.setTag(NBTLib.TOOLS,
                              toolsTag);
    }
}
