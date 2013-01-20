package slimevoid.tmf.blocks.ores;

import net.minecraft.block.BlockOre;

public class BlockTMFOre extends BlockOre {
	
	public int 
		spawnLevel,
		spawnRate,
		spawnSize,
		lightLevel;

	public BlockTMFOre(int id, int blockIndexInTexture, int spawnLevel, int spawnRate, int spawnSize, int lightLevel) {
		super(id, blockIndexInTexture);
		this.spawnLevel = spawnLevel;
		this.spawnRate = spawnRate;
		this.spawnSize = spawnSize;
		this.lightLevel = lightLevel;
	}

}
