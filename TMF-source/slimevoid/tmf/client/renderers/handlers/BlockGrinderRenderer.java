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
package slimevoid.tmf.client.renderers.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockGrinderRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        TileEntityGrinder tile = new TileEntityGrinder();
        tile.blockType = block;
        TileEntityRenderer.instance.renderTileEntityAt(tile,
                                                       0.0D,
                                                       0.0D,
                                                       0.0D,
                                                       0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public int getRenderId() {
        // TODO Auto-generated method stub
        return 0;
    }

}
