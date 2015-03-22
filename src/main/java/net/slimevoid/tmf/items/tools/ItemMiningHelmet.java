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
package net.slimevoid.tmf.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.blocks.BlockTransientLight;
import net.slimevoid.tmf.core.TMFCore;
import net.slimevoid.tmf.core.creativetabs.CreativeTabTMF;

public class ItemMiningHelmet extends ItemMiningArmor {

    public ItemMiningHelmet(int itemId, ArmorMaterial material, int renderIndex, String name, String texture) {
        super(itemId, material, renderIndex, 0, name, texture);
        this.setCreativeTab(CreativeTabTMF.tabTMF);
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public IIcon getIconFromDamageForRenderPass(int damage, int renderPass) {
//        return renderPass == 1 ? this.itemIcon : super.getIconFromDamageForRenderPass(damage,
//                                                                                      renderPass);
//    }

//    @Override
//    public IIcon getIconFromDamage(int par1) {
//        return this.itemIcon;
//    }

    @Override
    protected void onPlayerUpdate(World world, EntityPlayer entityplayer) {
        int x = MathHelper.floor_double(entityplayer.posX);
        // System.out.println(entityplayer.posX + " | " + x);
        int y = MathHelper.floor_double(entityplayer.posY) + 1;
        // System.out.println(entityplayer.posY + " | " + (y - 1));
        int z = MathHelper.floor_double(entityplayer.posZ);
        // System.out.println(entityplayer.posZ + " | " + z);
        BlockTransientLight.setBlock(
                world,
                TMFCore.blockMiningLamp.getDefaultState(),
                new BlockPos(x, y, z));
    }

    @Override
    protected boolean hasPlayerUpdate() {
        return true;
    }
}
