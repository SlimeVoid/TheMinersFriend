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

import net.minecraft.item.ItemStack;

public class GuiLib {

    // Gui Ids
    public static final int GUIID_TOOL_BELT                 = 0;
    public static final int GUIID_UTILITY_BELT              = 1;
    public static final int GUIID_REFINERY                  = 2;
    public static final int GUIID_GRINDER                   = 3;
    public static final int GUIID_GEOEQUIP                  = 4;
    public static final int GUIID_MIXINGTABLE               = 5;
    public static final int GUIID_STOVE                     = 6;

    // Button Ids
    public static final int MOTION_SENSOR_SETTINGS_BUTTONID = 0;

    public static int getBeltIdFromItemStack(ItemStack itemstack) {
        return itemstack.getItem().getUnlocalizedName().equals("item."
                                                               + ItemLib.MINING_TOOLBELT) ? GUIID_TOOL_BELT : GUIID_UTILITY_BELT;
    }

}
