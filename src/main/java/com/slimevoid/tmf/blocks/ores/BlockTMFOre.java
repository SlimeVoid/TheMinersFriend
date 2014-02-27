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
package com.slimevoid.tmf.blocks.ores;

import com.slimevoid.tmf.core.lib.BlockLib;
import com.slimevoid.tmf.core.lib.CoreLib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

public class BlockTMFOre extends BlockOre {

    public int spawnLevel, spawnRate, spawnSize;

    public BlockTMFOre(int id, int spawnLevel, int spawnRate, int spawnSize) {
        super(id);
        this.spawnLevel = spawnLevel;
        this.spawnRate = spawnRate;
        this.spawnSize = spawnSize;
        BlockLib.registerTMFOre(this);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        this.textureName = name;
        return super.setUnlocalizedName(name);
    }

    @Override
    public String getTextureName() {
        return CoreLib.MOD_RESOURCES + ":" + this.textureName;
    }
}
