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
package slimevoid.tmf.client.sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import slimevoid.tmf.core.LoggerTMF;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.SoundLib;
import slimevoidlib.data.Logger;
import cpw.mods.fml.common.FMLCommonHandler;

public class TrackerSounds {

	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
		if (ConfigurationLib.motionSensorPlaySounds) {
			// For each custom sound file we have defined in Sounds
			for (String file : SoundLib.trackerFiles) {
				// Try to add the custom sound file to the pool of sounds
				try {
					event.manager.soundPoolSounds.addSound(file);
				}
				// If we cannot add the custom sound file to the pool, log the
				// exception
				catch (Exception e) {
					System.err.print(e);
					LoggerTMF.getInstance(Logger.filterClassName(this.getClass().toString())).write(false,
																									"Failed to register sound ["
																											+ file
																											+ "]",
																									Logger.LogLevel.DEBUG);
				}
			}
			FMLCommonHandler.instance().getFMLLogger().fine("Tracker Sounds Registered");
		}
	}
}
