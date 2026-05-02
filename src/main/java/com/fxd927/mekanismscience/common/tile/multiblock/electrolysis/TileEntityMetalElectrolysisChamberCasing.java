package com.fxd927.mekanismscience.common.tile.multiblock.electrolysis;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.NBTConstants;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityMetalElectrolysisChamberCasing extends TileEntityMultiblock<MetalElectrolysisChamberMultiblockData> {

    private boolean handleSound;

    public TileEntityMetalElectrolysisChamberCasing(IBlockProvider block, BlockPos pos, BlockState state) {
        super(block, pos, state);
    }

    public TileEntityMetalElectrolysisChamberCasing(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING, pos, state);
    }

    @Override
    public MetalElectrolysisChamberMultiblockData createMultiblock() {
        return new MetalElectrolysisChamberMultiblockData(this);
    }

    @Override
    public MultiblockManager<MetalElectrolysisChamberMultiblockData> getManager() {
        return MekanismScience.metalElectrolysisChamberManager;
    }

    @Override
    protected boolean canPlaySound() {
        MetalElectrolysisChamberMultiblockData multiblock = getMultiblock();
        return multiblock.isFormed() && multiblock.active && handleSound;
    }

    @Override
    @NotNull
    public CompoundTag getReducedUpdateTag() {
        CompoundTag updateTag = super.getReducedUpdateTag();
        MetalElectrolysisChamberMultiblockData multiblock = getMultiblock();
        updateTag.putBoolean(NBTConstants.HANDLE_SOUND, multiblock.isFormed() && multiblock.handlesSound(this));
        return updateTag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag) {
        MetalElectrolysisChamberMultiblockData multiblock = getMultiblock();
        super.handleUpdateTag(tag);
        NBTUtils.setBooleanIfPresent(tag, NBTConstants.HANDLE_SOUND, value -> handleSound = value);
    }
}
