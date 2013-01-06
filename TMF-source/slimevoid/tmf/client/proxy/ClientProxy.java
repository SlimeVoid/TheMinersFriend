package slimevoid.tmf.client.proxy;

import net.minecraft.client.Minecraft;
import slimevoid.tmf.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}
}
