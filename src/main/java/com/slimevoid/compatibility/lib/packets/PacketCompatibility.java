package com.slimevoid.compatibility.lib.packets;

import net.minecraft.world.World;

import com.slimevoid.tmf.core.lib.PacketLib;
import com.slimevoid.tmf.network.packets.PacketMining;

public class PacketCompatibility extends PacketMining {

    public PacketCompatibility() {
        super(PacketLib.MOD_COMPAT);
    }

    @Override
    public boolean targetExists(World world) {
        return false;
    }

}