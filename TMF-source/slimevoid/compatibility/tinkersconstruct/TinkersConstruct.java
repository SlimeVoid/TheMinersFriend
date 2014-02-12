package slimevoid.compatibility.tinkersconstruct;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimevoid.compatibility.MasterCompatibility;
import slimevoid.tmf.core.helpers.ItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TinkersConstruct extends MasterCompatibility {

    public static final String MOD_ID        = "TinkersConstruct";
    public static final String INFI_TOOL     = "InfiTool";
    public static final String HARVEST_LEVEL = "HarvestLevel";

    public TinkersConstruct() {
        super(MOD_ID);
    }

    public static void handleNBT(ItemStack tool, NBTTagCompound nbttagcompound) {
        if (ItemHelper.isItemInfiTool(tool)) {
            NBTTagCompound tag = tool.stackTagCompound.getCompoundTag(INFI_TOOL);
            nbttagcompound.setTag(INFI_TOOL,
                                  tag);
        } else if (nbttagcompound.hasKey(INFI_TOOL)) {
            nbttagcompound.removeTag(INFI_TOOL);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerKeyBindings() {
    }

    public void registerPacketExecutors() {
    }

    @Override
    public void registerTickHandlers() {
    }
}
