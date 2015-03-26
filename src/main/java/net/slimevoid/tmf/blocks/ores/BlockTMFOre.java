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
package net.slimevoid.tmf.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.slimevoid.tmf.core.lib.BlockLib;

public class BlockTMFOre extends BlockOre {

    public int spawnLevel, spawnRate, spawnSize;

    public BlockTMFOre(int spawnLevel, int spawnRate, int spawnSize) {
        super();
        this.spawnLevel = spawnLevel;
        this.spawnRate = spawnRate;
        this.spawnSize = spawnSize;
        this.setHarvestLevel("pickaxe",
                2);
        BlockLib.registerTMFOre(this);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        //this.textureName = name;
        return super.setUnlocalizedName(name);
    }

//    @Override
//    public String getTextureName() {
//        return CoreLib.MOD_ID + ":" + this.textureName;
//    }
}
