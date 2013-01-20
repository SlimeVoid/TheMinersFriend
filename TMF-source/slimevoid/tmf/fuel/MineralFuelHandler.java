package slimevoid.tmf.fuel;

import slimevoid.tmf.items.ItemMineral;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class MineralFuelHandler implements IFuelHandlerTMF {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if ( !(fuel.getItem() instanceof ItemMineral) )
			return 0;
		
		return ((ItemMineral) fuel.getItem()).getBurnTime(fuel);
	}

	@Override
	public int getBurnSpeed(ItemStack fuel) {
		if ( !(fuel.getItem() instanceof ItemMineral) )
			return 0;
		
		return ((ItemMineral) fuel.getItem()).getBurnSpeed(fuel);
	}

	@Override
	public int getBurnWidth(ItemStack fuel) {
		if ( !(fuel.getItem() instanceof ItemMineral) )
			return 0;
		
		return ((ItemMineral) fuel.getItem()).getBurnWidth(fuel);
	}

}
