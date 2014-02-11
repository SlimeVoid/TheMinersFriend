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

public class NBTLib {

    public static final String  SELECTED_TOOL = "Tool";
    public static final String  SLOT          = "Slot";
    public static final String  TOOLS         = "Tools";

    public static final int     MAX_TOOLS     = 4;

    private static final String TOOL          = SLOT + "[#]";
    public static final String  MINING_MODE   = "Mode";
    public static final String  MIRRORED_TOOL = "MirroredTools";

    public static final String  INFI_TOOL     = "InfiTool";
    public static final String  HARVEST_LEVEL = "HarvestLevel";

    public static String getToolKey(int slot) {
        String toolKey = TOOL.replace('#',
                                      String.valueOf(slot).charAt(0));
        return toolKey;
    }

}
