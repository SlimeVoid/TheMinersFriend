package slimevoid.tmf.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import slimevoid.tmf.data.MiningToolBeltData;
import slimevoid.tmf.items.ItemMiningToolbelt;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TMFConnectionHandler implements IConnectionHandler {

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		IInventory playerInventory = entityplayer.inventory;
		for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
			ItemStack itemstack = playerInventory.getStackInSlot(slot);
			if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemMiningToolbelt) {
				MiningToolBeltData data = MiningToolBeltData.getToolBeltDataFromItemStack(entityplayer, entityplayer.worldObj, itemstack);
				if (data != null) {
					PacketDispatcher.sendPacketToPlayer(data.createPacket().getPacket(), player);
				}
			}
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub
		
	}

}
