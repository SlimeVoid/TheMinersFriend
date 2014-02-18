package slimevoid.compatibility.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.compatibility.lib.packets.PacketCompatibility;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRepairableExtended;
import thaumcraft.common.items.wands.WandManager;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThaumcraftStatic {

    public static final String ITEM_WAND_CASTING    = "item.WandCasting";
    public static final String COMMAND_CHANGE_FOCUS = "changeFocus";

    public static boolean isWand(ItemStack tool) {
        return tool != null && tool.getItem() != null
               && tool.getItem().getUnlocalizedName().equals(ITEM_WAND_CASTING);
    }

    @SideOnly(Side.CLIENT)
    public static void sendChangeFocus() {
        PacketCompatibility packet = new PacketCompatibility();
        packet.setCommand(COMMAND_CHANGE_FOCUS);
        PacketDispatcher.sendPacketToServer(packet.getPacket());
    }

    public static void doChangeFocus(World world, EntityPlayer entityplayer) {
        ItemStack heldItem = entityplayer.getHeldItem();
        ItemStack tool = ItemHelper.getSelectedTool(heldItem);
        if (isWand(tool)) {
            ItemStack toolCopy = tool.copy();
            WandManager.changeFocus(tool,
                                    world,
                                    entityplayer);
            ((ItemMiningToolBelt) heldItem.getItem()).updateToolInToolBelt(world,
                                                                           entityplayer,
                                                                           heldItem,
                                                                           tool,
                                                                           toolCopy);
        }
    }

    public static boolean doRepair(ItemStack itemstack, EntityPlayer entityplayer, int level) {
        ItemMiningToolBelt toolBelt = (ItemMiningToolBelt) itemstack.getItem();
        ItemStack tool = ItemHelper.getSelectedTool(itemstack);
        ItemStack toolCopy = ItemStack.copyItemStack(tool);
        if (tool != null && tool.getItem() != null) {
            if (tool.getItem() instanceof IRepairable) {
                if (tool.getItem() instanceof IRepairableExtended) {
                    if (((IRepairableExtended) tool.getItem()).doRepair(tool,
                                                                        entityplayer,
                                                                        level)) {
                        tool.damageItem(-level,
                                        entityplayer);
                    }
                } else {
                    tool.damageItem(-level,
                                    entityplayer);
                }
            }
        }
        toolBelt.updateToolInToolBelt(entityplayer.getEntityWorld(),
                                      entityplayer,
                                      itemstack,
                                      tool,
                                      toolCopy);
        return false;
    }
}
