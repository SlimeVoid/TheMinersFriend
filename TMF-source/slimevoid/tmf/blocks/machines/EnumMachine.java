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
package slimevoid.tmf.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityStove;
import slimevoid.tmf.client.renderers.handlers.BlockGrinderRenderer;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.BlockLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoidlib.tileentity.TileEntityBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public enum EnumMachine {

	REFINERY(BlockLib.BLOCK_REFINERY, TileEntityRefinery.class, true),
	GRINDER(BlockLib.BLOCK_GRINDER, TileEntityGrinder.class, new BlockGrinderRenderer(), true),
	GEOEQUIP(BlockLib.BLOCK_GEOEQUIPMENT, TileEntityGeologicalEquipment.class, true),
	AUTOMIXTABLE(BlockLib.BLOCK_AUTOMIXTABLE, TileEntityAutomaticMixingTable.class, false),
	STOVE(BlockLib.BLOCK_COOKER, TileEntityStove.class, true);

	private int								machineId	= this.ordinal();
	private String							machineName;
	private boolean							hasState;
	private ISimpleBlockRenderingHandler	renderHandler;
	private Class<? extends TileEntityBase>	_class;
	private Icon[]							iconList;

	EnumMachine(String name, Class<? extends TileEntityBase> tileClass, boolean hasState) {
		this(name, tileClass, null, hasState);
	}

	EnumMachine(String name, Class<? extends TileEntityBase> tileClass, ISimpleBlockRenderingHandler renderHandler, boolean hasState) {
		this.machineName = name;
		this._class = tileClass;
		this.renderHandler = renderHandler;
		this.hasState = hasState;
		int icons = hasState ? 12 : 6;
		this.iconList = new Icon[icons];
	}

	public int getId() {
		return this.machineId;
	}

	public String getTextureName() {
		return CoreLib.MOD_RESOURCES + ":" + this.machineName;
	}

	public String getUnlocalizedName() {
		return this.machineName;
	}

	public boolean hasRenderHandler() {
		return this.renderHandler != null;
	}

	public ISimpleBlockRenderingHandler getRenderHandler() {
		return this.renderHandler;
	}

	public boolean hasState() {
		return this.hasState;
	}

	public Icon getIcon(int side) {
		if (side >= 0 && side < this.iconList.length) {
			return this.iconList[side];
		}
		return null;
	}

	public void registerIcons(IconRegister iconRegister) {
		String stateString = "";
		if (this.hasState) {
			stateString = "_idle";
		}
		this.iconList[0] = iconRegister.registerIcon(this.getTextureName()
														+ stateString
														+ "_bottom");
		this.iconList[1] = iconRegister.registerIcon(this.getTextureName()
														+ stateString + "_top");
		this.iconList[2] = iconRegister.registerIcon(this.getTextureName()
														+ stateString
														+ "_front");
		this.iconList[3] = iconRegister.registerIcon(this.getTextureName()
														+ stateString + "_side");
		this.iconList[4] = iconRegister.registerIcon(this.getTextureName()
														+ stateString + "_side");
		this.iconList[5] = iconRegister.registerIcon(this.getTextureName()
														+ stateString + "_side");
		if (this.hasState) {
			stateString = "_active";
			this.iconList[6] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_bottom");
			this.iconList[7] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_top");
			this.iconList[8] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_front");
			this.iconList[9] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_side");
			this.iconList[10] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_side");
			this.iconList[11] = iconRegister.registerIcon(this.getTextureName()
															+ stateString
															+ "_side");
		}
	}

	public static EnumMachine getMachine(int tileId) {
		return tileId >= 0 && tileId < EnumMachine.values().length ? EnumMachine.values()[tileId] : null;
	}

	public static void registerMachines() {
		for (EnumMachine machine : EnumMachine.values()) {
			TMFCore.blockMachineBase.addMapping(machine.machineId,
												machine._class,
												machine.machineName);
		}
	}

}
