package slimevoid.tmf.minerals.items;

import java.util.HashMap;
import java.util.Map;

import slimevoid.lib.util.JSParser;
import slimevoid.tmf.core.TMFCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

public class ItemMineralMixedDust extends ItemMineralDust {	
	public static String script = null;
	public static String script_burnTime;
	public static String script_burnSpeed;
	public static String script_burnWidth;

	public ItemMineralMixedDust(int id) {
		super(id);
		/**
		 * Set this to true since we will be storing item damage data for each mixed dust
		 */
		this.setHasSubtypes(true);
		this.setCreativeTab(null);
	}

	public String getItemDisplayName(ItemStack itemstack) {
		// Fetch individual colors
		int meta = getDustMeta(itemstack);
		int iR = ((meta >> 8) & 15);
		int iG = ((meta >> 4) & 15);
		int iB = (meta & 15);
		
		return (StringTranslate.getInstance().translateNamedKey(
						this.getLocalItemName(
								itemstack)
						)
				+ " | "
				+ iR+","+iG+","+iB
				).trim();
	}
	
	@Override
	public int getColorFromItemStack(ItemStack item, int a) {
		return metaToColor(item.getItemDamage());
	}

	@Override
	public int getBurnTime(ItemStack stack) {
		int timeLevel = (getDustMeta(stack) >> 8) & 15;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("level", timeLevel);
		
		Object ret = JSParser.parse(script,script_burnTime,params);
		if ( ret instanceof Number )
			return ((Number)ret).intValue();
		
		return 1600;
	}
	@Override
	public int getBurnSpeed(ItemStack stack) {
		int speedLevel = (getDustMeta(stack) >> 4) & 15;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("level", speedLevel);
		
		Object ret = JSParser.parse(script,script_burnSpeed,params);
		if ( ret instanceof Number )
			return ((Number)ret).intValue();
		
		return 200;
	}
	@Override
	public int getBurnWidth(ItemStack stack) {
		int widthLevel = getDustMeta(stack) & 15;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("level", widthLevel);
		
		Object ret = JSParser.parse(script,script_burnWidth,params);
		if ( ret instanceof Number )
			return ((Number)ret).intValue();
		
		return 1;
	}
	
	/**
	 * Convert a 12-bit meta to 24-bit color.
	 * 
	 * @param meta 12-bit
	 * @return 24-bit
	 */
	private static int metaToColor(int meta) {
		// Fetch individual colors
		int iR = ((meta >> 8) & 15);
		int iG = ((meta >> 4) & 15);
		int iB = (meta & 15);
		
		// Blend
		int out = 0xffffff;
		for ( int i = 0; i < 9; i++ ) {
			if ( iR > i) {
				out -= 0x001919;
			}
			if ( iG > i) {
				out -= 0x190019;
			}
			if ( iB > i) {
				out -= 0x191900;
			}
		}
		return out;
	}
	
	/**
	 * Doubles the bit
	 * 1 = 11
	 * 0 = 00
	 * 
	 * @param bit
	 * @return
	 */
	private static int doubleBit(boolean bit) {
		if ( bit )
			return 3;
		else
			return 0;
	}
	
	/**
	 * Fetches the dust meta based on item stack with dust.
	 * 
	 * @param dust
	 * @return meta
	 */
	public static int getDustMeta(ItemStack dust) {
		if ( dust.getItem() instanceof ItemMineralMixedDust)
			return dust.getItemDamage();
		
		if ( dust.getItem() == TMFCore.dustAcxium )
			return 256; // 0001 0000 0000
		
		if ( dust.getItem() == TMFCore.dustBisogen )
			return 16; // 0000 0001 0000
		
		if ( dust.getItem() == TMFCore.dustCydrine )
			return 1; // 0000 0000 0001
		
		return 0;
	}
	
	/**
	 * Mixes two dust metas.
	 * 
	 * @param mA meta for dust A
	 * @param mB meta for dust B
	 * @return mixed meta
	 */
	public static int mixDustMeta(int mA, int mB) {
		// Fetch the individual levels
		int rA = (mA >> 8) & 15;
		int rB = (mB >> 8) & 15;

		int gA = (mA >> 4) & 15;
		int gB = (mB >> 4) & 15;
		
		int bA = mA & 15;
		int bB = mB & 15;

		// Add the levels up
		int r = rA+rB;
		int g = gA+gB;
		int b = bA+bB;
		
		// Neither are clean dust (both are mixed => both is mixed.
		if ( !(isMetaCleanDust(mA) || isMetaCleanDust(mB)) ) {
			r /= 2;
			g /= 2;
			b /= 2;
		}
		
		// Reassemble meta
		return (r << 8) | (g << 4) |  b;
	}
	
	/**
	 * Fetches the total meta level
	 * 
	 * @param meta
	 * @return level
	 */
	public static int getTotalLevel(int meta) {
		return ((meta >> 8) & 15)+((meta >> 4) & 15)+(meta & 15);
	}
	
	/**
	 * Checks if meta is from a clean dust (not mixed)
	 * 
	 * @param meta
	 * @return
	 */
	public static boolean isMetaCleanDust(int meta) {
		int r = (meta >> 8) & 15;
		int g = (meta >> 4) & 15;
		int b = meta & 15;
		
		// Meta is clean if all the levels summed together is 1
		// If it is mixed; it will be greater (obviously)
		return r+g+b == 1;
	}
}
