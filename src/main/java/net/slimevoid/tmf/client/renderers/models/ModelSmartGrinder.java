package net.slimevoid.tmf.client.renderers.models;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.slimevoid.library.blocks.state.BlockStates;
import net.slimevoid.library.render.SmartModelSlimevoid;
import net.slimevoid.tmf.blocks.machines.BlockMachineBase;

import java.util.List;

/**
 * Created by Greg on 26/03/15.
 */
public class ModelSmartGrinder extends SmartModelSlimevoid {

    private EnumFacing side;
    private boolean isActive;

    public ModelSmartGrinder() {
        super();
    }

    public ModelSmartGrinder(EnumFacing side, boolean isActive) {
        this();
        this.side = side;
        this.isActive = isActive;
    }
    @Override
    protected IBakedModel getModel() {
        return !this.isActive ? this.getDefaultModel() : null;
    }

    @Override
    protected List<BakedQuad> getBakedQuads(Integer[] uVs, EnumFacing face) {
        return this.getDefaultBakedQuads(uVs, face);
    }

    @Override
    public IBakedModel handleBlockState(IBlockState state) {
        return new ModelSmartGrinder((EnumFacing) state.getValue(BlockStates.FACING), ((Boolean) state.getValue(BlockMachineBase.ACTIVE)).booleanValue());
    }

    @Override
    public IBakedModel handleItemState(ItemStack stack) {
        return new ModelSmartGrinder(EnumFacing.NORTH, false);
    }
}
