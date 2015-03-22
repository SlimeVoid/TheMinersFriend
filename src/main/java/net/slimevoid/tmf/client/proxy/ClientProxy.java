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
package net.slimevoid.tmf.client.proxy;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.compatibility.TMFCompatibility;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.util.helpers.BlockHelper;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityStove;
import net.slimevoid.tmf.client.gui.GuiAutomaticMixingTable;
import net.slimevoid.tmf.client.gui.GuiGeologicalEquipment;
import net.slimevoid.tmf.client.gui.GuiGrinder;
import net.slimevoid.tmf.client.gui.GuiMiningToolBelt;
import net.slimevoid.tmf.client.gui.GuiRefinery;
import net.slimevoid.tmf.client.gui.GuiStove;
import net.slimevoid.tmf.client.renderers.ItemRendererToolBelt;
import net.slimevoid.tmf.client.tickhandlers.MiningHelmetRenderTickHandler;
import net.slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import net.slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleInToolbelt;
import net.slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleOnHotbar;
import net.slimevoid.tmf.core.TMFCore;
import net.slimevoid.tmf.core.lib.ArmorLib;
import net.slimevoid.tmf.core.lib.ConfigurationLib;
import net.slimevoid.tmf.core.lib.CoreLib;
import net.slimevoid.tmf.core.lib.EventLib;
import net.slimevoid.tmf.core.lib.GuiLib;
import net.slimevoid.tmf.core.lib.KeyBindings;
import net.slimevoid.tmf.core.lib.PacketLib;
import net.slimevoid.tmf.core.lib.ResourceLib;
import net.slimevoid.tmf.items.tools.inventory.ContainerMiningToolBelt;
import net.slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;
import net.slimevoid.tmf.items.tools.inventory.SlotUtilityBelt;
import net.slimevoid.tmf.proxy.CommonProxy;
import net.slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import net.slimevoid.tmf.tickhandlers.ToolBeltTickHandler;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();

        PacketLib.registerClientPacketExecutors();
    }

    @Override
    public void postInit() {
        super.postInit();
        KeyBindings.registerKeyBindings();

        TMFCompatibility.registerKeyBindings();
    }

    @Override
    public void registerConfigurationProperties(File configFile) {
        super.registerConfigurationProperties(configFile);
        ConfigurationLib.ClientConfig();
    }

    @Override
    public void registerRenderInformation() {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Registering Renderers...");
        if (ConfigurationLib.loadItems) {
            ArmorLib.registerArmorTexture(ConfigurationLib.miningHelmetIron,
                                          ResourceLib.IRON_MINING_HELMET);
            ArmorLib.registerArmorTexture(ConfigurationLib.miningHelmetGold,
                                          ResourceLib.GOLD_MINING_HELMET);
            ArmorLib.registerArmorTexture(ConfigurationLib.miningHelmetDiamond,
                                          ResourceLib.DIAMOND_MINING_HELMET);
        }

        ItemRendererToolBelt.init();

        if (ConfigurationLib.loadMachines) {
            //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrinder.class,
            //                                             new TileEntitySpecialRendererGrinder());
            //RenderingRegistry.registerBlockHandler(new BlockMachineRenderingHandler());
        }
    }

    @Override
    public void registerTickHandlers() {
        super.registerTickHandlers();
        if (ConfigurationLib.loadItems) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Registering Client tick handlers...");

            MotionSensorTickHandler motionSensor = new MotionSensorTickHandler(ConfigurationLib.motionSensorMaxEntityDistance, ConfigurationLib.motionSensorMaxGameTicks, ConfigurationLib.motionSensorDrawRight);
            motionSensor.addRule(new MotionSensorRuleOnHotbar());
            motionSensor.addRule(new MotionSensorRuleInToolbelt());
            MinecraftForge.EVENT_BUS.register(motionSensor);
            MinecraftForge.EVENT_BUS.register(new MiningHelmetTickHandler());
            MinecraftForge.EVENT_BUS.register(new MiningHelmetRenderTickHandler());
        }
        MinecraftForge.EVENT_BUS.register(new ToolBeltTickHandler());
        TMFCompatibility.registerTickHandlers();
    }

    @Override
    public String getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir.getPath();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID) {
        case GuiLib.GUIID_TOOL_BELT:
            InventoryMiningToolBelt toolBelt = new InventoryMiningToolBelt(world, player, player.getHeldItem());
            return new GuiMiningToolBelt(new ContainerMiningToolBelt(player.inventory, toolBelt), player, toolBelt);
        case GuiLib.GUIID_UTILITY_BELT:
            InventoryMiningToolBelt utilityBelt = new InventoryMiningToolBelt(world, player, player.getHeldItem());
            ContainerMiningToolBelt container = new ContainerMiningToolBelt(player.inventory, utilityBelt) {
                @Override
                protected void bindToolBeltInventory(IInventory toolBelt) {
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 0, 69, 37));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 1, 69, 59));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 2, 92, 37));
                    this.addSlotToContainer(new SlotUtilityBelt(toolBelt, 3, 92, 59));
                }
            };
            return new GuiMiningToolBelt(container, player, utilityBelt) {
                @Override
                public ResourceLocation getBackground() {
                    return ResourceLib.GUI_UTILITYBELT;
                }
            };
        case GuiLib.GUIID_REFINERY:
            TileEntityRefinery tileRefinery = (TileEntityRefinery) BlockHelper.getTileEntity(world,
                                                                                             pos,
                                                                                             TileEntityRefinery.class);
            if (tileRefinery != null) {
                return new GuiRefinery(player, tileRefinery);
            }
            return null;
        case GuiLib.GUIID_GRINDER:
            TileEntityGrinder tileGrinder = (TileEntityGrinder) BlockHelper.getTileEntity(world,
                                                                                          pos,
                                                                                          TileEntityGrinder.class);
            if (tileGrinder != null) {
                return new GuiGrinder(player, tileGrinder);
            }
            return null;
        case GuiLib.GUIID_GEOEQUIP:
            TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) BlockHelper.getTileEntity(world,
                                                                                                                   pos,
                                                                                                                   TileEntityGeologicalEquipment.class);
            if (tileGeoEquip != null) {
                return new GuiGeologicalEquipment(player, tileGeoEquip);
            }
            return null;
        case GuiLib.GUIID_MIXINGTABLE:
            TileEntityAutomaticMixingTable tileMixTable = (TileEntityAutomaticMixingTable) BlockHelper.getTileEntity(world,
                                                                                                                     pos,
                                                                                                                     TileEntityAutomaticMixingTable.class);
            if (tileMixTable != null) {
                return new GuiAutomaticMixingTable(player, tileMixTable);
            }
            return null;
        case GuiLib.GUIID_STOVE:
            TileEntityStove tileStove = (TileEntityStove) BlockHelper.getTileEntity(world,
                                                                                    pos,
                                                                                    TileEntityStove.class);
            if (tileStove != null) {
                return new GuiStove(player, tileStove);
            }
            return null;
        default:
            return null;
        }
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        EventLib.registerClientEvents();
        TMFCompatibility.registerEventHandlers();
    }
}
