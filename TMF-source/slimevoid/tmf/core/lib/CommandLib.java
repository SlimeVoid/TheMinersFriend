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

public class CommandLib {

	public static final String	PLAY_MOTION_SWEEP		= "playMotionSweep";
	public static final String	PLAY_MOTION_PING		= "playMotionPing";
	public static final String	CYCLE_TOOL_BELT			= "cycleToolBelt";
	public static final String	UPDATE_TOOL_BELT_TOOL	= "updateSelectedTool";
	public static final String	TOGGLE_MINING_MODE		= "toggleMiningMode";
	public static final String	MESSAGE_TOOL_SELECT		= "toolSelected";
	public static final String	MINING_MODE_ACTIVATED	= "miningModeOn";
	public static final String	MINING_MODE_DEACTIVATED	= "miningModeOff";

	public static final int		CYCLE_TOOLBELT_UP		= 1;
	public static final int		CYCLE_TOOLBELT_DOWN		= -1;

}
