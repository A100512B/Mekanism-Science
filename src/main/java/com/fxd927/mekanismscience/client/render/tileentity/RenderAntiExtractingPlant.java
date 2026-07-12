package com.fxd927.mekanismscience.client.render.tileentity;

import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
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
public class RenderAntiExtractingPlant extends MultiblockTileEntityRenderer<AntiExtractingPlantMultiblockData, TileEntityAntiExtractingPlantCasing> {

    public RenderAntiExtractingPlant(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void render(TileEntityAntiExtractingPlantCasing tile, AntiExtractingPlantMultiblockData multiblock, float partialTick,
                          PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        VertexConsumer buffer = renderer.getBuffer(Sheets.translucentCullBlockSheet());
        FluidRenderData data = RenderData.Builder.create(multiblock.extractTank.getFluid())
                .location(multiblock.renderLocation.offset(1, 0, 1))
                .dimensions(multiblock.width() - 2, multiblock.height() - 2, multiblock.length() - 2)
                .build();
        renderObject(data, multiblock.valves, tile.getBlockPos(), matrix, buffer, overlayLight, Math.min(1, multiblock.prevExtractScale));
    }

    @Override
    protected String getProfilerSection() {
        return "antiExtractingPlant";
    }

    @Override
    protected boolean shouldRender(TileEntityAntiExtractingPlantCasing tile, AntiExtractingPlantMultiblockData multiblock, Vec3 camera) {
        return super.shouldRender(tile, multiblock, camera) && !multiblock.extractTank.isEmpty();
    }
}
