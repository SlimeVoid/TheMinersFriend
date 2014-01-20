package slimevoid.tmf.core.lib;

public class NBTLib {

	public static final String	SELECTED_TOOL	= "Tool";
	public static final String	SLOT			= "Slot";
	public static final String	TOOLS			= "Tools";

	public static final int		MAX_TOOLS		= 4;

	private static final String	TOOL			= SLOT + "[#]";
	public static final String	MINING_MODE		= "Mode";

	public static String getToolKey(int slot) {
		String toolKey = TOOL.replace(	'#',
										String.valueOf(slot).charAt(0));
		return toolKey;
	}

}
