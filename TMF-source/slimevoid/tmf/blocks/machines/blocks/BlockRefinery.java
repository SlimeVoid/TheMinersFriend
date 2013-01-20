package slimevoid.tmf.blocks.machines.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRefinery extends BlockMachine {
	
	public BlockRefinery(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
}
