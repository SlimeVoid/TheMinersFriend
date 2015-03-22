package net.slimevoid.tmf.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class ItemMiningArmor extends ItemArmor {

    public ItemMiningArmor(int itemID, ArmorMaterial material, int renderIndex, int armorType, String name, String texture) {
        super(material, renderIndex, armorType);
        if (hasPlayerUpdate()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
        this.setUnlocalizedName(name);
        //this.setTextureName(texture);
    }

    @SubscribeEvent
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
