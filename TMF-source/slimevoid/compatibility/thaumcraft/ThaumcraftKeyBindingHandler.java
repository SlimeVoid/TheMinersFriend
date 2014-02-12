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
package slimevoid.compatibility.thaumcraft;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import slimevoid.tmf.core.helpers.ItemHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ThaumcraftKeyBindingHandler extends KeyHandler {

    public static boolean keyPressed = false;

    public ThaumcraftKeyBindingHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
        super(keyBindings, repeatings);
    }

    @Override
    public String getLabel() {
        return "TMFThaumcraftKeyBindings";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
        if (FMLClientHandler.instance().getClient().inGameHasFocus && tickEnd) {
            if (!keyPressed) {
                Minecraft mc = Minecraft.getMinecraft();
                EntityClientPlayerMP player = mc.thePlayer;

                if (player != null && player.getHeldItem() != null) {
                    ItemStack heldItem = player.getHeldItem();
                    ItemStack tool = ItemHelper.getSelectedTool(heldItem);
                    if (ThaumcraftStatic.isWand(tool)) {
                        ThaumcraftStatic.sendChangeFocus();
                    }
                }
            }

            keyPressed = true;
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
        keyPressed = false;
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }
}
