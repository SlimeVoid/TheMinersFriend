package slimevoid.tmf.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.lib.GuiLib;

public class ItemMiningToolbelt extends Item {

	public ItemMiningToolbelt(int itemID) {
		super(itemID);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		MiningToolBeltData data = MiningToolBeltData.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
		if (data == null) {
			data = MiningToolBeltData.getNewToolBeltData(entityplayer, world, itemstack);
			if (data != null) {
				world.setItemData(data.mapName, data);
				data.setToolBeltId(itemstack.getItemDamage());
				data.markDirty();
			}
		}
		if (data != null) {
			entityplayer.openGui(
					TheMinersFriend.instance,
					GuiLib.TOOL_BELT_GUIID,
					world,
					(int)entityplayer.posX,
					(int)entityplayer.posY,
					(int)entityplayer.posZ);
		}
		return itemstack;
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		MiningToolBeltData data = MiningToolBeltData.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
		if (data == null) {
			data = MiningToolBeltData.getNewToolBeltData(entityplayer, world, itemstack);
			if (data != null) {
				world.setItemData(data.mapName, data);
				data.setToolBeltId(itemstack.getItemDamage());
				data.markDirty();
			}
		}
	}

	
	@Override
	public String getTextureFile() {
		return "/TheMinersFriend/gui/items.png";
	}
}
