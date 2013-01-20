package slimevoid.tmf.fuel;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public interface IFuelHandlerTMF extends IFuelHandler {
	public int getBurnSpeed(ItemStack fuel);
	public int getBurnWidth(ItemStack fuel);
}
