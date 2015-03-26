package net.slimevoid.compatibility.thaumcraft.client;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.slimevoid.compatibility.thaumcraft.ThaumcraftStatic;
import net.slimevoid.tmf.core.helpers.ItemHelper;

import java.util.EnumSet;

public class WandGuiTickHandler extends GraphicsTicker implements ITickHandler {

    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        Minecraft mc = FMLClientHandler.instance().getClient();

        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;

            if (type.contains(TickType.RENDER)) {

                if (player != null && mc.inGameHasFocus
                        && Minecraft.isGuiEnabled()) {
                    ItemStack heldItem = player.inventory.getCurrentItem();
                    ItemStack tool = ItemHelper.getSelectedTool(heldItem);
                    if (ThaumcraftStatic.isWand(tool)) {
                        ItemStack heldCopy = heldItem.copy();
                        player.inventory.setInventorySlotContents(player.inventory.currentItem,
                                tool);
                        super.tickEnd(type,
                                tickData);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem,
                                heldCopy);
                    }
                }
            }
        }
    }
}
