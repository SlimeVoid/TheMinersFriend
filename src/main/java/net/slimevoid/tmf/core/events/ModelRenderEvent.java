package net.slimevoid.tmf.core.events;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.tmf.client.renderers.models.ModelSmartGrinder;
import net.slimevoid.tmf.core.lib.BlockLib;
import net.slimevoid.tmf.core.lib.CoreLib;

/**
 * Created by Greg on 26/03/15.
 */
@SideOnly(Side.CLIENT)
public class ModelRenderEvent {
    @SubscribeEvent
    public void onModelBaked(ModelBakeEvent event) {
        event.modelRegistry.putObject(new ModelResourceLocation(CoreLib.MOD_ID + ":" + BlockLib.BLOCK_MACHINE_BASE, BlockLib.BLOCK_GRINDER_VARIANT), new ModelSmartGrinder());
    }
}
