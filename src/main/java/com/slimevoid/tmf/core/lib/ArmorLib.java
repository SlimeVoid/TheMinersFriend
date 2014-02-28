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
package com.slimevoid.tmf.core.lib;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoidlib.data.Logger;

import com.slimevoid.tmf.core.LoggerTMF;
import com.slimevoid.tmf.items.tools.ItemMiningHelmet;

public class ArmorLib {

    private static HashMap<Integer, String> armorTextures = new HashMap<Integer, String>();

    public static String getArmorTextureFromItemStack(ItemStack itemstack) {
        return getArmorTextureFromItemID(itemstack.itemID);
    }

    public static String getArmorTextureFromItemID(int itemID) {
        if (armorTextures.containsKey(itemID)) {
            return armorTextures.get(itemID);
        } else {
            LoggerTMF.getInstance("ArmorLib").write(true,
                                                    "Failed to get Texture file for ID ["
                                                            + itemID + "]",
                                                    Logger.LogLevel.DEBUG);
        }
        return "";
    }

    public static String registerArmorTexture(Item item, String textureFile) {
        if (!armorTextures.containsKey(item.itemID)) {
            armorTextures.put(item.itemID,
                              textureFile);
            return getArmorTextureFromItemID(item.itemID);
        } else {
            LoggerTMF.getInstance("ArmorLib").write(true,
                                                    "Texture file for item ["
                                                            + item.getUnlocalizedName()
                                                            + "] already registered",
                                                    Logger.LogLevel.DEBUG);
        }
        return "";
    }

    private static ItemStack getPlayerArmorInSlot(int slot, EntityPlayer entityplayer, World world, Class<? extends ItemArmor> armorClass) {
        ItemStack armorPiece = entityplayer.getCurrentArmor(slot);
        if (armorPiece != null && armorClass.isInstance(armorPiece.getItem())) {
            return armorPiece;
        }
        return null;
    }

    public static ItemStack getPlayerHelm(EntityPlayer entityplayer, World world) {
        return getPlayerArmorInSlot(3,
                                    entityplayer,
                                    world,
                                    ItemMiningHelmet.class);
    }

    public static double getDamageToHelm(ItemStack miningHelm) {
        switch (((ItemArmor) miningHelm.getItem()).getArmorMaterial().ordinal()) {
        case 5:
            return 0.2;
        case 4:
            return 0.4;
        case 3:
            return 0.6;
        case 2:
            return 0.8;
        default:
            return 1;
        }
    }
}
