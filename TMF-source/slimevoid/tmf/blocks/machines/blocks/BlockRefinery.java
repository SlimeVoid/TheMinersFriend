package slimevoid.tmf.blocks.machines.blocks;

import java.util.Random;

import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.core.TMFCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRefinery extends BlockMachine {
	
	public BlockRefinery(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRefinery();
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return TMFCore.refineryIdleId;
	}

	public static void updateRefineryBlockState(boolean isBurning, World world, int x, int y, int z) {
		
	}
}
