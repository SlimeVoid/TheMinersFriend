package slimevoid.tmf.blocks.ores.tileentities;

import slimevoid.tmf.core.TMFCore;
import slimevoidlib.tileentity.TileEntityBase;

public abstract class TileEntityOreBase extends TileEntityBase {

	@Override
	public int getBlockID() {
		return TMFCore.blockBaseId;
	}

}
