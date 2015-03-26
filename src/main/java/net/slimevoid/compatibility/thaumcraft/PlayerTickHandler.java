package net.slimevoid.compatibility.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.slimevoid.tmf.core.helpers.ItemHelper;

public class PlayerTickHandler {

    @SubscribeEvent
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
