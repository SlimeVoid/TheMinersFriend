package slimevoid.tmf.core.events;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import slimevoid.tmf.core.helpers.ItemHelper;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.data.Logger.LogLevel;

public class MiningToolBeltAttributeModifiers {

	private HashMap<EntityLivingBase, ItemStack>	previousTool	= new HashMap<EntityLivingBase, ItemStack>();

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		ItemStack heldItem = entity.getHeldItem();
		ItemStack tool = ItemHelper.getSelectedTool(heldItem);
		ItemStack previousTool = this.previousTool.containsKey(entity) ? this.previousTool.get(entity) : null;
		if (!ItemStack.areItemStacksEqual(	previousTool,
											tool)) {
			if (previousTool != null) {
				event.entityLiving.getAttributeMap().removeAttributeModifiers(previousTool.getAttributeModifiers());
			}
			if (tool != null) {
				event.entityLiving.getAttributeMap().applyAttributeModifiers(tool.getAttributeModifiers());
			}
			try {
				this.previousTool.put(	entity,
										tool == null ? null : tool.copy());
			} catch (ConcurrentModificationException c) {
				SlimevoidCore.console(	CoreLib.MOD_ID,
										"Phew, that was close! onLivingUpdate could not update "
												+ entity.getEntityName()
												+ "'s previous item.",
										LogLevel.WARNING.ordinal());
			}
		}
	}
}
