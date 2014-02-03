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
package slimevoid.tmf.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import slimevoid.tmf.blocks.machines.EnumMachine;
import slimevoid.tmf.core.lib.ConfigurationLib;
import slimevoidlib.blocks.BlockBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockMachineRenderingHandler implements
        ISimpleBlockRenderingHandler {

    private void renderInventoryBlock(BlockBase block, int metadata, float brightness, RenderBlocks renderer) {

        Tessellator tessellator = Tessellator.instance;
        block.setBlockBoundsForItemRender(metadata);
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F,
                       0.0F,
                       1.0F,
                       0.0F);
        GL11.glTranslatef(-0.5F,
                          -0.5F,
                          -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              -1.0F,
                              0.0F);
        renderer.renderFaceYNeg(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         0,
                                                                         metadata));
        tessellator.draw();

        if (renderer.useInventoryTint) {
            int renderColor = block.getRenderColor(metadata);
            float red = (renderColor >> 16 & 255) / 255.0F;
            float green = (renderColor >> 8 & 255) / 255.0F;
            float blue = (renderColor & 255) / 255.0F;
            GL11.glColor4f(red * brightness,
                           green * brightness,
                           blue * brightness,
                           1.0F);
        }

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              1.0F,
                              0.0F);
        renderer.renderFaceYPos(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         1,
                                                                         metadata));
        tessellator.draw();

        if (renderer.useInventoryTint) {
            GL11.glColor4f(brightness,
                           brightness,
                           brightness,
                           1.0F);
        }

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              0.0F,
                              -1.0F);
        renderer.renderFaceZPos(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         2,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              0.0F,
                              1.0F);
        renderer.renderFaceZNeg(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         3,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F,
                              0.0F,
                              0.0F);
        renderer.renderFaceXNeg(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         4,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F,
                              0.0F,
                              0.0F);
        renderer.renderFaceXPos(block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(block,
                                                                         5,
                                                                         metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F,
                          0.5F,
                          0.5F);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (modelID == ConfigurationLib.renderMachineId) {
            if (block instanceof BlockBase) {
                BlockBase blockBase = (BlockBase) block;
                EnumMachine machine = EnumMachine.getMachine(metadata);
                if (machine != null) {
                    if (machine.hasRenderHandler()) {
                        machine.getRenderHandler().renderInventoryBlock(blockBase,
                                                                        metadata,
                                                                        modelID,
                                                                        renderer);
                    } else {
                        this.renderInventoryBlock(blockBase,
                                                  metadata,
                                                  1.0F,
                                                  renderer);
                    }
                }
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        EnumMachine machine = EnumMachine.getMachine(world.getBlockMetadata(x,
                                                                            y,
                                                                            z));
        if (machine != null) {
            if (machine.hasRenderHandler()) {
                return machine.getRenderHandler().renderWorldBlock(world,
                                                                   x,
                                                                   y,
                                                                   z,
                                                                   block,
                                                                   modelId,
                                                                   renderer);
            } else {
                return renderer.renderStandardBlock(block,
                                                    x,
                                                    y,
                                                    z);
            }
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return ConfigurationLib.renderMachineId;
    }

}
