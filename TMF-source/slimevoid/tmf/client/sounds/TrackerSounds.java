package slimevoid.tmf.client.sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.SoundLib;
import cpw.mods.fml.common.FMLCommonHandler;

public class TrackerSounds {
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
        // For each custom sound file we have defined in Sounds
		int i = 0;
		for (String file : SoundLib.trackerFiles) {
            // Try to add the custom sound file to the pool of sounds
			try {
				event.manager.soundPoolSounds.addSound(SoundLib.trackerSounds[i], TMFCore.class.getResource("/"+file));
				i++;
			}
            // If we cannot add the custom sound file to the pool, log the exception 
			catch (Exception e) {
				System.err.print(e);
				LoggerTMF.getInstance(
						Logger.filterClassName(this.getClass().toString())
						).write(
								false,
								"Failed to register sound [" + file + "]",
								Logger.LogLevel.DEBUG
						);
			}
		}
		FMLCommonHandler.instance().getFMLLogger().fine("Tracker Sounds Registered");
	}
}
