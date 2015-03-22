package net.slimevoid.tmf.tickhandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import net.slimevoid.tmf.core.lib.ConfigurationLib;
import net.slimevoid.tmf.items.tools.ItemMiningToolBelt;

public class ToolBeltTickHandler {

    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            EntityPlayer entityplayer = event.player;
            World world = entityplayer.worldObj;
            ItemStack heldItem = entityplayer.getHeldItem();
            if (ItemHelper.isToolBelt(heldItem)) {
                /**
                 * Here we check if the tool belt is set as the item in use We
                 * need to do this in order to satisfy the vanilla 'default'
                 */
                if (entityplayer.isUsingItem()) {
                    ItemStack itemInUse = (ItemStack) ReflectionHelper.getPrivateValue(EntityPlayer.class,
                            entityplayer,
                            ItemHelper.getItemInUseFieldId(world,
                                    entityplayer));
                    if (!(heldItem == itemInUse)) {
                        if (isHoldingAndUsingToolBelt(heldItem,
                                                      itemInUse)
                            && isUsingSelectedTool(heldItem,
                                                   itemInUse)) {
                            ReflectionHelper.setPrivateValue(EntityPlayer.class,
                                                             entityplayer,
                                                             heldItem,
                                                             ItemHelper.getItemInUseFieldId(world,
                                                                                            entityplayer));
                        }
                    }
                }
                /**
                 * Here we check if the player is swinging, if they are see if
                 * they are holding a Tool Belt Once those checks are satisfied,
                 * check if Mining mode is enabled if it is, start a counter
                 * When the counter hits a critical value remove some saturation
                 */
                if (entityplayer.isSwingInProgress) {
                    if (((ItemMiningToolBelt) heldItem.getItem()).isMiningModeEnabled(heldItem)) {
                        entityplayer.addExhaustion(this.calculateExhaustion(world,
                                                                            entityplayer,
                                                                            heldItem));
                    }
                }
            }
        }
    }

    private float calculateExhaustion(World world, EntityPlayer entityplayer, ItemStack heldItem) {
        float progress = entityplayer.swingProgress;
        if (progress >= 0.5F) {
            return ConfigurationLib.miningModeExhaustion;
        }
        return 0;
    }

    private boolean isUsingSelectedTool(ItemStack heldItem, ItemStack itemInUse) {
        return ItemHelper.getSelectedSlot(heldItem) == ItemHelper.getSelectedSlot(itemInUse);
    }

    private boolean isHoldingAndUsingToolBelt(ItemStack heldItem, ItemStack itemInUse) {
        return ItemHelper.isToolBelt(heldItem)
               && ItemHelper.isToolBelt(itemInUse);
    }

}
