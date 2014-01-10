package slimevoid.tmf.core.lib;

public class NBTLib {

	public static final String	SELECTED_TOOL	= "Tool";
	public static final int		MAX_TOOLS		= 4;
	private static final String	TOOL			= "Slot[#]";

	public static String getToolKey(int slot) {
		return TOOL.replace('#',
							String.valueOf(slot).charAt(0));
	}

}
