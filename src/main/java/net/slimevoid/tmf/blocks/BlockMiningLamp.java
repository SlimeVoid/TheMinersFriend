package net.slimevoid.tmf.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.slimevoid.library.blocks.BlockTransientLight;
import net.slimevoid.tmf.core.lib.ArmorLib;

import java.util.List;
import java.util.Random;

public class BlockMiningLamp extends BlockTransientLight {

    public BlockMiningLamp() {
        super();
    }

    @Override
    protected boolean handleLightingConditions(World world, BlockPos pos, Random random) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        int dist = 1;
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class,
                AxisAlignedBB.fromBounds(
                        x
                                - dist,
                        y
                                - dist,
                        z
                                - dist,
                        x
                                + dist,
                        y
                                + dist,
                        z
                                + dist));
        if (players.isEmpty()) {
            world.setBlockToAir(pos);
        } else {
            boolean flag = false;
            for (EntityPlayer entityplayer : players) {
                if (ArmorLib.getPlayerHelm(entityplayer,
                        world) != null) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                world.setBlockToAir(pos);
            }
        }
        return true;
    }

}
