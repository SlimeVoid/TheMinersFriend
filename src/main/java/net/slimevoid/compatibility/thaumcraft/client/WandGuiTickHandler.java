package net.slimevoid.compatibility.thaumcraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.slimevoid.compatibility.thaumcraft.ThaumcraftStatic;
import net.slimevoid.tmf.core.helpers.ItemHelper;
import thaumcraft.client.lib.ClientTickEventsFML;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WandGuiTickHandler extends ClientTickEventsFML {

    Minecraft mc;

    public WandGuiTickHandler() {
        this.mc = FMLClientHandler.instance().getClient();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            if (player != null && mc.inGameHasFocus && Minecraft.isGuiEnabled()) {
                if (ItemHelper.isToolBelt(player.getHeldItem())) {
                    ItemStack heldItem = player.inventory.getCurrentItem();
                    ItemStack tool = ItemHelper.getSelectedTool(heldItem);
                    if (ThaumcraftStatic.isWand(tool)) {
                        ItemStack heldCopy = heldItem.copy();
                        player.inventory.setInventorySlotContents(player.inventory.currentItem,
                                                                  tool);
                        super.renderTick(event);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem,
                                                                  heldCopy);
                    }
                }
            }
        }
    }
}
