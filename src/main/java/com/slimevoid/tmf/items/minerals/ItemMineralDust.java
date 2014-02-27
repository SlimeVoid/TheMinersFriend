/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package com.slimevoid.tmf.items.minerals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class ItemMineralDust extends ItemMineral {

    public ItemMineralDust(int id) {
        super(id);
        this.setPotionEffect(PotionHelper.spiderEyeEffect);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (player.canEat(true)) {
            player.setItemInUse(item,
                                this.getMaxItemUseDuration(item));
        }

        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 32;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.getFoodStats().addStats(0,
                                       0);
        world.playSoundAtEntity(player,
                                "random.burp",
                                0.5F,
                                world.rand.nextFloat() * 0.1F + 0.9F);

        float strength = 1.0F;
        world.createExplosion(player,
                              player.posX,
                              player.posY,
                              player.posZ,
                              strength,
                              true);
        player.setVelocity(0,
                           5,
                           0);
        return stack;
    }
}
