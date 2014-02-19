package slimevoid.compatibility.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import slimevoid.tmf.core.helpers.ItemHelper;
import thaumcraft.common.items.equipment.ItemElementalSword;

public class PlayerTickHandler {

    @ForgeSubscribe
    public void LivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) event.entity;
            if (entityplayer.isBlocking()) {
                ItemStack tool = ItemHelper.getSelectedTool(entityplayer.getHeldItem());
                if (tool != null
                    && tool.getItem() instanceof ItemElementalSword) {
                    entityplayer.motionY += 0.07999999821186066D;

                    if (entityplayer.motionY > 0.5D) {
                        entityplayer.motionY = 0.20000000298023224D;
                    }
                }
            }
        }
    }
}
