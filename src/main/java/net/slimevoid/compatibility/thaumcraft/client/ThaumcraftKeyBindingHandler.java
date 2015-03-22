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
//package net.slimevoid.compatibility.thaumcraft.client;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityClientPlayerMP;
//import net.minecraft.item.ItemStack;
//import net.slimevoid.compatibility.thaumcraft.ThaumcraftStatic;
//import net.slimevoid.tmf.core.helpers.ItemHelper;
//import net.slimevoid.tmf.items.tools.ItemMiningToolBelt;
//import thaumcraft.common.lib.events.KeyHandler;
//import cpw.mods.fml.client.FMLClientHandler;
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import cpw.mods.fml.common.gameevent.TickEvent;
//import cpw.mods.fml.common.gameevent.TickEvent.Phase;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//public class ThaumcraftKeyBindingHandler extends KeyHandler {
//
//    static int       currentItem = -1;
//    static ItemStack heldCopy    = null;
//    private boolean  keyPressed  = false;
//
//    public ThaumcraftKeyBindingHandler() {
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void playerTick(TickEvent.PlayerTickEvent event) {
//        if (event.side != Side.SERVER) {
//            if (event.phase == Phase.START) {
//                Minecraft mc = Minecraft.getMinecraft();
//                EntityClientPlayerMP player = mc.thePlayer;
//                if (ThaumcraftStatic.isWand(player.getHeldItem())
//                    && heldCopy == null) {
//                    super.playerTick(event);
//                    return;
//                }
//                if (this.key.getIsKeyPressed()) {
//                    if (FMLClientHandler.instance().getClient().inGameHasFocus) {
//                        if (!keyPressed) {
//
//                            if (heldCopy == null && currentItem == -1
//                                && !radialLock && player != null
//                                && player.getHeldItem() != null) {
//                                ItemStack heldItem = player.getHeldItem();
//                                ItemStack tool = ItemHelper.getSelectedTool(heldItem);
//                                if (ThaumcraftStatic.isWand(tool)) {
//                                    heldCopy = heldItem.copy();
//                                    currentItem = player.inventory.currentItem;
//                                    player.inventory.setInventorySlotContents(currentItem,
//                                                                              tool);
//                                    super.playerTick(event);
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    if (heldCopy != null) {
//                        ItemStack itemstack = player.inventory.getStackInSlot(currentItem);
//                        if (!ItemHelper.isToolBelt(itemstack)) {
//                            ItemStack wandCopy = ItemStack.copyItemStack(player.inventory.getStackInSlot(currentItem));
//                            ItemStack tool = ItemHelper.getSelectedTool(heldCopy);
//                            ((ItemMiningToolBelt) heldCopy.getItem()).updateToolInToolBelt(player.getEntityWorld(),
//                                                                                           player,
//                                                                                           heldCopy,
//                                                                                           tool,
//                                                                                           wandCopy);
//                            player.inventory.setInventorySlotContents(currentItem,
//                                                                      heldCopy);
//                        }
//                    }
//                    super.playerTick(event);
//                    heldCopy = null;
//                    currentItem = -1;
//                }
//            }
//        }
//    }
//}
