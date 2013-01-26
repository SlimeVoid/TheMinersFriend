package slimevoid.tmf.core.lib;

public class ResourceLib {
	public final static String PATH_PREFIX = "/TheMinersFriend/";
	
	public static final String BLOCK_TEXTURE_PATH = 	PATH_PREFIX + 		"terrain.png";
	
	public static final String RESOURCE_SPRITE_PATH = 	PATH_PREFIX + 		"resources/resources.png";

	public static final String ARMOR_PREFIX = PATH_PREFIX + "armor/";
	
	public static final String IRON_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfiron.png";
	public static final String GOLD_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfgold.png";
	public static final String DIAMOND_MINING_HELMET = 	ARMOR_PREFIX + 		"tmfdiamond.png";

	public static final String GUI_PREFIX = PATH_PREFIX + "gui/";
	
	public static final String ITEM_SPRITE_PATH = 		GUI_PREFIX + 		"items.png";
	public static final String GUI_TOOLBELT = 			GUI_PREFIX + 		"toolbeltGui.png";
	public static final String GUI_GEOEQUIP = 			GUI_PREFIX + 		"geoequip.png";
	public static final String GUI_GRINDER = 			GUI_PREFIX + 		"grinder.png";
	public static final String GUI_REFINERY = 			GUI_PREFIX + 		"refinery.png";
	public static final String GUI_AUTOMIXTABLE = 		GUI_PREFIX + 		"automixtable.png";

	public static final String MACHINE_PREFIX = PATH_PREFIX + "machines/";
	
	public static final String MACHINE_TEXTURE_PATH = 	MACHINE_PREFIX + 	"machines.png";
	public static final String RECIPES_REFINERY = 		MACHINE_PREFIX + 	"refinery.json";
	public static final String RECIPES_GRINDER = 		MACHINE_PREFIX + 	"grinder.json";
	
}