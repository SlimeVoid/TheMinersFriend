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
package net.slimevoid.tmf.client.renderers.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.slimevoid.tmf.blocks.machines.BlockTypeMachine;
import net.slimevoid.tmf.blocks.machines.tileentities.TileEntityGrinder;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class BlockGrinderRenderer implements ISmartBlockModel, ISmartItemModel {

    public void renderInventoryBlock(Block block, int metadata, int modelID, BlockRendererDispatcher renderer) {
        TileEntityGrinder tile = new TileEntityGrinder();
        // tile.blockType = block;
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.2F,
                          0.0F,
                          -0.3F);
        GL11.glRotatef(-5,
                       0,
                       1,
                       0);
        GL11.glRotatef(5,
                       0,
                       0,
                       1);
        GL11.glScalef(1.35F,
                      1.35F,
                      1.35F);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile,
                                                                 -0.3D,
                                                                 -0.3D,
                                                                 -0.3D,
                                                                 0.0F);
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, BlockRendererDispatcher renderer) {
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return BlockTypeMachine.GRINDER.getId();
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
