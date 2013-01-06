package slimevoid.tmf.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;

public class ItemMinersHat extends ItemArmor {

	public ItemMinersHat(int itemID, EnumArmorMaterial material, int renderIndex, int armorType) {
		super(itemID, material, renderIndex, armorType);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getIconFromDamageForRenderPass(int damage, int renderPass) {
		return renderPass == 1 ? this.iconIndex : super.getIconFromDamageForRenderPass(damage, renderPass);
	}
	
	@Override
    public int getIconFromDamage(int par1)
    {
        return this.iconIndex;
    }

	//@Override
	//public String getTextureFile() {
	//	return "/theminersfriend/gui/items.png";
	//}
}
