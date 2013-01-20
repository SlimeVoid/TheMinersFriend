package slimevoid.tmf.blocks.ores;

import java.util.List;

import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.SpriteLib;
import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockTMFOre extends BlockOre {

	public int spawnLevel, spawnRate, spawnSize, veinSize, lightLevel;

	public BlockTMFOre(
			int id,
			int spawnLevel,
			int spawnRate,
			int spawnSize,
			int veinSize,
			int lightLevel) {
		super(id, 0);
		this.spawnLevel = spawnLevel;
		this.spawnRate = spawnRate;
		this.spawnSize = spawnSize;
		this.veinSize = veinSize;
		this.lightLevel = lightLevel;
		BlockLib.registerTMFOre(this);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		if (meta == 0) {
			return this.blockIndexInTexture;
		} else {
			meta = ~(meta & 15);
			return 113 + ((meta & 8) >> 3) + (meta & 7) * 16;
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	public String getTextureFile() {
		return SpriteLib.RESOURCE_SPRITE_PATH;
	}

	public void getSubBlocks(int blockId, CreativeTabs creativetabs,
			List blockList) {
		for (int i = 0; i < BlockLib.getOreCount(); ++i) {
			blockList.add(new ItemStack(blockId, 1, i));
		}
	}
}
