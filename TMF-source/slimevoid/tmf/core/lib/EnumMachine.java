package slimevoid.tmf.core.lib;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import slimevoid.tmf.client.renderers.handlers.BlockGrinderRenderer;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.machines.tileentities.TileEntityRefinery;
import slimevoidlib.tileentity.TileEntityBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public enum EnumMachine {
	
	REFINERY(0, BlockLib.BLOCK_REFINERY, TileEntityRefinery.class, true),
	GRINDER(1, BlockLib.BLOCK_GRINDER, TileEntityGrinder.class, new BlockGrinderRenderer(), true),
	GEOEQUIP(2, BlockLib.BLOCK_GEOEQUIPMENT, TileEntityGeologicalEquipment.class, true),
	AUTOMIXTABLE(3, BlockLib.BLOCK_AUTOMIXTABLE, TileEntityAutomaticMixingTable.class, false);
	
	int machineId;
	String machineName;
	boolean hasState;
	ISimpleBlockRenderingHandler renderHandler;
	Class <? extends TileEntityBase> _class;
	Icon[] iconList;
	
	EnumMachine(int tileId, String name, Class <? extends TileEntityBase> tileClass, boolean hasState) {
		this(tileId, name, tileClass, null, hasState);
	}
	
	EnumMachine(int tileId, String name, Class <? extends TileEntityBase> tileClass, ISimpleBlockRenderingHandler renderHandler, boolean hasState) {
		this.machineId = tileId;
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
		this.iconList[0] = iconRegister.registerIcon(this.getTextureName() + stateString + "_bottom");
		this.iconList[1] = iconRegister.registerIcon(this.getTextureName() + stateString + "_top");
		this.iconList[2] = iconRegister.registerIcon(this.getTextureName() + stateString + "_front");
		this.iconList[3] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
		this.iconList[4] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
		this.iconList[5] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
		if (this.hasState) {
			stateString = "_active";
			this.iconList[6] = iconRegister.registerIcon(this.getTextureName() + stateString + "_bottom");
			this.iconList[7] = iconRegister.registerIcon(this.getTextureName() + stateString + "_top");
			this.iconList[8] = iconRegister.registerIcon(this.getTextureName() + stateString + "_front");
			this.iconList[9] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
			this.iconList[10] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
			this.iconList[11] = iconRegister.registerIcon(this.getTextureName() + stateString + "_side");
		}
	}
	
	public static EnumMachine getMachine(int tileId) {
		return tileId >= 0 && tileId < EnumMachine.values().length ? EnumMachine.values()[tileId] : null;
	}

	public static void registerMachines() {
		for (EnumMachine machine : EnumMachine.values()) {
			TMFCore.blockMachineBase.addMapping(machine.machineId, machine._class, machine.machineName);
		}
	}

}
