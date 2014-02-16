package slimevoid.tmf.client.tickhandlers;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.items.tools.ItemMiningToolBelt;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class PlayerToolBeltTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            EntityPlayer entityplayer = (EntityPlayer) tickData[0];
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

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return "The Miners Friend Player Tick Handler";
    }

}
