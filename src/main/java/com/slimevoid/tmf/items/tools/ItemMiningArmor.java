package com.slimevoid.tmf.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public abstract class ItemMiningArmor extends ItemArmor {

    public ItemMiningArmor(int itemID, EnumArmorMaterial material, int renderIndex, int armorType) {
        super(itemID, material, renderIndex, armorType);
        if (hasPlayerUpdate()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @ForgeSubscribe
    public void onEntityUpdate(LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) event.entityLiving;

            ItemStack armor = entityplayer.getCurrentArmor(3 - armorType);
            if (armor != null && armor.getItem() == this) {
                this.onPlayerUpdate(entityplayer.worldObj,
                                    entityplayer);
            }
        }
    }

    protected abstract boolean hasPlayerUpdate();

    protected void onPlayerUpdate(World world, EntityPlayer entityplayer) {
    }

}
