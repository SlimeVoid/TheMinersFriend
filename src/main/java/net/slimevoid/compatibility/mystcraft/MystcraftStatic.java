package net.slimevoid.compatibility.mystcraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class MystcraftStatic {

    public static boolean isBookStandOrLectern(World world, int x, int y, int z) {
        // if (Mods.MYSTCRAFT.getCompat().isLoaded) {
        // return checkBookStandOrLectern(world,
        // x,
        // y,
        // z);
        // }
        return false;
    }

    public static boolean checkBookStandOrLectern(World world, int x, int y, int z) {
        Block blockID = world.getBlock(x,
                                       y,
                                       z);
        boolean flag = false;
        // if (MystObjects.bookstand != null) {
        // SlimevoidCore.console(CoreLib.MOD_ID,
        // "Activate Mystcraft Bookstand.");
        // flag = blockID == MystObjects.bookstand.blockID;
        // }
        // if (!flag && MystObjects.book_lectern != null) {
        // SlimevoidCore.console(CoreLib.MOD_ID,
        // "Activate Mystcraft Lectern.");
        // flag = blockID == MystObjects.book_lectern.blockID;
        // }
        return flag;
    }
}
