package slimevoid.compatibility.lib.packets;

import net.minecraft.world.World;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.network.packets.PacketMining;

public class PacketCompatibility extends PacketMining {

    public PacketCompatibility() {
        super(PacketLib.MOD_COMPAT);
    }

    @Override
    public boolean targetExists(World world) {
        return false;
    }

}