package slimevoid.tmf.core.world;

import java.util.HashMap;
import java.util.Random;

import slimevoid.tmf.blocks.ores.BlockTMFOre;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneration implements IWorldGenerator {
	
	HashMap<BlockTMFOre, Integer> oreFrequencies;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
			case -1 :
				generateNether(world, random, chunkX, chunkZ);
				break;
			case 0 :
				generateSurface(world, random, chunkX, chunkZ);
				break;
			case 1 :
				generateEnd(world, random, chunkX, chunkZ);
		}
	}

	private void generateNether(World world, Random random, int chunkX,
			int chunkZ) {
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
	}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}

}