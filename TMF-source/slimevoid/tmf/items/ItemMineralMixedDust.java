package slimevoid.tmf.items;

import slimevoid.tmf.core.TMFCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class ItemMineralMixedDust extends ItemMineralDust implements ICraftingHandler {

	public ItemMineralMixedDust(int id) {
		super(id);
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,	IInventory craftMatrix) {
		ItemStack a = null;
		ItemStack b = null;
		for ( int i = 0; i < craftMatrix.getInventoryStackLimit(); i++ ) {
			if ( craftMatrix.getStackInSlot(i) != null ) {
				if ( craftMatrix.getStackInSlot(i).getItem() instanceof ItemMineralDust ) {
					if ( a == null ) {
						a = craftMatrix.getStackInSlot(i);
					} else if ( b == null ) {
						b = craftMatrix.getStackInSlot(i);
					}
				}
			}
		}
		
		if ( a != null && b != null ) {
			System.out.println("--------");
			System.out.println(a);
			System.out.println(b);
			System.out.println(item);
			mixDustMeta(getDustMeta(a),getDustMeta(b));
			System.out.println("--------");
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		
	}
	
	public int getDustMeta(ItemStack dust) {
		if ( dust.getItem() instanceof ItemMineralMixedDust)
			return dust.getItemDamage();
		
		if ( dust.getItem() == TMFCore.dustAcxium )
			return 3840; // 1111 0000 0000
		
		if ( dust.getItem() == TMFCore.dustBisogen )
			return 240; // 0000 1111 0000
		
		if ( dust.getItem() == TMFCore.dustCydrine )
			return 15; // 0000 0000 1111
		
		return 0;
	}
	
	public int mixDustMeta(int mA, int mB) {
		int rA = (mA >> 8) & 15;
		int rB = (mB >> 8) & 15;
		int r = 0;

		int gA = (mA >> 4) & 15;
		int gB = (mB >> 4) & 15;
		int g = 0;
		
		int bA = mA & 15;
		int bB = mB & 15;
		int b = 0;
		
		System.out.println(Integer.toBinaryString(rA));
		System.out.println(Integer.toBinaryString(gA));
		System.out.println(Integer.toBinaryString(bA));
		
		System.out.println(Integer.toBinaryString(rB));
		System.out.println(Integer.toBinaryString(gB));
		System.out.println(Integer.toBinaryString(bB));
		
		return 0;
	}
}
