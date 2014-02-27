package com.slimevoid.compatibility.thaumcraft.client;

import java.util.EnumSet;

import com.slimevoid.compatibility.thaumcraft.ThaumcraftStatic;
import com.slimevoid.tmf.core.helpers.ItemHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.client.lib.GraphicsTicker;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

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
