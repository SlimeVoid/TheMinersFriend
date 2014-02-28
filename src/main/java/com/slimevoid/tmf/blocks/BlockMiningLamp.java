package com.slimevoid.tmf.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import slimevoidlib.blocks.BlockTransientLight;

import com.slimevoid.tmf.core.lib.ArmorLib;

public class BlockMiningLamp extends BlockTransientLight {

    public BlockMiningLamp(int id) {
        super(id);
    }

    @Override
    protected boolean handleLightingConditions(World world, int x, int y, int z, Random random) {
        int dist = 1;
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class,
                                                                 AxisAlignedBB.getBoundingBox(x
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
            world.setBlockToAir(x,
                                y,
                                z);
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
                world.setBlockToAir(x,
                                    y,
                                    z);
            }
        }
        return true;
    }

}
