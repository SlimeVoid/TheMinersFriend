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
package slimevoid.tmf.core.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MiningMode {
	private static List<EntityPlayer>	playersInMiningMode;
	private static float				strength;

	public static void init(float strengthMultiplier) {
		playersInMiningMode = new ArrayList<EntityPlayer>();
		strength = strengthMultiplier;
	}

	private static void activateMiningModeForPlayer(EntityPlayer entityplayer) {
		playersInMiningMode.add(entityplayer);
	}

	private static void deactivateMiningModeForPlayer(EntityPlayer entityplayer) {
		playersInMiningMode.remove(entityplayer);
	}

	public static void toggleMiningModeForPlayer(EntityPlayer entityplayer) {
		String mode = "";
		if (playersInMiningMode.contains(entityplayer)) {
			deactivateMiningModeForPlayer(entityplayer);
			mode = "deactivated.";
		} else {
			activateMiningModeForPlayer(entityplayer);
			mode = "activated.";
		}
		entityplayer.addChatMessage("Mining Mode: " + mode);
	}

	public static boolean isPlayerInMiningMode(EntityPlayer entityplayer) {
		return playersInMiningMode.contains(entityplayer);
	}

	public static float getPlayerStrength(EntityPlayer entityplayer, ItemStack toolBelt, MiningToolBelt data) {
		if (isPlayerInMiningMode(entityplayer)) {
			return strength;
		}
		return 1;
	}

}
