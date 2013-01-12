package slimevoid.tmf.items;

import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.inventory.ContainerMiningToolBelt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMiningToolbelt extends Item {

	public ItemMiningToolbelt(int itemID) {
		super(itemID);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		MiningToolBeltData data = (MiningToolBeltData)world.loadItemData(MiningToolBeltData.class, "ToolBelt["+itemstack.getItemDamage()+"]");
		return itemstack;
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int tick, boolean isHeld) {
		
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		MiningToolBeltData data = (MiningToolBeltData)world.loadItemData(MiningToolBeltData.class, "ToolBelt["+itemstack.getItemDamage()+"]");
		if (data == null) {
			data = new MiningToolBeltData("ToolBelt["+itemstack.getItemDamage()+"]");
			if (data != null) {
				world.setItemData("ToolBelt["+itemstack.getItemDamage()+"]", data);
				data.markDirty();
			}
		}
	}

}
