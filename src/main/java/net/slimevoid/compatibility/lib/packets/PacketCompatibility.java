package net.slimevoid.compatibility.lib.packets;

import net.minecraft.world.World;
import net.slimevoid.tmf.core.lib.PacketLib;
import net.slimevoid.tmf.network.packets.PacketMining;

public class PacketCompatibility extends PacketMining {

    public PacketCompatibility() {
        super(PacketLib.MOD_COMPAT);
    }

    @Override
    public boolean targetExists(World world) {
        return false;
    }

}