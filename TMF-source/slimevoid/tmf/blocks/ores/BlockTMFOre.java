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
