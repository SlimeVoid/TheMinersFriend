package slimevoid.tmf.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.tmf.core.TheMinersFriend;
import slimevoid.tmf.core.data.MiningToolBelt;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.ItemLib;

public class ItemMiningToolbelt extends Item {

	public ItemMiningToolbelt(int itemID) {
		super(itemID);
		this.setNoRepair();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
		if (data == null) {
			data = MiningToolBelt.getNewToolBeltData(entityplayer, world, itemstack);
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
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving) {
		return MiningToolBelt.doDestroyBlock(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving, super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving));
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		MiningToolBelt data = MiningToolBelt.getToolBeltDataFromItemStack(entityplayer, world, itemstack);
		if (data == null) {
			data = MiningToolBelt.getNewToolBeltData(entityplayer, world, itemstack);
			if (data != null) {
				world.setItemData(data.mapName, data);
				data.setToolBeltId(itemstack.getItemDamage());
				data.markDirty();
			}
		}
	}

	
	@Override
	public String getTextureFile() {
		return ItemLib.ITEM_SPRITE_PATH;
	}
}
