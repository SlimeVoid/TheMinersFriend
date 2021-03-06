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
package net.slimevoid.tmf.core.lib;

public class SoundLib {

    private static String  RESOURCE_PREFIX = CoreLib.MOD_ID + ":";

    public static String[] trackerFiles    = {
            RESOURCE_PREFIX + "trackersweep.ogg",
            RESOURCE_PREFIX + "trackerping.ogg" };

    public static String   TRACKER_SWEEP   = RESOURCE_PREFIX + "trackersweep";
    public static String   TRACKER_PING    = RESOURCE_PREFIX + "trackerping";
}
