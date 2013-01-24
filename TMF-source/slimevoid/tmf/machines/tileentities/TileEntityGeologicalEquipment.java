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
		this.surveyData = new HashMap<Integer, Block[]>();
		this.hasOre = false;
	}
	
	private void calculateScan() {
		// TODO :: GeoEquip: calculate the scan speed based on fuel
		this.scanSpeed = this.getCurrentFuelBurnSpeed(); // ???
		// TODO :: GeoEquip: calculate the maxScanWidth based on fuel
		this.maxScanWidth= this.getCurrentFuelBurnWidth(); // ???
		// TODO :: GeoEquip: calculate the maxScanDepth based on fuel
		this.maxScanDepth = yCoord /* - some value from fuel */;
	}
	
	private void scanLevel() {
		// TODO : Scan currentLevel using scanWidth
	}
	
	private void gotoNextLevel() {
		if (currentLevel <= this.maxScanDepth) {
			currentLevel = this.yCoord - 1;
		} else {
			currentLevel--;
		}
	}

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

	public Block getBlockAt(World world, int x, int y, int z) {
		return Block.blocksList[world.getBlockId(x, y, z)];
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
			// TODO :: GeoEquip: smelt item
			
		}
	}

	@Override
	protected boolean canSmelt() {		
		if ( currentLevel >= 0 ) {
			int lastLevelScannedNumber = 0;
			
			if ( surveyData.containsKey(0) ) {
				if ( surveyData.get(0).length == 9 ) {
					for ( Block b: surveyData.get(0) ) {
						if ( b == null )
							continue;
						lastLevelScannedNumber++;
					}
				}
			}
			
			if ( lastLevelScannedNumber == 9 )
				return false;
			return true;
		}
		
		return false;
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
