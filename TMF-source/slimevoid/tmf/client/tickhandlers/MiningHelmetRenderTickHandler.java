package slimevoid.tmf.client.tickhandlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.core.lib.ArmorLib;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MiningHelmetRenderTickHandler implements ITickHandler {
	private final Minecraft mc;
	
	public MiningHelmetRenderTickHandler() {
		this.mc = FMLClientHandler.instance().getClient();
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if ( 
				type.equals(EnumSet.of(TickType.CLIENT)) ||
				type.equals(EnumSet.of(TickType.RENDER))
				
		) {
			EntityPlayer entityplayer = mc.thePlayer;
			World world = mc.theWorld;
			if (entityplayer != null && world != null) {
				if ( type.equals(EnumSet.of(TickType.RENDER)) ) {
					this.onRenderTick(entityplayer, world);
				}
			}
		}
	}

	private void onRenderTick(EntityPlayer entityplayer, World world) {
		ItemStack miningHelmet = ArmorLib.getHelm(entityplayer, world); 
		if (miningHelmet != null) {
			doRenderMinersLamp(entityplayer, world);
		}
	}

	private void doRenderMinersLamp(EntityPlayer entityplayer, World world) {
		System.out.println("Render Lamp");
		GL11.glPushMatrix();
			// TODO : Render Lamp
		GL11.glPopMatrix();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT,TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "Miners Helmet Rendering";
	}

}
