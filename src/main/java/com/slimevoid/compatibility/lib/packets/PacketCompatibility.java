package com.slimevoid.compatibility.lib.packets;

import com.slimevoid.tmf.core.lib.PacketLib;
import com.slimevoid.tmf.network.packets.PacketMining;

import net.minecraft.world.World;

public class PacketCompatibility extends PacketMining {

    public PacketCompatibility() {
        super(PacketLib.MOD_COMPAT);
    }

    @Override
    public boolean targetExists(World world) {
        return false;
    }

}