package slimevoid.tmf.core.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import slimevoid.tmf.blocks.ores.BlockTMFOre;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneration implements IWorldGenerator {
	
	private static List<BlockTMFOre> ores;
	
	public static void init() {
		ores = new ArrayList<BlockTMFOre>();
	}
	
	public static void registerTMFOre(BlockTMFOre ore) {
		if (!ores.contains(ore)) {
			ores.add(ore);
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
			case -1 :
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 0 :
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 1 :
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateNether(World world, Random random, int chunkX,
			int chunkZ) {
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for (BlockTMFOre ore : ores) {
			for (int i = 0; i < ore.spawnRate; i++) {
				int xCoord = chunkX + random.nextInt(16);
				int yCoord = random.nextInt(ore.spawnLevel);
				int zCoord = chunkZ + random.nextInt(16);
				WorldGenMinable minable = new WorldGenMinable(ore.blockID, 5);
				minable.generate(world, random, xCoord, yCoord, zCoord);
			}
		}
	}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}

}