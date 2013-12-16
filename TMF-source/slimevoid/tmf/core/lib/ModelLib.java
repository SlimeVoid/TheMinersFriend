package slimevoid.tmf.core.lib;

import net.minecraft.util.ResourceLocation;

public class ModelLib {

	public static final String				MACHINE_PREFIX			= "textures/machines/";

	private static final String				GRINDER_STATIC_PATH		= MACHINE_PREFIX
																		+ "grinderStatic.png";
	public static final ResourceLocation	GRINDER_STATIC			= new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_STATIC_PATH);

	public static final String				GRINDER_ROLLERS_PATH	= MACHINE_PREFIX
																		+ "grinderRoller.png";
	public static final ResourceLocation	GRINDER_ROLLERS			= new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_ROLLERS_PATH);

	public static final String				GRINDER_GEARS_PATH		= MACHINE_PREFIX
																		+ "grinderGears.png";
	public static final ResourceLocation	GRINDER_GEARS			= new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_GEARS_PATH);

	public static final String				GRINDER_AXELS_PATH		= MACHINE_PREFIX
																		+ "grinderAxles.png";
	public static final ResourceLocation	GRINDER_AXELS			= new ResourceLocation(CoreLib.MOD_RESOURCES, GRINDER_AXELS_PATH);

}
