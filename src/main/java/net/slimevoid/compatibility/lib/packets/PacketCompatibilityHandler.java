package net.slimevoid.compatibility.lib.packets;

import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.library.network.handlers.SubPacketHandler;

public class PacketCompatibilityHandler extends SubPacketHandler {

    @Override
    protected PacketUpdate createNewPacket() {
        return new PacketCompatibility();
    }

}
