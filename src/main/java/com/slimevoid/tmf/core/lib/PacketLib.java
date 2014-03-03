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
package com.slimevoid.tmf.core.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.slimevoid.compatibility.lib.packets.PacketCompatibilityHandler;
import com.slimevoid.library.network.handlers.ClientPacketHandler;
import com.slimevoid.library.network.handlers.ServerPacketHandler;
import com.slimevoid.library.util.helpers.PacketHelper;
import com.slimevoid.tmf.client.network.handlers.ClientPacketMiningToolBeltHandler;
import com.slimevoid.tmf.client.network.packets.executors.ClientMiningModeActivatedExecutor;
import com.slimevoid.tmf.client.network.packets.executors.ClientMiningModeDeactivatedExecutor;
import com.slimevoid.tmf.client.network.packets.executors.ClientMiningToolSelectedExecutor;
import com.slimevoid.tmf.network.handlers.PacketMiningToolBeltHandler;
import com.slimevoid.tmf.network.handlers.PacketMotionSensorHandler;
import com.slimevoid.tmf.network.packets.PacketMiningToolBelt;
import com.slimevoid.tmf.network.packets.executors.MiningModeExecutor;
import com.slimevoid.tmf.network.packets.executors.MotionSensorPingExecutor;
import com.slimevoid.tmf.network.packets.executors.MotionSensorSweepExecutor;
import com.slimevoid.tmf.network.packets.executors.ToolBeltCycleToolExecutor;
import com.slimevoid.tmf.network.packets.executors.ToolBeltOpenGuiExecutor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketLib {

    public static final int MOD_COMPAT       = 0;
    public static final int MOTION_SENSOR    = 1;
    public static final int MINING_TOOL_BELT = 2;

    public static void registerPacketExecutors() {

        ServerPacketHandler handler = new ServerPacketHandler();
        // MOD COMPATIBILITY
        handler.registerPacketHandler(MOD_COMPAT,
                                      new PacketCompatibilityHandler());

        // MOTION SENSOR
        PacketMotionSensorHandler packetMotionSensorHandler = new PacketMotionSensorHandler();
        packetMotionSensorHandler.registerPacketHandler(CommandLib.PLAY_MOTION_SWEEP,
                                                        new MotionSensorSweepExecutor());
        packetMotionSensorHandler.registerPacketHandler(CommandLib.PLAY_MOTION_PING,
                                                        new MotionSensorPingExecutor());
        handler.registerPacketHandler(MOTION_SENSOR,
                                      packetMotionSensorHandler);

        // MINING TOOL BELT
        PacketMiningToolBeltHandler packetMiningToolBeltHandler = new PacketMiningToolBeltHandler();
        packetMiningToolBeltHandler.registerPacketHandler(CommandLib.CYCLE_TOOL_BELT,
                                                          new ToolBeltCycleToolExecutor());
        packetMiningToolBeltHandler.registerPacketHandler(CommandLib.TOGGLE_MINING_MODE,
                                                          new MiningModeExecutor());
        packetMiningToolBeltHandler.registerPacketHandler(CommandLib.OPEN_TOOLBELT_GUI,
                                                          new ToolBeltOpenGuiExecutor());
        handler.registerPacketHandler(MINING_TOOL_BELT,
                                      packetMiningToolBeltHandler);

        PacketHelper.registerPacketHandler(CoreLib.MOD_CHANNEL,
                                           handler);
    }

    @SideOnly(Side.CLIENT)
    public static void registerClientPacketExecutors() {

        ClientPacketHandler handler = new ClientPacketHandler();

        // MINING TOOL BELT
        ClientPacketMiningToolBeltHandler clientToolBeltHandler = new ClientPacketMiningToolBeltHandler();
        clientToolBeltHandler.registerPacketHandler(CommandLib.MESSAGE_TOOL_SELECT,
                                                    new ClientMiningToolSelectedExecutor());
        clientToolBeltHandler.registerPacketHandler(CommandLib.MINING_MODE_ACTIVATED,
                                                    new ClientMiningModeActivatedExecutor());
        clientToolBeltHandler.registerPacketHandler(CommandLib.MINING_MODE_DEACTIVATED,
                                                    new ClientMiningModeDeactivatedExecutor());
        handler.registerPacketHandler(PacketLib.MINING_TOOL_BELT,
                                      clientToolBeltHandler);

        PacketHelper.registerClientPacketHandler(CoreLib.MOD_CHANNEL,
                                                 handler);

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
        PacketMiningToolBelt packet = new PacketMiningToolBelt(CommandLib.OPEN_TOOLBELT_GUI);
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
