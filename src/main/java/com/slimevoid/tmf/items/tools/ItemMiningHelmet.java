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
package com.slimevoid.tmf.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.slimevoid.library.blocks.BlockTransientLight;
import com.slimevoid.tmf.core.TMFCore;
import com.slimevoid.tmf.core.creativetabs.CreativeTabTMF;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMiningHelmet extends ItemMiningArmor {

    public ItemMiningHelmet(int itemID, EnumArmorMaterial material, int renderIndex) {
        super(itemID, material, renderIndex, 0);
        this.setCreativeTab(CreativeTabTMF.tabTMF);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        return renderPass == 1 ? this.itemIcon : super.getIconFromDamageForRenderPass(damage,
                                                                                      renderPass);
    }

    @Override
    public Icon getIconFromDamage(int par1) {
        return this.itemIcon;
    }

    @Override
    protected void onPlayerUpdate(World world, EntityPlayer entityplayer) {
        int x = MathHelper.floor_double(entityplayer.posX);
        // System.out.println(entityplayer.posX + " | " + x);
        int y = MathHelper.floor_double(entityplayer.posY) + 1;
        // System.out.println(entityplayer.posY + " | " + (y - 1));
        int z = MathHelper.floor_double(entityplayer.posZ);
        // System.out.println(entityplayer.posZ + " | " + z);
        BlockTransientLight.setBlock(TMFCore.blockMiningLamp.blockID,
                                     x,
                                     y,
                                     z,
                                     world);
    }

    @Override
    protected boolean hasPlayerUpdate() {
        return true;
    }
}
