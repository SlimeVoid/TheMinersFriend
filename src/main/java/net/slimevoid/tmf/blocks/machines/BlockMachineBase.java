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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.slimevoid.library.IEnumBlockType;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.library.blocks.BlockSimpleBase;
import net.slimevoid.library.blocks.BlockStates;
import net.slimevoid.library.items.ItemBlockBase;
import net.slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import net.slimevoid.tmf.core.lib.ConfigurationLib;

import java.util.List;

public class BlockMachineBase extends BlockSimpleBase {

    protected static PropertyEnum VARIANT = PropertyEnum.create("variant", BlockTypeMachine.class);
    public static PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachineBase() {
        super(Material.rock);
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabTMF.tabTMF;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        if (item instanceof ItemBlockBase) {
            for (int i = 0; i < ((ItemBlockBase) item).getValidItemBlocks().size(); i++) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    public boolean isCube() {
        return true;
    }

    @Override
    public int getRenderType() {
        return ConfigurationLib.renderMachineId;
    }

    @Override
    protected void setActualDefaultState() {
        this.setDefaultState(this.getInitialState(this.blockState.getBaseState().withProperty(BlockStates.FACING, EnumFacing.NORTH).withProperty(this.getBlockTypeProperty(), this.getDefaultBlockType())));
    }

    protected IBlockState getInitialState(IBlockState state) {
        return state.withProperty(ACTIVE, false);
    }

    protected PropertyEnum getBlockTypeProperty() {
        return VARIANT;
    }

    protected IProperty[] getPropertyList() {
        return new IProperty[] {ACTIVE, BlockStates.FACING, VARIANT};
    }

    protected Comparable<? extends IEnumBlockType> getDefaultBlockType() {
        return BlockTypeMachine.REFINERY;
    }

    protected Comparable<? extends IEnumBlockType> getBlockType(int meta) {
        return BlockTypeMachine.getMachine(meta);
    }

}
