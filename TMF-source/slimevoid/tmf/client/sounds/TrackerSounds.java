package slimevoid.tmf.client.sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class TrackerSounds {
	
	public static TrackerSounds instance = new TrackerSounds();
	
	public class TrackerPing {
		
		@ForgeSubscribe
		public void trackerPing(SoundLoadEvent event) {
			try {
				event.manager.soundPoolSounds.addSound("sounds/trackerping.ogg", this.getClass().getResource("/sounds/trackerping.ogg"));
				System.out.println("Tracker Pin Registered");
			} catch (Exception e) {
				System.err.println("Failed to register one or more sounds.");
			}
		}
	}

	public class TrackerPong {
		@ForgeSubscribe
		public void trackerPing(SoundLoadEvent event) {
			try {
				event.manager.soundPoolSounds.addSound("sounds/trackerpong.ogg", this.getClass().getResource("/sounds/trackerpong.ogg"));
				System.out.println("Tracker Pong Registered");
			} catch (Exception e) {
				System.err.println("Failed to register one or more sounds.");
			}
		}
	}
	
	public TrackerPing trackerping = new TrackerPing();
	public TrackerPong trackerpong = new TrackerPong();
}
