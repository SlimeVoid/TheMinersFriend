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
package slimevoid.tmf.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import slimevoid.tmf.core.creativetabs.CreativeTabTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.EnumBlocks;
import slimevoidlib.blocks.BlockBase;

public class BlockTMFBase extends BlockBase {

	@Override
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < this.tileEntityMap.length; i++) {
			EnumBlocks block = EnumBlocks.getBlock(i);
			if (block != null) {
				block.registerIcons(iconRegister);
			}
		}
	}

	public BlockTMFBase(int id) {
		super(id, Material.rock, EnumBlocks.values().length);
	}

	@Override
	public Icon getIcon(int side, int metadata) {
		Icon icon = null;
		EnumBlocks machine = EnumBlocks.getBlock(metadata);
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

}
