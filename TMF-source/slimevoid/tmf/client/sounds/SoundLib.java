package slimevoid.tmf.client.sounds;

public class SoundLib {
	
	private static String resourceLocation = "TheMinersFriend/sound/";
	private static String resourcePrefix = "sound.";

	public static String[] trackerFiles = {
		resourceLocation + "trackerPing.ogg",
		resourceLocation + "trackerPong.ogg"
	};

	public static String TRACKER_PING = resourcePrefix + "trackerPing";
	public static String TRACKER_PONG = resourcePrefix + "trackerPong";
}
