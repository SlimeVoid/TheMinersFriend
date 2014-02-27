package com.slimevoid.compatibility.thaumcraft;

import com.slimevoid.tmf.core.helpers.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PlayerTickHandler {

    @ForgeSubscribe
    public void LivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) event.entity;
            if (entityplayer.isBlocking()) {
                ItemStack tool = ItemHelper.getSelectedTool(entityplayer.getHeldItem());
                ThaumcraftStatic.doElementalSwordUpdate(entityplayer,
                                                        tool);
            }
        }
    }
}
