package slimevoid.tmf.client.tickhandlers;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.helpers.ItemHelper;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class TickHandlerPlayer implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            EntityPlayer entityplayer = (EntityPlayer) tickData[0];
            World world = entityplayer.worldObj;
            if (entityplayer.isUsingItem()) {
                ItemStack heldItem = entityplayer.getHeldItem();
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
        }
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
