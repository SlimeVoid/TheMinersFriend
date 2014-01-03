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
package slimevoid.tmf.core.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.EnumBlocks;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(	world,
							random,
							chunkX * 16,
							chunkZ * 16);
			break;
		case 0:
			generateSurface(world,
							random,
							chunkX * 16,
							chunkZ * 16);
			break;
		case 1:
			generateEnd(world,
						random,
						chunkX * 16,
						chunkZ * 16);
		}
	}

	private void generateNether(World world, Random random, int chunkX, int chunkZ) {
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for (EnumBlocks ore : EnumBlocks.values()) {
			if (ore.getUnlocalizedName().startsWith(BlockLib.ORE_PREFIX)) {
				int[] oreData = EnumBlocks.getOreData(ore.getId());
				if (oreData != null) {
					int spawnLevel = oreData[0];
					int spawnRate = oreData[1];
					int spawnSize = oreData[2];
					for (int i = 0; i < spawnRate; i++) {
						int xCoord = chunkX + random.nextInt(16);
						int yCoord = random.nextInt(spawnLevel);
						int zCoord = chunkZ + random.nextInt(16);
						WorldGenMinable minable = new WorldGenMinable(TMFCore.blockBaseId, ore.getId(), spawnSize);
						minable.generate(	world,
											random,
											xCoord,
											yCoord,
											zCoord);
					}
				} else {
					FMLCommonHandler.instance().getFMLLogger().warning("Tried to generate an ore without any data and failed!");
				}
			}
		}
	}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}

}