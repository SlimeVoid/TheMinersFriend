package slimevoid.tmf.core.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MiningMode {
	private static List<EntityPlayer> playersInMiningMode;
	private static float strength;
	
	public static void InitMiningMode(float strengthMultiplier) {
		playersInMiningMode = new ArrayList();
		strength = strengthMultiplier;
	}
	
	private static void activateMiningModeForPlayer(EntityPlayer entityplayer) {
		playersInMiningMode.add(entityplayer);
	}
	
	private static void deactivateMiningModeForPlayer(EntityPlayer entityplayer) {
		playersInMiningMode.remove(entityplayer);
	}
	
	public static void toggleMiningModeForPlayer(EntityPlayer entityplayer) {
		if (playersInMiningMode.contains(entityplayer)) {
			deactivateMiningModeForPlayer(entityplayer);
		} else {
			activateMiningModeForPlayer(entityplayer);
		}
	}
	
	private static boolean isPlayerInMiningMode(EntityPlayer entityplayer) {
		return playersInMiningMode.contains(entityplayer);
	}

	public static float getMinerStrength(EntityPlayer entityplayer,
			ItemStack toolBelt, MiningToolBelt data) {
		if (isPlayerInMiningMode(entityplayer)) {
			return strength;
		}
		return 0;
	}

}
