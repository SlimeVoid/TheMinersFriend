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
package net.slimevoid.tmf.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import org.lwjgl.opengl.GL11;

import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.core.lib.ConfigurationLib;

import java.util.List;

public class BlockMachineRenderingHandler implements ISmartBlockModel, ISmartItemModel{

    private void renderInventoryBlock(BlockBase block, int metadata, float brightness, BlockRendererDispatcher renderer) {

        Tessellator tessellator = Tessellator.getInstance();
        block.setBlockBoundsForItemRender();
        //renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(
                90.0F,
                0.0F,
                1.0F,
                0.0F);
        GL11.glTranslatef(
                -0.5F,
                -0.5F,
                -0.5F);
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                0.0F,
                -1.0F,
                0.0F);
//        renderer.renderFaceYNeg(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         0,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();

//        if (renderer.useInventoryTint) {
//            int renderColor = block.getRenderColor(metadata);
//            float red = (renderColor >> 16 & 255) / 255.0F;
//            float green = (renderColor >> 8 & 255) / 255.0F;
//            float blue = (renderColor & 255) / 255.0F;
//            GL11.glColor4f(red * brightness,
//                           green * brightness,
//                           blue * brightness,
//                           1.0F);
//        }

        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                0.0F,
                1.0F,
                0.0F);
//        renderer.renderFaceYPos(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         1,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();

//        if (renderer.useInventoryTint) {
//            GL11.glColor4f(brightness,
//                           brightness,
//                           brightness,
//                           1.0F);
//        }

        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                0.0F,
                0.0F,
                -1.0F);
//        renderer.renderFaceZPos(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         2,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                0.0F,
                0.0F,
                1.0F);
//        renderer.renderFaceZNeg(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         3,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                -1.0F,
                0.0F,
                0.0F);
//        renderer.renderFaceXNeg(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         4,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().setNormal(
                1.0F,
                0.0F,
                0.0F);
//        renderer.renderFaceXPos(block,
//                                0.0D,
//                                0.0D,
//                                0.0D,
//                                renderer.getBlockIconFromSideAndMetadata(block,
//                                                                         5,
//                                                                         metadata));
        tessellator.getWorldRenderer().finishDrawing();
        GL11.glTranslatef(
                0.5F,
                0.5F,
                0.5F);
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, BlockRendererDispatcher renderer) {
        if (modelID == ConfigurationLib.renderMachineId) {
            if (block instanceof BlockBase) {
                BlockBase blockBase = (BlockBase) block;
                BlockTypeMachine machine = BlockTypeMachine.getMachine(metadata);
                if (machine != null) {
//                    if (machine.hasRenderHandler()) {
//                        machine.getRenderHandler().renderInventoryBlock(blockBase,
//                                                                        metadata,
//                                                                        modelID,
//                                                                        renderer);
//                    } else {
                        this.renderInventoryBlock(blockBase,
                                                  metadata,
                                                  1.0F,
                                                  renderer);
//                    }
                }
            }
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, BlockPos pos, IBlockState state, int modelId, BlockRendererDispatcher renderer) {
        BlockTypeMachine machine = BlockTypeMachine.getMachine(state.getBlock().getMetaFromState(state));
        if (machine != null) {
//            if (machine.hasRenderHandler()) {
//                return machine.getRenderHandler().renderWorldBlock(world,
//                                                                   x,
//                                                                   y,
//                                                                   z,
//                                                                   block,
//                                                                   modelId,
//                                                                   renderer);
//            } else {
//                return renderer.renderStandardBlock(block,
//                                                    x,
//                                                    y,
//                                                    z);
//            }
        }
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ConfigurationLib.renderMachineId;
    }

    @Override
    public IBakedModel handleBlockState(IBlockState state) {
        return null;
    }

    @Override
    public IBakedModel handleItemState(ItemStack stack) {
        return null;
    }

    @Override
    public List getFaceQuads(EnumFacing p_177551_1_) {
        return null;
    }

    @Override
    public List getGeneralQuads() {
        return null;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return null;
    }
}
