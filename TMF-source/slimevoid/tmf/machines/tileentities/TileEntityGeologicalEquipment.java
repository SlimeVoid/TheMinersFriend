package slimevoid.tmf.machines.tileentities;

import java.util.HashMap;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.machines.blocks.BlockGeologicalEquipment;
import slimevoid.tmf.minerals.items.ItemMineral;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGeologicalEquipment extends TileEntityMachine {
	/**
	 * Fuel
	 */
	private ItemStack fuelStack;
	
	/**
	 * The width of the Geo scan in relation to this xCoord and zCoord
	 */
	private int maxScanWidth;
	
	/**
	 * How many levels that Geo Equipment can scan along yCoord
	 */
	private int maxScanDepth;
	
	/**
	 * The current level of scan
	 */
	public int currentLevel;
	public int currentLevelIdx;
	
	/**
	 * The scan speed based on fuel
	 */
	private int scanSpeed;
	
	/**
	 * Survey data
	 * 
	 * Idea is to store each level (Integer) with Array of blocks of scanSize
	 */
	private HashMap<Integer, Block[]> surveyData;
	
	private boolean hasOre;

	public TileEntityGeologicalEquipment() {
		super();
		this.surveyData = new HashMap<Integer, Block[]>();
		this.hasOre = false;
		currentLevelIdx = 9;
	}
	
	private void gotoNextLevel() {
		currentLevelIdx = 0;
		if ( currentLevel == 0 ) {
			currentLevel = this.yCoord - 1;
		} else {
			currentLevel--;
		}
	}

	public void scan(int depth, int idx) {
		Block block = getBlockAt(
				worldObj,
				xCoord+indexToRelativeX(idx),
				depth,
				zCoord+indexToRelativeZ(idx)
		);

		if ( block != null ) {
			addSurveyData(
					depth,
					idx,
					block
			);
		}
	}
	
	private int indexToRelativeX(int idx) {
		switch ( idx ) {
			case 3:
				return -1;
			case 4:
				return 1;
			case 5:
				return -1;
			case 6:
				return 1;
			case 7:
				return -1;
			case 8:
				return 1;
			default:
				return 0;
		}
	}
	private int indexToRelativeZ(int idx) {
		switch ( idx ) {
			case 1:
				return -1;
			case 2:
				return 1;
			case 5:
				return -1;
			case 6:
				return -1;
			case 7:
				return 1;
			case 8:
				return 1;
			default:
				return 0;
		}
	}
	public void addSurveyData(int depth, int idx, Block block) {
		if ( (worldObj != null && depth >= worldObj.getHeight()) || (depth -  this.maxScanDepth) <= 0)
			return;
		
		if ( !surveyData.containsKey(depth) ) {
			surveyData.put(depth, new Block[9]);
		}
		
		surveyData.get(depth)[idx] = block;
	}

	public Block getBlockAt(World world, int x, int y, int z) {
		return Block.blocksList[world.getBlockId(x, y, z)];
	}
	
	public Block getSurveyResult(int depth, int cell) {
		Block[] map = getSurveyResult(depth);
		if ( map != null && map.length >= cell )
			return map[cell];
		return null;
	}
	public Block[] getSurveyResult(int depth) {
		return surveyData.get(depth);
	}
	
	public boolean oreFound() {
		return hasOre;
	}
	
	public Block[] getBlocksAt(int level) {
		if (surveyData.containsKey(level)) {
			return surveyData.get(level);
		}
		return null;
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if ( index == 0 )
			return fuelStack;
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		fuelStack = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "container.tmf.geoequip";
	}

	@Override
	public void readFromNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = ntbCompound.getTagList("Items");
		if ( items.tagCount() > 0 ) {
			NBTTagCompound itemInSlot = (NBTTagCompound)items.tagAt(0);
			fuelStack = ItemStack.loadItemStackFromNBT(itemInSlot);
		}

		surveyData = new HashMap<Integer, Block[]>();
		NBTTagList survey = ntbCompound.getTagList("Survey");
		for (int i = 0; i < survey.tagCount(); ++i) {
			NBTTagList depthTag = (NBTTagList) survey.tagAt(i);

			NBTTagCompound depthData = (NBTTagCompound)depthTag.tagAt(0);
			int depth = depthData.getInteger("Depth");
			
			for ( int j = 1; j <= 9; j++ ) {
				NBTTagCompound block = (NBTTagCompound)depthTag.tagAt(j);
				int blockId = block.getInteger("Block");
				
				if ( blockId >= 0 )
					addSurveyData(depth, j-1, Block.blocksList[blockId]);
			}
		}
		
		currentLevel = ntbCompound.getInteger("CurrentLevel");
		currentLevelIdx = ntbCompound.getInteger("CurrentLevelIdx");
		
		super.readFromNBT(ntbCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound ntbCompound) {
		NBTTagList items = new NBTTagList();
		if ( fuelStack != null ) {
			NBTTagCompound itemInSlot = new NBTTagCompound();
			fuelStack.writeToNBT(itemInSlot);
			items.appendTag(itemInSlot);
		}
		ntbCompound.setTag("Items", items);

		NBTTagList survey = new NBTTagList();
		for (int depth: surveyData.keySet() ) {
			if (surveyData.get(depth) != null) {
				NBTTagList depthTag = new NBTTagList();
				NBTTagCompound depthData = new NBTTagCompound();
				depthData.setInteger("Depth", depth);
				depthTag.appendTag(depthData);
				
				for ( int idx = 0; idx < surveyData.get(depth).length; idx++ ) {
					NBTTagCompound blockId = new NBTTagCompound();
					if ( surveyData.get(depth)[idx] == null ) {
						blockId.setInteger("Block", -1);
					} else {
						blockId.setInteger("Block", surveyData.get(depth)[idx].blockID);
					}
					depthTag.appendTag(blockId);
				}
				survey.appendTag(depthTag);
			}
		}
		ntbCompound.setTag("Survey", survey);

		ntbCompound.setInteger("CurrentLevel", currentLevel);
		ntbCompound.setInteger("CurrentLevelIdx", currentLevelIdx);
		
		super.writeToNBT(ntbCompound);
		
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void smeltItem() {
		if ( canSmelt() ) {
			for ( int i=0; i < this.currentItemWidth; i++ ) {
				if ( currentLevelIdx > 8) {
					gotoNextLevel();
				}
				scan(currentLevel, currentLevelIdx);
				
				currentLevelIdx++;
			}
		}
	}

	@Override
	protected boolean canSmelt() {
		return true;
	}
	
	// Override to force TMF fuel.
	@Override
	public int getItemBurnTime(ItemStack stack) {
		if ( stack == null )
			return 0;

		if ( stack.getItem() instanceof IFuelHandlerTMF ) 
			return ((ItemMineral)stack.getItem()).getBurnTime(stack);

		return 0;
	}
	
	@Override
	public int getCurrentFuelBurnTime() {
		return getItemBurnTime(fuelStack);
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(fuelStack);
	}

	@Override
	public int getCurrentFuelBurnWidth() {
		return getItemBurnWidth(fuelStack);
	}

	@Override
	public ItemStack getCurrentFuelStack() {
		return fuelStack;
	}

	@Override
	public void setCurrentFuelStack(ItemStack stack) {
		fuelStack  = stack;
	}

	@Override
	public void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
		((BlockGeologicalEquipment)TMFCore.geoEquipIdle).updateMachineBlockState(isBurning, world, x, y, z);
	}
	
	@Override
	public void onInventoryHasChanged(World world, int x, int y, int z) {
		/*
		 * Sends block to client for update
		 * Automatically updates the associated GUI should it be open
		 */
		world.markBlockForUpdate(x, y, z);
	}
}
