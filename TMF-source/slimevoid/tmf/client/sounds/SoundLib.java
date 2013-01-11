package slimevoid.tmf.client.sounds;

public class SoundLib {
	
	private static String RESOURCE_LOCATION = "TheMinersFriend/sound/";
	private static String RESOURCE_PREFIX = "TheMinersFriend.sound.";

	public static String[] trackerFiles = {
		RESOURCE_LOCATION + "trackerPing.ogg",
		RESOURCE_LOCATION + "trackerPong.ogg"
	};

	public static String TRACKER_PING = RESOURCE_PREFIX + "trackerPing";
	public static String TRACKER_PONG = RESOURCE_PREFIX + "trackerPong";
}
