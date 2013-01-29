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
package slimevoid.tmf.blocks.ores;

import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.ResourceLib;
import net.minecraft.block.BlockOre;

public class BlockTMFOre extends BlockOre {

	public int spawnLevel, spawnRate, spawnSize;
	public float lightLevel;

	public BlockTMFOre(
			int id,
			int blockIndexInTexture,
			int spawnLevel,
			int spawnRate,
			int spawnSize,
			float lightLevel) {
		super(id, blockIndexInTexture);
		this.spawnLevel = spawnLevel;
		this.spawnRate = spawnRate;
		this.spawnSize = spawnSize;
		this.lightLevel = lightLevel;
		this.setLightValue(lightLevel);
		BlockLib.registerTMFOre(this);
	}

	public String getTextureFile() {
		return ResourceLib.RESOURCE_SPRITE_PATH;
	}
}
