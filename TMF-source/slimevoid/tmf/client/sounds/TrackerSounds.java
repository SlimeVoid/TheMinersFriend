package slimevoid.tmf.client.sounds;

import java.net.URL;

import slimevoid.lib.data.Logger;
import slimevoid.tmf.core.LoggerTMF;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class TrackerSounds {
	
	@ForgeSubscribe
	public void trackerSounds(SoundLoadEvent event) {
        // For each custom sound file we have defined in Sounds
		for (String file : SoundLib.trackerFiles) {
            // Try to add the custom sound file to the pool of sounds
			try {
				event.manager.soundPoolSounds.addSound(file, this.getClass().getResource("/" + file));
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
