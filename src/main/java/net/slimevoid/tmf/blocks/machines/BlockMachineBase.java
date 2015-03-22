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
package net.slimevoid.tmf.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import net.slimevoid.tmf.core.lib.ConfigurationLib;

public class BlockMachineBase extends BlockBase {

    @Override
    public void registerIcons(IIconRegister IIconRegister) {
        for (int i = 0; i < this.tileEntityMap.length; i++) {
            EnumMachine machine = EnumMachine.getMachine(i);
            if (machine != null) {
                machine.registerIcons(IIconRegister);
            }
        }
    }

    public BlockMachineBase(int blockId) {
        super(Material.rock, EnumMachine.values().length);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        IIcon icon = null;
        EnumMachine machine = EnumMachine.getMachine(metadata);
        if (machine != null) {
            icon = machine.getIcon(side);
        }
        return icon != null ? icon : this.blockIcon;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabTMF.tabTMF;
    }

    @Override
    public int getRenderType() {
        return ConfigurationLib.renderMachineId;
    }

    @Override
    public IIcon[] registerBottomIcons(IIconRegister iconRegister) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIcon[] registerTopIcons(IIconRegister iconRegister) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIcon[] registerFrontIcons(IIconRegister iconRegister) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIcon[] registerSideIcons(IIconRegister iconRegister) {
        // TODO Auto-generated method stub
        return null;
    }

}
