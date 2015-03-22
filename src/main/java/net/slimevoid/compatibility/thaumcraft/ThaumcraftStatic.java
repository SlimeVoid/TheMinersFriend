package net.slimevoid.compatibility.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import net.slimevoid.tmf.items.tools.ItemMiningToolBelt;
//import thaumcraft.api.IRepairable;
//import thaumcraft.api.IRepairableExtended;

import com.google.common.io.ByteArrayDataInput;

public class ThaumcraftStatic {

    public static final String ITEM_WAND_CASTING = "item.WandCasting";

    public static boolean isWand(ItemStack tool) {
        return tool != null && tool.getItem() != null
               && tool.getItem().getUnlocalizedName().equals(ITEM_WAND_CASTING);
    }

    public static boolean isWandInToolBelt(ItemStack itemstack) {
        ItemStack tool = ItemHelper.getSelectedTool(itemstack);
        boolean flag = false;
        // flag = tool != null && tool.getItem() != null
        // && tool.getItem() instanceof ItemWandCasting;
        return flag;
    }

    public static void doChangeFocus(World world, EntityPlayer entityplayer, String focus) {
        ItemStack heldItem = entityplayer.getHeldItem();
        ItemStack tool = ItemHelper.getSelectedTool(heldItem);
        if (isWand(tool)) {
            ItemStack toolCopy = tool.copy();
            // WandManager.changeFocus(tool,
            // world,
            // entityplayer,
            // focus);
            ((ItemMiningToolBelt) heldItem.getItem()).updateToolInToolBelt(world,
                                                                           entityplayer,
                                                                           heldItem,
                                                                           tool,
                                                                           toolCopy);
        }
    }

    public static void handleFocusChangePacket(ByteArrayDataInput data) {
        int entityId = data.readInt();
        int dimension = data.readInt();
        WorldServer world = DimensionManager.getWorld(dimension);

        if (world != null) {
            Entity player = world.getEntityByID(entityId);
            String focus = readString(data);
            if (player != null && player instanceof EntityPlayer) {
                doChangeFocus(world,
                              (EntityPlayer) player,
                              focus);
            }
        }
    }

    public static void doElementalSwordUpdate(EntityPlayer entityplayer, ItemStack tool) {
        // if (tool != null && tool.getItem() instanceof ItemElementalSword) {
        // entityplayer.motionY += 0.08D;

        // if (entityplayer.motionY > 0.5D) {
        // entityplayer.motionY = 0.2D;
        // }
        // }
    }

    public static boolean doRepair(ItemStack itemstack, EntityPlayer entityplayer, int level) {
        ItemMiningToolBelt toolBelt = (ItemMiningToolBelt) itemstack.getItem();
        ItemStack tool = ItemHelper.getSelectedTool(itemstack);
        ItemStack toolCopy = ItemStack.copyItemStack(tool);
        if (tool != null && tool.getItem() != null) {
            // if (tool.getItem() instanceof IRepairable) {
            // if (tool.getItem() instanceof IRepairableExtended) {
            // if (((IRepairableExtended) tool.getItem()).doRepair(tool,
            // entityplayer,
            // level)) {
            // tool.damageItem(-level,
            // entityplayer);
            // }
            // } else {
            tool.damageItem(-level,
                            entityplayer);
            // }
            // }
        }
        toolBelt.updateToolInToolBelt(entityplayer.getEntityWorld(),
                                      entityplayer,
                                      itemstack,
                                      tool,
                                      toolCopy);
        return false;
    }

    public static void handlePacket(/*
                                     * NetworkManager manager,
                                     * Packet250CustomPayload packet, Player
                                     * player
                                     */) {
        // if (Mods.THAUMCRAFT.getCompat().isLoaded) {
        // ByteArrayDataInput data =
        // ByteStreams.newDataInput(packet.data.clone());
        // byte packetId = data.readByte();
        // if (packetId == PacketLib.SEND_FOCUS_CHANGE_TO_SERVER) {
        // handleFocusChangePacket(data);
        // }
        // }
    }

    private static String readString(ByteArrayDataInput dat) {
        short size = dat.readShort();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; ++i) {
            builder.append(dat.readChar());
        }

        return builder.toString();
    }
}
