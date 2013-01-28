package slimevoid.tmf.minerals.items;

import java.util.HashMap;
import java.util.Map;

import slimevoid.lib.util.javascript.JSParser;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.fuel.MixedDustNameRegistry;
import net.minecraft.item.ItemStack;

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
		return MixedDustNameRegistry.getName(getDustMeta(itemstack));
	}
	
	@Override
	public int getColorFromItemStack(ItemStack item, int a) {
		return metaToColor(item.getItemDamage());
	}

	public static int getBurnTimeLevel(int meta) {
		return (meta >> 8) & 15;
	}
	public static int getBurnSpeedLevel(int meta) {
		return (meta >> 4) & 15;
	}
	public static int getBurnWidthLevel(int meta) {
		return meta & 15;
	}
	
	@Override
	public int getBurnTime(ItemStack stack) {
		int timeLevel = getBurnTimeLevel(getDustMeta(stack));
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("level", timeLevel);
		
		Object ret = JSParser.parse(script,script_burnTime,params);
		if ( ret instanceof Number )
			return ((Number)ret).intValue();
		
		return 1600;
	}
	@Override
	public int getBurnSpeed(ItemStack stack) {
		int speedLevel = getBurnSpeedLevel(getDustMeta(stack));
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("level", speedLevel);
		
		Object ret = JSParser.parse(script,script_burnSpeed,params);
		if ( ret instanceof Number )
			return ((Number)ret).intValue();
		
		return 200;
	}
	@Override
	public int getBurnWidth(ItemStack stack) {
		int widthLevel = getBurnWidthLevel(getDustMeta(stack));
		
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
		int iR = getBurnTimeLevel(meta);
		int iG = getBurnSpeedLevel(meta);
		int iB = getBurnWidthLevel(meta);
		
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
		int rA = getBurnTimeLevel(mA);
		int rB = getBurnTimeLevel(mB);

		int gA = getBurnSpeedLevel(mA);
		int gB = getBurnSpeedLevel(mB);
		
		int bA = getBurnWidthLevel(mA);
		int bB = getBurnWidthLevel(mB);

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
		return getBurnTimeLevel(meta) + getBurnSpeedLevel(meta) + getBurnWidthLevel(meta);
	}
	
	/**
	 * Checks if meta is from a clean dust (not mixed)
	 * 
	 * @param meta
	 * @return
	 */
	public static boolean isMetaCleanDust(int meta) {
		int r = getBurnTimeLevel(meta);
		int g = getBurnSpeedLevel(meta);
		int b = getBurnWidthLevel(meta);
		
		// Meta is clean if all the levels summed together is 1
		// If it is mixed; it will be greater (obviously)
		return r+g+b == 1;
	}
}
