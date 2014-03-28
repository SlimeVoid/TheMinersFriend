package net.slimevoid.compatibility.tinkersconstruct;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.slimevoid.tmf.core.helpers.ItemHelper;

public class TinkersConstructStatic {

    public static final String INFI_TOOL     = "InfiTool";
    public static final String HARVEST_LEVEL = "HarvestLevel";

    public static void handleNBT(ItemStack tool, NBTTagCompound nbttagcompound) {
        if (ItemHelper.isItemInfiTool(tool)) {
            NBTTagCompound tag = tool.stackTagCompound.getCompoundTag(INFI_TOOL);
            nbttagcompound.setTag(INFI_TOOL,
                                  tag);
        } else if (nbttagcompound.hasKey(INFI_TOOL)) {
            nbttagcompound.removeTag(INFI_TOOL);
        }
    }
}
