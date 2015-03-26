/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.tmf.core.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.tmf.client.network.executors.ClientMiningModeActivatedExecutor;
import net.slimevoid.tmf.client.network.executors.ClientMiningModeDeactivatedExecutor;
import net.slimevoid.tmf.client.network.executors.ClientMiningToolSelectedExecutor;
import net.slimevoid.tmf.network.executors.*;
import net.slimevoid.tmf.network.packets.PacketMiningToolBelt;
import net.slimevoid.tmf.network.packets.PacketMotionSensor;

public class PacketLib {

    public static final int MOD_COMPAT = 0;
    public static final int MOTION_SENSOR = 1;
    public static final int MINING_TOOL_BELT = 2;

    public static void registerPacketExecutors() {

        // MOD COMPATIBILITY

        // MOTION SENSOR
        PacketHelper.registerServerExecutor(
                MotionSensorSweepExecutor.class,
                PacketMotionSensor.Sweep.class,
                0
        );
        PacketHelper.registerServerExecutor(
                MotionSensorPingExecutor.class,
                PacketMotionSensor.Ping.class,
                1
        );

        // MINING TOOL BELT
        PacketHelper.registerServerExecutor(//CommandLib.CYCLE_TOOL_BELT,
                ToolBeltCycleToolExecutor.class,
                PacketMiningToolBelt.Cycle.class,
                2);
        PacketHelper.registerServerExecutor(//CommandLib.TOGGLE_MINING_MODE,
                ToolBeltModeExecutor.class,
                PacketMiningToolBelt.Mode.class,
                3);
        PacketHelper.registerServerExecutor(//CommandLib.OPEN_TOOLBELT_GUI,
                ToolBeltOpenGuiExecutor.class,
                PacketMiningToolBelt.Gui.class,
                4);
    }

    @SideOnly(Side.CLIENT)
    public static void registerClientPacketExecutors() {

        // MINING TOOL BELT
        PacketHelper.registerClientExecutor(//CommandLib.MESSAGE_TOOL_SELECT,
                ClientMiningToolSelectedExecutor.class,
                PacketMiningToolBelt.Cycle.class,
                5);
        PacketHelper.registerClientExecutor(//CommandLib.MINING_MODE_ACTIVATED,
                ClientMiningModeActivatedExecutor.class,
                PacketMiningToolBelt.Mode.class,
                6);
        PacketHelper.registerClientExecutor(//CommandLib.MINING_MODE_DEACTIVATED,
                ClientMiningModeDeactivatedExecutor.class,
                PacketMiningToolBelt.Mode.class,
                7);

    }

    public static void sendToolBeltMessage(World world, EntityPlayer entityplayer, String command) {
        PacketMiningToolBelt packet = new PacketMiningToolBelt(command);
        PacketHelper.sendToPlayer(packet,
                (EntityPlayerMP) entityplayer);
    }

    public static void sendActivateMessage(World world, EntityPlayer entityplayer) {
        sendToolBeltMessage(world,
                entityplayer,
                CommandLib.MINING_MODE_ACTIVATED);
    }

    public static void sendDeactivateMessage(World world, EntityPlayer entityplayer) {
        sendToolBeltMessage(world,
                entityplayer,
                CommandLib.MINING_MODE_DEACTIVATED);
    }

    public static void sendToolBeltSelectMessage(World world, EntityPlayer entityplayer, int toolBeltId) {
        PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.MESSAGE_TOOL_SELECT);
        // packet.setToolBeltId(toolBeltId);
        PacketHelper.sendToPlayer(packet,
                (EntityPlayerMP) entityplayer);
    }

    public static void sendMiningModeMessage(World world, EntityLivingBase entityliving, boolean mode) {
        if (entityliving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityliving;
            if (mode) {
                sendActivateMessage(world,
                        entityplayer);
            } else {
                sendDeactivateMessage(world,
                        entityplayer);
            }
        }
    }

    public static void sendToolBeltGuiRequest(World world, EntityPlayer entityplayer) {
        PacketMiningToolBelt.Gui packet = new PacketMiningToolBelt.Gui();
        PacketHelper.sendToServer(packet);
    }

    public static void sendToolBeltToggle(World world, EntityPlayer entityplayer) {
        PacketMiningToolBelt.Mode packet = new PacketMiningToolBelt.Mode();
        PacketHelper.sendToServer(packet);
    }

    public static void sendToolBeltCycle(World world, EntityPlayer entityplayer, int direction) {
        PacketMiningToolBelt.Cycle packet = new PacketMiningToolBelt.Cycle();
        packet.side = direction;
        // packet.setToolBeltId(toolBelt.getItemDamage());
        PacketHelper.sendToServer(packet);
    }

    public static void playSoundPing(EntityPlayer entityplayer, World world, BlockPos pos, float dist2sq) {
        PacketMotionSensor.Ping packet = new PacketMotionSensor.Ping(entityplayer, pos, dist2sq);
        PacketHelper.sendToServer(packet);
    }

    public static void playSoundSweep(EntityPlayer entityplayer, World world, BlockPos pos) {
        PacketMotionSensor.Sweep packet = new PacketMotionSensor.Sweep(entityplayer, pos, 1.0F);
        PacketHelper.sendToServer(packet);

    }

    // public static void tryAlternativeHandling(INetworkManager manager,
    // Packet250CustomPayload packet, Player player) {
    // if (packet.channel.equals(Thaumcraft.MOD_CHANNEL)) {
    // ThaumcraftStatic.handlePacket(manager,
    // packet,
    // player);
    // }
    // }
}
