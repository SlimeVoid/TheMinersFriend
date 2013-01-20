package slimevoid.tmf.blocks.ores;

import slimevoid.tmf.core.lib.SpriteLib;
import slimevoid.tmf.core.world.WorldGeneration;
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
		WorldGeneration.registerTMFOre(this);
	}

	public String getTextureFile() {
		return SpriteLib.RESOURCE_SPRITE_PATH;
	}
}
