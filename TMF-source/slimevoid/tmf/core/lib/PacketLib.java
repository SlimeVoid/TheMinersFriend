package slimevoid.tmf.core.lib;

import slimevoid.tmf.client.network.ClientPacketHandler;
import slimevoid.tmf.client.network.handlers.ClientPacketMiningToolBeltHandler;
import slimevoid.tmf.client.network.packets.executors.ClientMiningToolBeltUpdateExecutor;
import slimevoid.tmf.client.network.packets.executors.ClientMiningToolBeltUpdateToolExecutor;
import slimevoid.tmf.network.CommonPacketHandler;
import slimevoid.tmf.network.handlers.PacketMiningToolBeltHandler;
import slimevoid.tmf.network.handlers.PacketMotionSensorHandler;
import slimevoid.tmf.network.packets.executors.MiningModeExecutor;
import slimevoid.tmf.network.packets.executors.MotionSensorPingExecutor;
import slimevoid.tmf.network.packets.executors.MotionSensorSweepExecutor;
import slimevoid.tmf.network.packets.executors.ToolBeltCycleToolExecutor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketLib {

	public static final int MOTION_SENSOR = 0;
	public static final int MINING_TOOL_BELT = 1;

	public static void registerPacketExecutors() {
		CommonPacketHandler.init();
		
		// MOTION SENSOR
		PacketMotionSensorHandler packetMotionSensorHandler = new PacketMotionSensorHandler();
		packetMotionSensorHandler.registerPacketHandler(
				CommandLib.PLAY_MOTION_SWEEP,
				new MotionSensorSweepExecutor());
		packetMotionSensorHandler.registerPacketHandler(
				CommandLib.PLAY_MOTION_PING,
				new MotionSensorPingExecutor());
		CommonPacketHandler.registerPacketHandler(
				PacketLib.MOTION_SENSOR,
				packetMotionSensorHandler);
		
		// MINING TOOL BELT
		PacketMiningToolBeltHandler packetMiningToolBeltHandler = new PacketMiningToolBeltHandler();
		packetMiningToolBeltHandler.registerPacketHandler(
				CommandLib.CYCLE_TOOL_BELT,
				new ToolBeltCycleToolExecutor());
		packetMiningToolBeltHandler.registerPacketHandler(
				CommandLib.TOGGLE_MINING_MODE,
				new MiningModeExecutor());
		CommonPacketHandler.registerPacketHandler(
				PacketLib.MINING_TOOL_BELT,
				packetMiningToolBeltHandler);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerClientPacketExecutors() {
		ClientPacketHandler.init();
		
		// MINING TOOL BELT
		ClientPacketMiningToolBeltHandler clientToolBeltHandler = new ClientPacketMiningToolBeltHandler();
		clientToolBeltHandler.registerPacketHandler(
				CommandLib.UPDATE_TOOL_BELT_CONTENTS,
				new ClientMiningToolBeltUpdateExecutor());
		clientToolBeltHandler.registerPacketHandler(
				CommandLib.UPDATE_TOOL_BELT_TOOL,
				new ClientMiningToolBeltUpdateToolExecutor());
		ClientPacketHandler.registerPacketHandler(
				PacketLib.MINING_TOOL_BELT,
				clientToolBeltHandler);
	}

}
