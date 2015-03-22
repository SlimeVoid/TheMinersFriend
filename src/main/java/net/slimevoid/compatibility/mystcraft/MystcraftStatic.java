package net.slimevoid.compatibility.mystcraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MystcraftStatic {

    public static boolean isBookStandOrLectern(World world, BlockPos pos) {
        // if (Mods.MYSTCRAFT.getCompat().isLoaded) {
        // return checkBookStandOrLectern(world,
        // x,
        // y,
        // z);
        // }
        return false;
    }

    public static boolean checkBookStandOrLectern(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
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
