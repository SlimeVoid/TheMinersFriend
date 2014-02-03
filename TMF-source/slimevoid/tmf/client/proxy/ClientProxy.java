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
package slimevoid.tmf.client.proxy;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityAutomaticMixingTable;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGeologicalEquipment;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityRefinery;
import slimevoid.tmf.blocks.machines.tileentities.TileEntityStove;
import slimevoid.tmf.client.gui.GuiAutomaticMixingTable;
import slimevoid.tmf.client.gui.GuiGeologicalEquipment;
import slimevoid.tmf.client.gui.GuiGrinder;
import slimevoid.tmf.client.gui.GuiMiningToolBelt;
import slimevoid.tmf.client.gui.GuiRefinery;
import slimevoid.tmf.client.gui.GuiStove;
import slimevoid.tmf.client.renderers.BlockMachineRenderingHandler;
import slimevoid.tmf.client.renderers.ItemRendererToolBelt;
import slimevoid.tmf.client.renderers.TileEntitySpecialRendererGrinder;
import slimevoid.tmf.client.tickhandlers.MiningHelmetRenderTickHandler;
import slimevoid.tmf.client.tickhandlers.MotionSensorTickHandler;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleInToolbelt;
import slimevoid.tmf.client.tickhandlers.rules.MotionSensorRuleOnHotbar;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ArmorLib;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoid.tmf.core.lib.CoreLib;
import slimevoid.tmf.core.lib.EventLib;
import slimevoid.tmf.core.lib.GuiLib;
import slimevoid.tmf.core.lib.KeyBindings;
import slimevoid.tmf.core.lib.PacketLib;
import slimevoid.tmf.core.lib.ResourceLib;
import slimevoid.tmf.items.tools.inventory.InventoryMiningToolBelt;
import slimevoid.tmf.proxy.CommonProxy;
import slimevoid.tmf.tickhandlers.MiningHelmetTickHandler;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.util.helpers.BlockHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        PacketLib.registerClientPacketExecutors();

        EventLib.registerClientEvents();

        KeyBindings.registerKeyBindings();
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
            ArmorLib.registerArmorTexture(TMFCore.miningHelmetIron,
                                          ResourceLib.IRON_MINING_HELMET);
            ArmorLib.registerArmorTexture(TMFCore.miningHelmetGold,
                                          ResourceLib.GOLD_MINING_HELMET);
            ArmorLib.registerArmorTexture(TMFCore.miningHelmetDiamond,
                                          ResourceLib.DIAMOND_MINING_HELMET);
        }

        ItemRendererToolBelt.init();

        if (ConfigurationLib.loadMachines) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrinder.class,
                                                         new TileEntitySpecialRendererGrinder());
            RenderingRegistry.registerBlockHandler(new BlockMachineRenderingHandler());
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
            TickRegistry.registerTickHandler(motionSensor,
                                             Side.CLIENT);

            TickRegistry.registerTickHandler(new MiningHelmetTickHandler(),
                                             Side.CLIENT);
            TickRegistry.registerTickHandler(new MiningHelmetRenderTickHandler(),
                                             Side.CLIENT);
        }
    }

    @Override
    public String getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir.getPath();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case GuiLib.GUIID_TOOL_BELT:
            InventoryMiningToolBelt data = new InventoryMiningToolBelt(world, player, player.getHeldItem());
            return new GuiMiningToolBelt(player, data);
        case GuiLib.GUIID_REFINERY:
            TileEntityRefinery tileRefinery = (TileEntityRefinery) BlockHelper.getTileEntity(world,
                                                                                             x,
                                                                                             y,
                                                                                             z,
                                                                                             TileEntityRefinery.class);
            if (tileRefinery != null) {
                return new GuiRefinery(player, tileRefinery);
            }
            return null;
        case GuiLib.GUIID_GRINDER:
            TileEntityGrinder tileGrinder = (TileEntityGrinder) BlockHelper.getTileEntity(world,
                                                                                          x,
                                                                                          y,
                                                                                          z,
                                                                                          TileEntityGrinder.class);
            if (tileGrinder != null) {
                return new GuiGrinder(player, tileGrinder);
            }
            return null;
        case GuiLib.GUIID_GEOEQUIP:
            TileEntityGeologicalEquipment tileGeoEquip = (TileEntityGeologicalEquipment) BlockHelper.getTileEntity(world,
                                                                                                                   x,
                                                                                                                   y,
                                                                                                                   z,
                                                                                                                   TileEntityGeologicalEquipment.class);
            if (tileGeoEquip != null) {
                return new GuiGeologicalEquipment(player, tileGeoEquip);
            }
            return null;
        case GuiLib.GUIID_MIXINGTABLE:
            TileEntityAutomaticMixingTable tileMixTable = (TileEntityAutomaticMixingTable) BlockHelper.getTileEntity(world,
                                                                                                                     x,
                                                                                                                     y,
                                                                                                                     z,
                                                                                                                     TileEntityAutomaticMixingTable.class);
            if (tileMixTable != null) {
                return new GuiAutomaticMixingTable(player, tileMixTable);
            }
            return null;
        case GuiLib.GUIID_STOVE:
            TileEntityStove tileStove = (TileEntityStove) BlockHelper.getTileEntity(world,
                                                                                    x,
                                                                                    y,
                                                                                    z,
                                                                                    TileEntityStove.class);
            if (tileStove != null) {
                return new GuiStove(player, tileStove);
            }
            return null;
        default:
            return null;
        }
    }
}
