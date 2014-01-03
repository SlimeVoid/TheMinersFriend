package slimevoid.tmf.core.lib;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.blocks.ores.tileentities.TileEntityOreArkite;
import slimevoid.tmf.blocks.ores.tileentities.TileEntityOreBistite;
import slimevoid.tmf.blocks.ores.tileentities.TileEntityOreCrokere;
import slimevoid.tmf.blocks.ores.tileentities.TileEntityOreDernite;
import slimevoid.tmf.blocks.ores.tileentities.TileEntityOreEgioclase;
import slimevoid.tmf.client.renderers.handlers.BlockGrinderRenderer;
import slimevoid.tmf.core.TMFCore;
import slimevoidlib.tileentity.TileEntityBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public enum EnumBlocks {

	MACHINE_REFINERY(BlockLib.BLOCK_REFINERY, TileEntityRefinery.class, true),
	MACHINE_GRINDER(BlockLib.BLOCK_GRINDER, TileEntityGrinder.class, new BlockGrinderRenderer(), true),
	MACHINE_GEOEQUIP(BlockLib.BLOCK_GEOEQUIPMENT, TileEntityGeologicalEquipment.class, true),
	MACHINE_AUTOMIXTABLE(BlockLib.BLOCK_AUTOMIXTABLE, TileEntityAutomaticMixingTable.class),
	ORE_ARKITE(BlockLib.ORE_ARKITE, TileEntityOreArkite.class, new Object[] {
			"128",
			"20",
			"5" }),
	ORE_BISTITE(BlockLib.ORE_BISTITE, TileEntityOreBistite.class, new Object[] {
			"64",
			"15",
			"5" }),
	ORE_CROKERE(BlockLib.ORE_CROKERE, TileEntityOreCrokere.class, new Object[] {
			"48",
			"15",
			"5" }),
	ORE_DERNITE(BlockLib.ORE_DERNITE, TileEntityOreDernite.class, new Object[] {
			"26",
			"10",
			"5" }),
	ORE_EGIOCLASE(BlockLib.ORE_EGIOCLASE, TileEntityOreEgioclase.class, new Object[] {
			"5",
			"5",
			"5" });

	private int								blockId	= this.ordinal();
	private String							blockName;
	private Object[]						data	= null;
	private boolean							hasState;
	private ISimpleBlockRenderingHandler	renderHandler;
	private Class<? extends TileEntityBase>	_class;
	private Icon[]							iconList;

	EnumBlocks(String name, Class<? extends TileEntityBase> tileClass) {
		this(name, tileClass, false);
	}

	EnumBlocks(String name, Class<? extends TileEntityBase> tileClass, Object[] object) {
		this(name, tileClass, false);
		this.data = object;
	}

	EnumBlocks(String name, Class<? extends TileEntityBase> tileClass, boolean hasState) {
		this(name, tileClass, null, hasState);
	}

	EnumBlocks(String name, Class<? extends TileEntityBase> tileClass, ISimpleBlockRenderingHandler renderHandler, boolean hasState) {
		this.blockName = name;
		this._class = tileClass;
		this.renderHandler = renderHandler;
		this.hasState = hasState;
		int icons = hasState ? 12 : 6;
		this.iconList = new Icon[icons];
	}

	public int getId() {
		return this.blockId;
	}

	public String getTextureName() {
		return CoreLib.MOD_RESOURCES + ":" + this.blockName;
	}

	public String getUnlocalizedName() {
		return this.blockName;
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

	public Object[] getData() {
		return this.data;
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

	public static EnumBlocks getBlock(int tileId) {
		return tileId >= 0 && tileId < EnumBlocks.values().length ? EnumBlocks.values()[tileId] : null;
	}

	public static void registerBlocks() {
		for (EnumBlocks block : EnumBlocks.values()) {
			TMFCore.blockBase.addMapping(	block.blockId,
											block._class,
											block.blockName);
		}
	}

	public static int[] getOreData(int tileId) {
		EnumBlocks ore = getBlock(tileId);
		Object[] raw = ore.getData();
		if (raw != null) {
			int i = 0;
			if (raw[i] instanceof String) {
				int[] oreData = new int[raw.length];
				for (i = 0; i < oreData.length; i++) {
					oreData[i] = Integer.parseInt((String) raw[i]);
					i++;
				}
				return oreData;
			}
		}
		return null;
	}
}
