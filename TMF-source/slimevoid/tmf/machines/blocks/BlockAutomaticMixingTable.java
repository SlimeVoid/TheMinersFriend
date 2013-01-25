package slimevoid.tmf.machines.blocks;

import java.util.Random;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.machines.tileentities.TileEntityAutomaticMixingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAutomaticMixingTable extends BlockMachine {

	public BlockAutomaticMixingTable(int id, int texX, int texY, boolean isActive) {
		super(id, texX, texY, isActive);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		// TODO :: MixingTable : idDropped idle
		return TMFCore.autoMixTableId;
	}
	
	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		// Never happens.
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float d) {
		// TODO :: MixingTable : onBlockActivated GUIID
		/*
		player.openGui(
				TheMinersFriend.instance,
				GuiLib.MIXINGTABLE_GUIID,
				world,
				x,
				y,
				z
		);
		*/
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityAutomaticMixingTable();
	}

}
