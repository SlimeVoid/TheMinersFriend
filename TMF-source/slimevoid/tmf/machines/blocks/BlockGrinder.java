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
package slimevoid.tmf.machines.blocks;

import slimevoid.tmf.core.lib.ConfigurationLib;

public class BlockGrinder extends BlockMachineBase {

	public BlockGrinder(int id, String textureName, boolean isActive) {
		super(id);
		this.setBlockBounds(
				0.1875f, 0f, 0.1875f, 
				0.8125f, 0.9f, 0.8125f		
		);
	}

	@Override
	public int getRenderType() {
		return ConfigurationLib.renderMachineId;
	}
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
