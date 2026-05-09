package com.fxd927.mekanismscience.client.render.tileentity;

import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.client.render.data.FluidRenderData;
import mekanism.client.render.data.RenderData;
import mekanism.client.render.tileentity.MultiblockTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

@NothingNullByDefault
public class RenderExtractingPlant extends MultiblockTileEntityRenderer<ExtractingPlantMultiblockData, TileEntityExtractingPlantCasing> {

    public RenderExtractingPlant(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void render(TileEntityExtractingPlantCasing tile, ExtractingPlantMultiblockData multiblock, float partialTick,
                          PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        VertexConsumer buffer = renderer.getBuffer(Sheets.translucentCullBlockSheet());
        FluidRenderData data = RenderData.Builder.create(multiblock.leachateTank.getFluid())
                .location(multiblock.renderLocation.offset(1, 0, 1))
                .dimensions(multiblock.width() - 2, multiblock.height() - 2, multiblock.length() - 2)
                .build();
        renderObject(data, multiblock.valves, tile.getBlockPos(), matrix, buffer, overlayLight, Math.min(1, multiblock.prevLeachateScale));
    }

    @Override
    protected String getProfilerSection() {
        return "extractingPlant";
    }

    @Override
    protected boolean shouldRender(TileEntityExtractingPlantCasing tile, ExtractingPlantMultiblockData multiblock, Vec3 camera) {
        return super.shouldRender(tile, camera) && !multiblock.leachateTank.isEmpty();
    }
}
