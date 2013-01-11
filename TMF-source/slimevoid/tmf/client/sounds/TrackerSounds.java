package slimevoid.tmf.client.sounds;

import java.net.URL;

import net.minecraftforge.client.event.sound.SoundLoadEvent;

public class TrackerSounds {
	
	public static TrackerSounds instance = new TrackerSounds();
	
	public class TrackerPing {
		
		//@ForgeSubscribe
		public void trackerPing(SoundLoadEvent event) {
			try {
				URL pingPath = this.getClass().getResource("/theminersfriend/sounds/trackerping.ogg");
				System.out.println(pingPath);
				event.manager.soundPoolSounds.addSound("theminersfriend/sounds/trackerping.ogg", pingPath);
				System.out.println("Tracker Ping Registered");
			} catch (Exception e) {
				System.err.println("Failed to register one or more sounds.");
			}
		}
	}

	public class TrackerPong {
		//@ForgeSubscribe
		public void trackerPing(SoundLoadEvent event) {
			try {
				URL pongPath = this.getClass().getResource("/theminersfriend/sounds/trackerpong.ogg");
				System.out.println(pongPath);
				event.manager.soundPoolSounds.addSound("theminersfriend/sounds/trackerpong.ogg", pongPath);
				System.out.println("Tracker Pong Registered");
			} catch (Exception e) {
				System.err.println("Failed to register one or more sounds.");
			}
		}
	}
	
	public TrackerPing trackerping = new TrackerPing();
	public TrackerPong trackerpong = new TrackerPong();
}
