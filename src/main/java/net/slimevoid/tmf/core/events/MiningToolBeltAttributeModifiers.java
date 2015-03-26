/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.tmf.core.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.data.Logger.LogLevel;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import net.slimevoid.tmf.core.lib.CoreLib;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class MiningToolBeltAttributeModifiers {

    private HashMap<EntityLivingBase, ItemStack> previousTool = new HashMap<EntityLivingBase, ItemStack>();

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        EntityLivingBase entity = event.entityLiving;
        ItemStack heldItem = entity.getHeldItem();
        ItemStack tool = ItemHelper.getSelectedTool(heldItem);
        ItemStack previousTool = this.previousTool.containsKey(entity) ? this.previousTool.get(entity) : null;
        if (!ItemStack.areItemStacksEqual(previousTool,
                tool)) {
            if (previousTool != null) {
                event.entityLiving.getAttributeMap().removeAttributeModifiers(previousTool.getAttributeModifiers());
            }
            if (tool != null) {
                event.entityLiving.getAttributeMap().applyAttributeModifiers(tool.getAttributeModifiers());
            }
            try {
                this.previousTool.put(entity,
                        tool == null ? null : tool.copy());
            } catch (ConcurrentModificationException c) {
                SlimevoidCore.console(CoreLib.MOD_ID,
                        "Phew, that was close! onLivingUpdate could not update "
                                + entity + "'s previous item.",
                        LogLevel.WARNING.ordinal());
            }
        }
    }
}
