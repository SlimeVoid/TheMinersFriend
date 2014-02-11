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
package slimevoid.tmf.core.lib;

import net.minecraftforge.common.MinecraftForge;
import slimevoid.tmf.client.sounds.TrackerSounds;
import slimevoid.tmf.client.tickhandlers.input.ToolBeltMouseWheelHandler;
import slimevoid.tmf.core.events.MiningToolBeltAttributeModifiers;
import slimevoid.tmf.core.events.MiningToolBeltEntityInteract;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventLib {

    @SideOnly(Side.CLIENT)
    public static void registerClientEvents() {
        MinecraftForge.EVENT_BUS.register(new TrackerSounds());
        MinecraftForge.EVENT_BUS.register(new ToolBeltMouseWheelHandler());
    }

    public static void registerCommonEvents() {
        MinecraftForge.EVENT_BUS.register(new MiningToolBeltEntityInteract());
        // MinecraftForge.EVENT_BUS.register(new MiningToolBeltHarvestCheck());
        // MinecraftForge.EVENT_BUS.register(new MiningToolBeltBreakSpeed());
        MinecraftForge.EVENT_BUS.register(new MiningToolBeltAttributeModifiers());
    }
}
