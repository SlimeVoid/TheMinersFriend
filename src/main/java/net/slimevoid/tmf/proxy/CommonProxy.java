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
package net.slimevoid.tmf.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.slimevoid.compatibility.TMFCompatibility;
import net.slimevoid.library.ICommonProxy;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.util.helpers.SlimevoidHelper;
import net.slimevoid.tmf.blocks.machines.inventory.ContainerAutomaticMixingTable;
import net.slimevoid.tmf.blocks.machines.inventory.ContainerGeologicalEquipment;
import net.slimevoid.tmf.blocks.machines.inventory.ContainerGrinder;
import net.slimevoid.tmf.blocks.machines.inventory.ContainerRefinery;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import net.slimevoid.tmf.core.TheMinersFriend;
import net.slimevoid.tmf.core.lib.ConfigurationLib;
import net.slimevoid.tmf.core.lib.CoreLib;
import net.slimevoid.tmf.core.lib.EventLib;
import net.slimevoid.tmf.core.lib.GuiLib;
import net.slimevoid.tmf.core.lib.PacketLib;
import net.slimevoid.tmf.items.tools.inventory.ContainerMiningToolBelt;
import net.slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;
import net.slimevoid.tmf.items.tools.inventory.SlotUtilityBelt;
import net.slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import net.slimevoid.tmf.tickhandlers.ToolBeltTickHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements ICommonProxy {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case GuiLib.GUIID_TOOL_BELT:
            InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, player, player.getHeldItem());
            return new ContainerMiningToolBelt(player.inventory, data);
        case GuiLib.GUIID_UTILITY_BELT:
            InventoryMiningToolBelt utility = new InventoryMiningToolBelt(world, player, player.getHeldItem());
            return new ContainerMiningToolBelt(player.inventory, utility) {
                @Override
                protected void bindToolBeltInventory(IInventory toolBelt) {
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 0, 69, 37));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 1, 69, 59));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 2, 92, 37));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 3, 92, 59));
                }
            };
        case GuiLib.GUIID_REFINERY:
            TileEntity tileRef = SlimevoidHelper.getBlockTileEntity(world,
                                                                    x,
                                                                    y,
                                                                    z);
            if (tileRef instanceof TileEntityRefinery) {
                TileEntityRefinery tileRefinery = (TileEntityRefinery) tileRef;
                return new ContainerRefinery(player.inventory, tileRefinery);
            }
            return null;
        case GuiLib.GUIID_GRINDER:
            TileEntity tileGrind = SlimevoidHelper.getBlockTileEntity(world,
                                                                      x,
                                                                      y,
                                                                      z);
            if (tileGrind instanceof TileEntityGrinder) {
                TileEntityGrinder tileGrinder = (TileEntityGrinder) tileGrind;
                return new ContainerGrinder(player.inventory, tileGrinder);
            }
            return null;
        case GuiLib.GUIID_GEOEQUIP:
            TileEntity tileGeo = SlimevoidHelper.getBlockTileEntity(world,
                                                                    x,
                                                                    y,
                                                                    z);
            if (tileGeo instanceof TileEntityGeologicalEquipment) {
                TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) tileGeo;
                return new ContainerGeologicalEquipment(player.inventory, tileGeoEquip);
            }
            return null;
        case GuiLib.GUIID_MIXINGTABLE:
            TileEntity tileMix = SlimevoidHelper.getBlockTileEntity(world,
                                                                    x,
                                                                    y,
                                                                    z);
            if (tileMix instanceof TileEntityAutomaticMixingTable) {
                TileEntityAutomaticMixingTable tileMixTable = (TileEntityAutomaticMixingTable) tileMix;
                return new ContainerAutomaticMixingTable(player.inventory, tileMixTable);
            }
            return null;
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(TheMinersFriend.instance,
                                                    this);
        PacketLib.registerPacketExecutors();

        TMFCompatibility.registerPacketExecutors();
    }

    @Override
    public void postInit() {
    }

    @Override
    public void registerConfigurationProperties(File configFile) {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Loading properties...");
        ConfigurationLib.CommonConfig(configFile);
    }

    @Override
    public void registerTickHandlers() {
        if (ConfigurationLib.loadItems) {
            MinecraftForge.EVENT_BUS.register(new MiningHelmetTickHandler());
        }
        MinecraftForge.EVENT_BUS.register(new ToolBeltTickHandler());
    }

    @Override
    public void registerRenderInformation() {
    }

    @Override
    public String getMinecraftDir() {
        return ".";
    }

    @Override
    public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
    }

    @Override
    public void registerEventHandlers() {
        EventLib.registerCommonEvents();
    }

    @Override
    public boolean isClient(World world) {
        return world.isRemote;
    }
}
