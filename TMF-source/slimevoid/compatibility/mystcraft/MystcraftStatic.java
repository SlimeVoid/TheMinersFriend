package slimevoid.compatibility.mystcraft;

import net.minecraft.world.World;
import slimevoid.compatibility.Mods;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoidlib.core.SlimevoidCore;

import com.xcompwiz.mystcraft.api.MystObjects;

public class MystcraftStatic {

    public static boolean isBookStandOrLectern(World world, int x, int y, int z) {
        if (Mods.MYSTCRAFT.getCompat().isLoaded) {
            return checkBookStandOrLectern(world,
                                           x,
                                           y,
                                           z);
        }
        return false;
    }

    public static boolean checkBookStandOrLectern(World world, int x, int y, int z) {
        int blockID = world.getBlockId(x,
                                       y,
                                       z);
        boolean flag = false;
        if (MystObjects.bookstand != null) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Activate Mystcraft Bookstand.");
            flag = blockID == MystObjects.bookstand.blockID;
        }
        if (!flag && MystObjects.book_lectern != null) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Activate Mystcraft Lectern.");
            flag = blockID == MystObjects.book_lectern.blockID;
        }
        return flag;
    }
}
