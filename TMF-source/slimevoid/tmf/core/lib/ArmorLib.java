package slimevoid.tmf.core.lib;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.tools.items.ItemMiningHelmet;

public class ArmorLib {
	
	private static HashMap<Integer, String> armorTextures = new HashMap<Integer, String>();

	public static String getArmorTextureFromItemStack(ItemStack itemstack) {
		return getArmorTextureFromItemID(itemstack.itemID);
	}
	
	public static String getArmorTextureFromItemID(int itemID) {
		if (armorTextures.containsKey(itemID)) {
			return armorTextures.get(itemID);
		} else {
			LoggerTMF.getInstance("ArmorLib"
					).write(
							true,
							"Failed to get Texture file for ID [" + itemID + "]",
							Logger.LogLevel.DEBUG);
		}
		return "";
	}
	
	public static String registerArmorTexture(Item item, String textureFile) {
		if (!armorTextures.containsKey(item.itemID)) {
			armorTextures.put(item.itemID, textureFile);
			return getArmorTextureFromItemID(item.itemID);
		} else {
			LoggerTMF.getInstance("ArmorLib"
					).write(
							true,
							"Texture file for item [" + item.getItemName() + "] already registered",
							Logger.LogLevel.DEBUG);
		}
		return "";
	}

	private static ItemStack playerIsWearingArmor(EntityPlayer entityplayer,
			World world, Class<? extends ItemArmor> armorClass) {
		for (ItemStack armorPiece : entityplayer.inventory.armorInventory) {
			if (armorPiece != null && armorClass.isInstance(armorPiece.getItem())) {
				return armorPiece;
			}
		}
		return null;
	}

	public static ItemStack getHelm(EntityPlayer entityplayer, World world) {
		return playerIsWearingArmor(entityplayer, world, ItemMiningHelmet.class);
	}
}
