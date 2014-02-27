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
package com.slimevoid.tmf.core.lib;

import net.minecraft.util.ResourceLocation;

public class ModelLib {

    public static final String           MACHINE_PREFIX       = "textures/machines/";

    private static final String          GRINDER_STATIC_PATH  = MACHINE_PREFIX
                                                                + "grinderStatic.png";
    public static final ResourceLocation GRINDER_STATIC       = new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_STATIC_PATH);

    public static final String           GRINDER_ROLLERS_PATH = MACHINE_PREFIX
                                                                + "grinderRoller.png";
    public static final ResourceLocation GRINDER_ROLLERS      = new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_ROLLERS_PATH);

    public static final String           GRINDER_GEARS_PATH   = MACHINE_PREFIX
                                                                + "grinderGears.png";
    public static final ResourceLocation GRINDER_GEARS        = new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_GEARS_PATH);

    public static final String           GRINDER_AXELS_PATH   = MACHINE_PREFIX
                                                                + "grinderAxles.png";
    public static final ResourceLocation GRINDER_AXELS        = new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_AXELS_PATH);

}
