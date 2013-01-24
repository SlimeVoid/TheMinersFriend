package slimevoid.tmf.machines.tileentities;

import java.util.HashMap;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.fuel.IFuelHandlerTMF;
import slimevoid.tmf.machines.blocks.BlockGeologicalEquipment;
import slimevoid.tmf.minerals.items.ItemMineral;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
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
	private int currentLevel;
	private int currentLevelIdx;
	
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
		System.out.println(block);
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
		if (depth >= worldObj.getHeight() || (depth -  this.maxScanDepth) <= 0)
			return;
		
		if ( !surveyData.containsKey(depth) ) {
			surveyData.put(depth, new Block[9]);
		}
		
		surveyData.get(depth)[idx] = block;
		
		System.out.println(depth+":"+idx+":"+block.blockID);
	}
	
	/*
	public void setBlock(World world, int x, int y, int z, Block block) {
		// If we're at bedrock or top of the world do nothing
		if (y >= world.getHeight() || (y -  this.maxScanDepth) <= 0) {
			return;
		}
		// TODO :: GeoEquip: Refine setBlock process and storage
		if (world.equals(this.getWorldObj())) {
			Block[] blocks = new Block[this.maxScanWidth];
			if (!this.surveyData.containsKey(y)) {
				blocks[0] = block;
			} else {
				blocks = this.surveyData.get(y);
				for (int i = 0; i < blocks.length; i++) {
					if (blocks[i] == null) {
						blocks[i] = block;
						break;
					}
				}
			}
			if (!this.hasOre && block instanceof BlockOre) {
				this.hasOre = true;
			}
			this.surveyData.put(y, blocks);
		}
	}
	*/

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
			int depth = depthData.getInteger("Slot");
			
			for ( int j = 1; j <= 9; j++ ) {
				NBTTagCompound block = (NBTTagCompound)depthTag.tagAt(j);
				int blockId = block.getInteger("Block");
				
				addSurveyData(depth, j-1, Block.blocksList[blockId]);
			}
		}
		
		System.out.println(surveyData);
		
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
		
		super.writeToNBT(ntbCompound);
		
	}
	
	@Override
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.DOWN) return 1;
		if (side == ForgeDirection.UP) return 0;
		return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void smeltItem() {
		if ( canSmelt() ) {
			for ( int i=0; i < this.currentItemWidth+1; i++ ) {
				if ( currentLevelIdx >= 8) {
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
		return getItemBurnTime(fuelStack)/2;
	}

	@Override
	public int getCurrentFuelBurnSpeed() {
		return getItemBurnSpeed(fuelStack)*2;
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
}
