package com.slimevoid.compatibility.lib.packets;

import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.library.network.handlers.SubPacketHandler;

public class PacketCompatibilityHandler extends SubPacketHandler {

    @Override
    protected PacketUpdate createNewPacket() {
        return new PacketCompatibility();
    }

}
