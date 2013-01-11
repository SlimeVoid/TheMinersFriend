package slimevoid.tmf.client.sounds;

public class SoundLib {
	
	private static String RESOURCE_LOCATION = "TheMinersFriend/sounds/";
	private static String RESOURCE_PREFIX = "TheMinersFriend.sounds.";

	public static String[] trackerFiles = {
		RESOURCE_LOCATION + "trackersweep.ogg",
		RESOURCE_LOCATION + "trackerping.ogg"
	};

	public static String TRACKER_SWEEP = RESOURCE_PREFIX + "trackersweep";
	public static String TRACKER_PING = RESOURCE_PREFIX + "trackerping";
}
