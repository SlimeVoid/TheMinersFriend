package slimevoid.compatibility.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoid.compatibility.lib.packets.PacketCompatibility;
import slimevoidlib.IPacketExecutor;
import slimevoidlib.network.PacketUpdate;

public class PacketChangeWandFocusExecutor implements IPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketCompatibility) {
            if (packet.getCommand().equals(ThaumcraftStatic.COMMAND_CHANGE_FOCUS)) {
                ThaumcraftStatic.doChangeFocus(world,
                                               entityplayer);
            }
        }
    }

}
